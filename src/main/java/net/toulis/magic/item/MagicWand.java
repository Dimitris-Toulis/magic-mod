package net.toulis.magic.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UseCooldownComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.toulis.magic.MagicMod;
import net.toulis.magic.ModComponents;
import net.toulis.magic.spell.SpellItem;

import java.util.List;
import java.util.Optional;

public class MagicWand extends Item {
    public MagicWand(int tier, Settings settings) {
        super(settings);
        this.tier = tier;
    }
    private final int tier;
    private int castIndex = 0;

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return ActionResult.PASS;
        }
        cast(world,player,player.getStackInHand(hand));
        return ActionResult.PASS;
    }

    private void cast(World world, PlayerEntity player, ItemStack stack) {
        List<String> spellList = stack.get(ModComponents.SPELLS_COMPONENT);
        if(spellList == null) return;

        SpellItem spell = (SpellItem) Registries.ITEM.get(Identifier.of(spellList.get(this.castIndex))).asItem();
        spell.cast(world,player);
        this.castIndex = (this.castIndex + 1) % spellList.size();

        int cooldown = Integer.max(spell.getCooldown()+this.getCooldown(),this.castIndex == 0 ? this.getRechargeTime() : 0);
        stack.set(DataComponentTypes.USE_COOLDOWN, new UseCooldownComponent(cooldown, Optional.of(Identifier.of(MagicMod.MOD_ID, String.valueOf(world.random.nextInt(100))))));
        player.getItemCooldownManager().set(stack, cooldown);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        List<String> spells = stack.get(ModComponents.SPELLS_COMPONENT);
        if (spells != null) {
            for(int i=0;i<spells.size();i++){
                tooltip.add(Text.translatable(Registries.ITEM.get(Identifier.of(spells.get(i))).getTranslationKey()).formatted(i == this.castIndex ? Formatting.AQUA :Formatting.GOLD));
            }
        }
    }

    public int getMaxSpells(){
        return switch (tier) {
            case 1 -> 3;
            case 2 -> 8;
            case 3 -> 14;
            default -> 0;
        };
    }
    private int getCooldown(){
        return switch (tier) {
            case 1 -> 10;
            case 2 -> 6;
            case 3 -> 2;
            default -> 0;
        };
    }
    private int getRechargeTime(){
        return switch (tier) {
            case 1 -> 30;
            case 2 -> 20;
            case 3 -> 10;
            default -> 0;
        };
    }
    public int getTier() {
        return tier;
    }
}
