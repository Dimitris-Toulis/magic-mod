package net.toulis.magic;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.toulis.magic.item.MagicWand;
import net.toulis.magic.spell.*;

import java.util.function.Function;

public class ModItems {
    public static final Item MAGIC_DUST = register("magic_dust", Item::new, new Item.Settings());
    public static final Item CONCENTRATED_MAGIC_DUST = register("concentrated_magic_dust", Item::new, new Item.Settings().rarity(Rarity.RARE));
    public static final Item MAGIC_ESSENCE = register("magic_essence", Item::new, new Item.Settings().rarity(Rarity.EPIC));
    public static final Item MAGIC_WAND_TIER_1 = register("magic_wand_tier_1", settings -> new MagicWand(1,settings), new Item.Settings().maxCount(1));
    public static final Item MAGIC_WAND_TIER_2 = register("magic_wand_tier_2", settings -> new MagicWand(2,settings), new Item.Settings().maxCount(1).rarity(Rarity.RARE));
    public static final Item MAGIC_WAND_TIER_3 = register("magic_wand_tier_3", settings -> new MagicWand(3,settings), new Item.Settings().maxCount(1).rarity(Rarity.EPIC));

    public static final Item SPELL_FIREBALL = register("spell_fireball", FireballSpell::new, new Item.Settings());
    public static final Item SPELL_LIGHTNING_BOLT = register("spell_lightning_bolt", LightningSpell::new, new Item.Settings().rarity(Rarity.EPIC));
    public static final Item SPELL_EXPLODING_ARROW = register("spell_exploding_arrow", ExplodingArrowSpell::new, new Item.Settings());
    public static final Item SPELL_TELEPORT = register("spell_teleport", TeleportSpell::new, new Item.Settings().rarity(Rarity.RARE));
    public static final Item SPELL_REDUCE_RECHARGE = register("spell_reduce_recharge", ReduceRechargeSpell::new, new Item.Settings());
    public static final Item SPELL_CAST_ALL = register("spell_cast_all", CastAllSpell::new, new Item.Settings().rarity(Rarity.RARE));
    public static final Item SPELL_CONTINUE = register("spell_continue", ContinueSpell::new, new Item.Settings().rarity(Rarity.RARE));
    public static final Item SPELL_NO_COOLDOWN = register("spell_no_cooldown", NoCooldownSpell::new, new Item.Settings().rarity(Rarity.EPIC));
    public static final Item SPELL_RANDOM_POTION = register("spell_random_potion", RandomPotionSpell::new, new Item.Settings().rarity(Rarity.EPIC));

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MagicMod.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }

    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(itemGroup -> {
            itemGroup.add(MAGIC_DUST);
            itemGroup.add(CONCENTRATED_MAGIC_DUST);
            itemGroup.add(MAGIC_ESSENCE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> {
            itemGroup.add(MAGIC_WAND_TIER_1);
            itemGroup.add(MAGIC_WAND_TIER_2);
            itemGroup.add(MAGIC_WAND_TIER_3);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.add(SPELL_FIREBALL);
            itemGroup.add(SPELL_EXPLODING_ARROW);
            itemGroup.add(SPELL_REDUCE_RECHARGE);
            itemGroup.add(SPELL_RANDOM_POTION);
            itemGroup.add(SPELL_TELEPORT);
            itemGroup.add(SPELL_CAST_ALL);
            itemGroup.add(SPELL_CONTINUE);
            itemGroup.add(SPELL_NO_COOLDOWN);
            itemGroup.add(SPELL_LIGHTNING_BOLT);
        });
    }

}
