package amymialee.peculiarpieces;

import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import amymialee.peculiarpieces.client.FishTankBlockEntityRenderer;
import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.blockentities.PedestalBlockEntity;
import amymialee.peculiarpieces.blocks.RedstoneStaticBlock;
import amymialee.peculiarpieces.client.FlagBlockEntityRenderer;
import amymialee.peculiarpieces.client.HangGliderEntityModel;
import amymialee.peculiarpieces.client.PedestalBlockEntityRenderer;
import amymialee.peculiarpieces.client.TeleportItemEntityRenderer;
import amymialee.peculiarpieces.items.PlayerCompassItem;
import amymialee.peculiarpieces.items.TransportPearlItem;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.registry.PeculiarEntities;
import amymialee.peculiarpieces.registry.PeculiarItems;
import amymialee.peculiarpieces.screens.FishTankScreen;
import amymialee.peculiarpieces.screens.PackedPouchScreen;
import amymialee.peculiarpieces.screens.PedestalScreen;
import amymialee.peculiarpieces.screens.PotionPadScreen;
import amymialee.peculiarpieces.screens.WarpScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PeculiarPiecesClient implements ClientModInitializer {
    public static final EntityModelLayer HANG_GLIDER = new EntityModelLayer(PeculiarPieces.id("hang_glider"), "main");
    public static final EntityModelLayer FLAG = new EntityModelLayer(PeculiarPieces.id("flag"), "main");
    private final FlagBlockEntity renderFlag = new FlagBlockEntity(BlockPos.ORIGIN, PeculiarBlocks.FLAG.getDefaultState());

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT_REMOVER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT_RETURNER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT_DAMAGER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.INVISIBLE_PLATE_HEAVY, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.INVISIBLE_PLATE_LIGHT, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.INVISIBLE_PLAYER_PLATE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_CLAMP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_FILTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_HURDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_STATIC, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_MONO, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_RANDOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ADVENTURE_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SURVIVOR_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ADVENTURE_BLOCKER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SURVIVOR_BLOCKER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ADVENTURE_SETTER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SURVIVOR_SETTER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.TORCH_LEVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SOUL_TORCH_LEVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ENTITY_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.TINTED_ENTITY_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.PLAYER_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.TINTED_PLAYER_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ENTITY_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.PLAYER_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.TOUGHENED_SCAFFOLDING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ENTANGLED_SCAFFOLDING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.POTION_PAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.FISH_TANK, RenderLayer.getCutout());

        HandledScreens.register(PeculiarPieces.WARP_SCREEN_HANDLER, WarpScreen::new);
        HandledScreens.register(PeculiarPieces.POTION_PAD_SCREEN_HANDLER, PotionPadScreen::new);
        HandledScreens.register(PeculiarPieces.BUSTLING_SCREEN_HANDLER, PackedPouchScreen::new);
        HandledScreens.register(PeculiarPieces.FISH_TANK_SCREEN_HANDLER, FishTankScreen::new);

        BlockEntityRendererRegistry.register(PeculiarBlocks.FISH_TANK_BLOCK_ENTITY, FishTankBlockEntityRenderer::new);
        HandledScreens.register(PeculiarPieces.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);

        EntityModelLayerRegistry.registerModelLayer(HANG_GLIDER, HangGliderEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FLAG, FlagBlockEntityRenderer::getTexturedModelData);

        EntityRendererRegistry.register(PeculiarEntities.TELEPORT_ITEM_ENTITY, TeleportItemEntityRenderer::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> tintIndex == 1 ? (state.get(RedstoneStaticBlock.POWERED) ? 16711680 : 2621440) : -1, PeculiarBlocks.REDSTONE_STATIC);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex != 1 ? -1 : 16711680, PeculiarBlocks.REDSTONE_STATIC);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MathHelper.hsvToRgb(((float)(TransportPearlItem.getSlot(stack) + 1) / 8), 1.0F, 1.0F), PeculiarItems.TRANS_PEARL);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 0xF800F8, PeculiarBlocks.POTION_PAD);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtil.getColor(stack), PeculiarItems.HIDDEN_POTION);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), PeculiarItems.PACKED_POUCH);


        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), PeculiarItems.PACKED_POUCH, PeculiarItems.REACHING_REMOTE, PeculiarItems.REDSTONE_REMOTE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 16560501, PeculiarItems.PLAYER_COMPASS);

        BlockEntityRendererRegistry.register(PeculiarBlocks.PEDESTAL_BLOCK_ENTITY, ctx -> new PedestalBlockEntityRenderer());
        BlockEntityRendererRegistry.register(PeculiarBlocks.FLAG_BLOCK_ENTITY, FlagBlockEntityRenderer::new);

        ClientPlayNetworking.registerGlobalReceiver(PedestalBlockEntity.PEDESTAL_SYNC, ((client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            ItemStack stack1 = buf.readItemStack();
            ItemStack stack2 = buf.readItemStack();
            client.execute(() -> {
                if (client.world != null) {
                    if (client.world.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal) {
                        pedestal.setStack(0, stack1);
                        pedestal.setStack(1, stack2);
                    }
                }
            });
        }));
        ClientPlayNetworking.registerGlobalReceiver(FishTankBlockEntity.FISH_SYNC, ((client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            ItemStack stack = buf.readItemStack();
            client.execute(() -> {
                if (client.world != null) {
                    if (client.world.getBlockEntity(pos) instanceof FishTankBlockEntity tank) {
                        tank.setStack(0, stack);
                    }
                }
            });
        }));

        BuiltinItemRendererRegistry.INSTANCE.register(PeculiarBlocks.FLAG, (stack, mode, matrixStack, vertexConsumerProvider, light, overlay) -> {
            this.renderFlag.readFrom(stack);
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(renderFlag, matrixStack, vertexConsumerProvider, light, overlay);
        });
    }

    static {
        ModelPredicateProviderRegistry.register(PeculiarBlocks.JUMP_PAD.asItem(), new Identifier("variant"), (stack, world, entity, number) -> stack.getNbt() == null ? 0 : (float) stack.getNbt().getInt("pp:variant") / 3);
        ModelPredicateProviderRegistry.register(PeculiarBlocks.PUSH_PAD.asItem(), new Identifier("variant"), (stack, world, entity, number) -> stack.getNbt() == null ? 0 : (float) stack.getNbt().getInt("pp:variant") / 3);
        ModelPredicateProviderRegistry.register(PeculiarItems.HANG_GLIDER, new Identifier("active"), (stack, world, entity, number) -> stack.getNbt() == null || !stack.getNbt().getBoolean("pp:gliding") ? 0 : 1);
        ModelPredicateProviderRegistry.register(PeculiarItems.PLAYER_COMPASS, new Identifier("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> PlayerCompassItem.createPlayerPos(world, stack.getOrCreateNbt())));
    }
}