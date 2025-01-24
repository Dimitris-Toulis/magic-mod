package net.toulis.magic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.toulis.magic.block.wandEditor.WandEditorScreen;

@Environment(EnvType.CLIENT)
public class ClientMagicMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModBlockEntities.WAND_EDITOR_SCREEN_HANDLER, WandEditorScreen::new);
    }
}

