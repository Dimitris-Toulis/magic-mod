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
import net.toulis.magic.spell.SpellItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static net.toulis.magic.ModComponents.*;

public class MagicWand extends Item {
    public MagicWand(int tier, Settings settings) {
        super(settings);
        this.tier = tier;
    }
    private final int tier;

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return ActionResult.PASS;
        }
        cast(world,player,player.getStackInHand(hand));
        return ActionResult.PASS;
    }

    private void cast(World world, PlayerEntity player, ItemStack stack) {
        List<String> spellList = stack.get(SPELLS_COMPONENT);
        if(spellList == null) return;
        int castingIndex = stack.getOrDefault(CASTING_INDEX,0);

        SpellItem spell = (SpellItem) Registries.ITEM.get(Identifier.of(spellList.get(castingIndex))).asItem();
        spell.cast(world,player,this.tier,stack);
        castingIndex = (castingIndex + 1) % spellList.size();

        int cooldown = spell.getCooldown()+this.getCooldown();
        if(castingIndex == 0){
            int rechargeReduction = stack.getOrDefault(REDUCE_RECHARGE_TIME,0);
            cooldown = Integer.max(Integer.max(cooldown,this.getRechargeTime()) - rechargeReduction,0);
            stack.set(REDUCE_RECHARGE_TIME,0);
        }
        stack.set(DataComponentTypes.USE_COOLDOWN, new UseCooldownComponent(cooldown, Optional.of(Identifier.of(MagicMod.MOD_ID, String.valueOf(world.random.nextInt(1000000))))));
        player.getItemCooldownManager().set(stack, cooldown);
        stack.set(CASTING_INDEX,castingIndex);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        List<String> spells = stack.get(SPELLS_COMPONENT);
        if (spells != null) {
            @Nullable Integer castingIndex = stack.get(CASTING_INDEX);
            for(int i=0;i<spells.size();i++){
                boolean current = castingIndex != null && castingIndex == i;
                tooltip.add(Text.translatable(Registries.ITEM.get(Identifier.of(spells.get(i))).getTranslationKey()).formatted(current ? Formatting.DARK_PURPLE :Formatting.GOLD));
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
