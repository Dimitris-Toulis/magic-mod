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
    public static final Item CONCENTRATED_MAGIC_DUST = register("concentrated_magic_dust", Item::new, new Item.Settings());
    public static final Item MAGIC_WAND_TIER_1 = register("magic_wand_tier_1", settings -> new MagicWand(1,settings), new Item.Settings().maxCount(1));
    public static final Item MAGIC_WAND_TIER_2 = register("magic_wand_tier_2", settings -> new MagicWand(2,settings), new Item.Settings().maxCount(1));
    public static final Item MAGIC_WAND_TIER_3 = register("magic_wand_tier_3", settings -> new MagicWand(3,settings), new Item.Settings().maxCount(1));

    public static final Item SPELL_FIREBALL = register("spell_fireball", Item::new, new Item.Settings());
    public static final Item SPELL_LIGHTNING_BOLT = register("spell_lightning_bolt", Item::new, new Item.Settings());
    public static final Item SPELL_ARROW = register("spell_arrow", Item::new, new Item.Settings());

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MagicMod.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }

    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(itemGroup -> itemGroup.add(MAGIC_DUST));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(itemGroup -> itemGroup.add(CONCENTRATED_MAGIC_DUST));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> itemGroup.add(MAGIC_WAND_TIER_1));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> itemGroup.add(MAGIC_WAND_TIER_2));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> itemGroup.add(MAGIC_WAND_TIER_3));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(itemGroup -> itemGroup.add(SPELL_FIREBALL));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(itemGroup -> itemGroup.add(SPELL_LIGHTNING_BOLT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(itemGroup -> itemGroup.add(SPELL_ARROW));
    }

}
