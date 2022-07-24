package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.cca.PeculiarChunkComponents;
import amymialee.peculiarpieces.util.RedstoneInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RedstoneActivatorItem extends Item {
    public RedstoneActivatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PeculiarChunkComponents.REDSTONE.maybeGet(world.getWorldChunk(pos)).ifPresent((manager) -> {
            System.out.println("activ");
            manager.addInstance(pos, new RedstoneInstance(world));
            System.out.println(manager.getInstance(pos));
        });
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            player.getItemCooldownManager().set(this, 2);
        }
        return ActionResult.SUCCESS;
    }
}