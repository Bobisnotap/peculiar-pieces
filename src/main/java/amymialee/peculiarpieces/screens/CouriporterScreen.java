package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CouriporterScreen extends HandledScreen<CouriporterScreenHandler> {
    private static final Identifier TEXTURE = PeculiarPieces.id("textures/gui/single_inventory.png");
    private static final Identifier EMPTY_TEXTURE = PeculiarPieces.id("textures/gui/empty_pearl.png");

    public CouriporterScreen(CouriporterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;
        this.backgroundHeight = 133;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (handler.getSlot(0).getStack().isEmpty()) {
            RenderSystem.setShaderTexture(0, EMPTY_TEXTURE);
            DrawableHelper.drawTexture(matrices, x + 80, y + 20, getZOffset(), 0, 0, 16, 16, 16, 16);
        }
    }
}