package net.toulis.magic;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.toulis.magic.block.MagicNullifierBlockEntity;
import net.toulis.magic.block.wandEditor.WandEditorScreenHandler;

public class ModBlockEntities {
    public static final BlockEntityType<MagicNullifierBlockEntity> MAGIC_NULLIFIER_BLOCK_ENTITY =
            register("magic_nullifier", MagicNullifierBlockEntity::new, ModBlocks.MAGIC_NULLIFIER);
    public static final ScreenHandlerType<WandEditorScreenHandler> WAND_EDITOR_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MagicMod.MOD_ID, "wand_editor"), new ScreenHandlerType<>(WandEditorScreenHandler::new, FeatureSet.empty()));

    private static <T extends BlockEntity> BlockEntityType<T> register(String name,
                                                                       FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
                                                                       Block... blocks) {
        Identifier id = Identifier.of(MagicMod.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
    public static void init() {
    }
}
