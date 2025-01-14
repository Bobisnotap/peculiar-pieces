package amymialee.peculiarpieces.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

public class SlimeItem extends Item {
    public SlimeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        SlimeEntity slime = EntityType.SLIME.create(context.getWorld());
        if (slime != null) {
            slime.setSize(0, true);
            slime.setPosition(Vec3d.ofBottomCenter(context.getBlockPos()));
            if (context.getWorld().spawnEntity(slime)) {
                context.getStack().decrement(1);
            }
        }
        return super.useOnBlock(context);
    }
}