package amymialee.peculiarpieces.util;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RedstoneManager implements ServerTickingComponent {
    private final Chunk chunk;
    private static final LinkedHashMap<BlockPos, RedstoneInstance> redstoneInstances = new LinkedHashMap<>();

    public RedstoneManager(Chunk chunk) {
        this.chunk = chunk;
    }

    public void addInstance(BlockPos pos, RedstoneInstance instance) {
        redstoneInstances.remove(pos);
        redstoneInstances.put(pos, instance);
        updatePos(instance.getWorld(), pos);
    }

    private void updatePos(World world, BlockPos pos) {
        chunk.getBlockState(pos).neighborUpdate(world, pos, Blocks.REDSTONE_BLOCK, pos, true);
        world.updateNeighbors(pos, Blocks.REDSTONE_BLOCK);
        this.chunk.setNeedsSaving(true);
    }

    @Override
    public void serverTick() {
        ArrayList<BlockPos> expirations = new ArrayList<>();
        redstoneInstances.forEach((p, r) -> {
            if (r.tick()) {
                expirations.add(p);
            } else {
                this.chunk.setNeedsSaving(true);
            }
            System.out.println(r);
        });
        for (BlockPos pos : expirations) {
            RedstoneInstance instance = redstoneInstances.get(pos);
            redstoneInstances.remove(pos);
            updatePos(instance.getWorld(), pos);
        }
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag) {

    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {

    }

    public RedstoneInstance getInstance(BlockPos pos) {
        return redstoneInstances.get(pos);
    }

    public boolean isPowered(BlockPos pos) {
        RedstoneInstance instance = redstoneInstances.get(pos);
        return instance != null && instance.getLifetime() > 0;
    }

    public boolean isStrong(BlockPos pos) {
        RedstoneInstance instance = redstoneInstances.get(pos);
        if (instance != null && instance.getLifetime() > 0) {
            return instance.isStrong();
        }
        return false;
    }

    public int getPower(BlockPos pos) {
        RedstoneInstance instance = redstoneInstances.get(pos);
        if (instance != null && instance.getLifetime() > 0) {
            return instance.getPower();
        }
        return 0;
    }
}