package net.toulis.magic;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Item> SPELL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MagicMod.MOD_ID, "spell"));

    protected static void init() {
        MagicMod.LOGGER.info("Registering {} tags", MagicMod.MOD_ID);
    }
}
