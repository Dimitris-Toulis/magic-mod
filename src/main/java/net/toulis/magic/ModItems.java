package net.toulis.magic;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.toulis.magic.item.MagicWand;

import java.util.function.Function;

public class ModItems {
    public static final Item MAGIC_DUST = register("magic_dust", Item::new, new Item.Settings());
    public static final Item MAGIC_WAND_TIER_1 = register("magic_wand_tier_1", MagicWand::new, new Item.Settings().maxCount(16));

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MagicMod.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }


    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(itemGroup -> itemGroup.add(MAGIC_DUST));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> itemGroup.add(MAGIC_WAND_TIER_1));
    }

}
