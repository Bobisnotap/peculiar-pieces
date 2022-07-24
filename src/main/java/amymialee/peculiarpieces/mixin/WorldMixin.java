package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.cca.PeculiarChunkComponents;
import amymialee.peculiarpieces.util.RedstoneInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
    @Shadow public abstract WorldChunk getWorldChunk(BlockPos pos);

    @Inject(method = "isReceivingRedstonePower", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$RedstoneInstances(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        PeculiarChunkComponents.REDSTONE.maybeGet(this.getWorldChunk(pos)).ifPresentOrElse((manager) -> {
            if (manager.isPowered(pos)) {
                cir.setReturnValue(true);
            }
        }, () -> System.out.println(132));
    }

    @Inject(method = "getReceivedRedstonePower", at = @At("RETURN"), cancellable = true)
    public void PeculiarPieces$RedstoneInstancePower(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        PeculiarChunkComponents.REDSTONE.maybeGet(this.getWorldChunk(pos)).ifPresentOrElse((manager) -> {
            int instancePower = manager.getPower(pos);
            if (cir.getReturnValue() < instancePower) {
                cir.setReturnValue(instancePower);
            }
        }, () -> System.out.println(12341));
    }

    @Inject(method = "getReceivedStrongRedstonePower", at = @At("RETURN"), cancellable = true)
    public void PeculiarPieces$StrongRedstoneInstancePower(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        PeculiarChunkComponents.REDSTONE.maybeGet(this.getWorldChunk(pos)).ifPresentOrElse((manager) -> {
            RedstoneInstance instance = manager.getInstance(pos);
            if (instance != null) {
                System.out.println(instance);
                if (instance.isStrong()) {
                    int instancePower = instance.getPower();
                    if (cir.getReturnValue() < instancePower) {
                        cir.setReturnValue(instancePower);
                    }
                }
            }
        }, () -> System.out.println(534));
    }
}