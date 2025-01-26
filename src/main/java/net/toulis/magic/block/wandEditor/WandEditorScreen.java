package net.toulis.magic.block.wandEditor;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.toulis.magic.MagicMod;

public class WandEditorScreen extends ForgingScreen<WandEditorScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of(MagicMod.MOD_ID,"textures/gui/container/wand_editor.png");
    private static final Identifier ERROR_TEXTURE = Identifier.of(MagicMod.MOD_ID,"container/wand_editor/error");

    public WandEditorScreen(WandEditorScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, TEXTURE);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderSlotTooltip(context, mouseX, mouseY);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {
        if (this.hasInvalidRecipe()) {
            context.drawGuiTexture(RenderLayer::getGuiTextured, ERROR_TEXTURE, x + 95, y + 48, 22, 15);
        }
    }

    private void renderSlotTooltip(DrawContext context, int mouseX, int mouseY) {
        if (this.hasInvalidRecipe() && this.isPointWithinBounds(95, 48, 22, 15, mouseX, mouseY)) {
            Text error;
            if(this.handler.invalidRecipe() == 1){
                error = Text.translatable("container.wand_editor.too_many_spells");
            } else {
                error = Text.translatable("container.wand_editor.low_tier");
            }
            context.drawOrderedTooltip(this.textRenderer, this.textRenderer.wrapLines(error, 135), mouseX, mouseY);
        }
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    private boolean hasInvalidRecipe() {
        return this.handler.invalidRecipe() != 0;
    }
}
