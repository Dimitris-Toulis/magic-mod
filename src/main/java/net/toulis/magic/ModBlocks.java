package net.toulis.magic;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.toulis.magic.block.MagicNullifier;
import net.toulis.magic.block.wandEditor.WandEditor;

import java.util.function.Function;

public class ModBlocks {
    public static final Block MAGIC_ORE = register("magic_ore", Block::new, Block.Settings.create().strength(3f).requiresTool());
    public static final Block DEEPSLATE_MAGIC_ORE = register("deepslate_magic_ore", Block::new, Block.Settings.create().strength(5f).requiresTool());
    public static final Block MAGIC_NULLIFIER = register("magic_nullifier", MagicNullifier::new, Block.Settings.create().strength(4f));
    public static final Block WAND_EDITOR = register("wand_editor", WandEditor::new, Block.Settings.create().strength(1f));

    private static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final Identifier identifier = Identifier.of(MagicMod.MOD_ID, path);
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        final Block block = Blocks.register(registryKey, factory, settings);
        Items.register(block);
        return block;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(itemGroup -> {
            itemGroup.add(MAGIC_ORE);
            itemGroup.add(DEEPSLATE_MAGIC_ORE);
            itemGroup.add(MAGIC_NULLIFIER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(itemGroup -> itemGroup.add(WAND_EDITOR));
    }
}
