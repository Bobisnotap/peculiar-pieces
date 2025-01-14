package amymialee.peculiarpieces.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PhasingDoorBlock extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final BooleanProperty SOLID = BooleanProperty.of("solid");

    public PhasingDoorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SOLID, true).with(ACTIVE, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return !state.get(SOLID) ? VoxelShapes.empty() : super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return !state.get(SOLID) ? VoxelShapes.empty() : super.getCameraCollisionShape(state, world, pos, context);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.getBlock() instanceof PhasingDoorBlock) {
            return !stateFrom.get(SOLID);
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())) {
            ctx.getWorld().createAndScheduleBlockTick(ctx.getBlockPos(), this.asBlock(), 2);
        }
        return this.getDefaultState();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.get(ACTIVE)) {
            world.setBlockState(pos, state.with(ACTIVE, true));
            activateNearby(world, pos, world.random);
            world.createAndScheduleBlockTick(pos, this.asBlock(), 4);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient) {
            return;
        }
        boolean bl = state.get(ACTIVE);
        if (!bl && world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(ACTIVE));
            activateNearby(world, pos, world.random);
            world.createAndScheduleBlockTick(pos, this.asBlock(), 4);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean active = state.get(ACTIVE);
        boolean solid = state.get(SOLID);
        if (active && solid) {
            world.setBlockState(pos, state.cycle(SOLID));
            world.createAndScheduleBlockTick(pos, this.asBlock(), 140);
            world.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.5f, 0.5f * (1 + nearbyActive(world, pos)));
        } else if (active) {
            world.setBlockState(pos, state.cycle(ACTIVE));
            world.createAndScheduleBlockTick(pos, this.asBlock(), 4);
        } else if (!solid) {
            world.setBlockState(pos, state.cycle(SOLID));
            world.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.5f, 0.5f * (1 + nearbyActive(world, pos)));
        } else {
            world.setBlockState(pos, state.cycle(ACTIVE));
            activateNearby(world, pos, random);
            world.createAndScheduleBlockTick(pos, this.asBlock(), 4);
        }
    }

    private static void activateNearby(World world, BlockPos pos, Random random) {
        for (Direction direction : Direction.values()) {
            BlockPos pos2 = pos.add(direction.getVector());
            BlockState state = world.getBlockState(pos2);
            if (state.getBlock() instanceof PhasingDoorBlock && state.get(SOLID) && !state.get(ACTIVE)) {
                world.createAndScheduleBlockTick(pos2, state.getBlock(), random.nextInt(3) + 1);
            }
        }
    }

    private static int nearbyActive(World world, BlockPos pos) {
        int active = 0;
        for (Direction direction : Direction.values()) {
            BlockState state = world.getBlockState(pos.add(direction.getVector()));
            if (state.getBlock() instanceof PhasingDoorBlock && !state.get(SOLID) && state.get(ACTIVE)) {
                active++;
            }
        }
        return active;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE, SOLID);
    }
}