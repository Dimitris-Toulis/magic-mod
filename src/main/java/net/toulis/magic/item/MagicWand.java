package net.toulis.magic.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UseCooldownComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toulis.magic.MagicMod;
import net.toulis.magic.ModBlocks;
import net.toulis.magic.spell.ContinueSpell;
import net.toulis.magic.spell.SpellItem;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
        if(detectNullifier(world,player,stack)) return;
        int castingIndex = stack.getOrDefault(CASTING_INDEX,0);

        SpellItem spell = (SpellItem) Registries.ITEM.get(Identifier.of(spellList.get(castingIndex))).asItem();
        spell.cast(world,player,this.tier,stack);
        castingIndex = (castingIndex + 1) % spellList.size();

        int spellCooldown = spell.getCooldown();
        int extraCooldown = stack.getOrDefault(EXTRA_COOLDOWN,0);
        int cooldown = spellCooldown == -1 || extraCooldown == -1 ? 0 : spellCooldown+this.getCooldown()+extraCooldown;
        if(castingIndex == 0){
            int rechargeReduction = stack.getOrDefault(REDUCE_RECHARGE_TIME,0);
            cooldown = Integer.max(Integer.max(cooldown,this.getRechargeTime()) - rechargeReduction,0);
            stack.remove(REDUCE_RECHARGE_TIME);
            stack.remove(EXTRA_COOLDOWN);
        }
        stack.set(CASTING_INDEX,castingIndex);
        if(extraCooldown != -1 && !(spell instanceof ContinueSpell)){
            stack.remove(EXTRA_COOLDOWN);
        }

        SpellItem nextSpell = (SpellItem) Registries.ITEM.get(Identifier.of(spellList.get(castingIndex))).asItem();
        if(nextSpell instanceof ContinueSpell && castingIndex != 0) {
            cast(world,player,stack);
            return;
        }
        if(cooldown != 0){
            stack.set(DataComponentTypes.USE_COOLDOWN, new UseCooldownComponent(cooldown, Optional.of(Identifier.of(MagicMod.MOD_ID, String.valueOf(world.random.nextInt(1000000))))));
            player.getItemCooldownManager().set(stack, cooldown);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected){
        if(world.isClient){
            return;
        }
        if(!(entity instanceof PlayerEntity)){
            return;
        }
        Integer castUntil = stack.get(CAST_UNTIL);
        if(castUntil != null) {
            boolean coolingDown = ((PlayerEntity) entity).getItemCooldownManager().isCoolingDown(stack);
            if(coolingDown) return;
            int castingIndex = stack.getOrDefault(CASTING_INDEX,0);
            MagicMod.LOGGER.info("Casting! {}, {}",castingIndex,castUntil);
            cast(world,(PlayerEntity) entity,stack);
            Integer newCastUntil = stack.get(CAST_UNTIL);
            castUntil = newCastUntil == null ? castUntil : newCastUntil;
            if(castUntil == castingIndex) {
                stack.remove(CAST_UNTIL);
            }
        }
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

    private boolean detectNullifier(World world, PlayerEntity player, ItemStack stack){
        boolean nullifier_close = false;
        BlockPos playerPos = player.getBlockPos();
        int radius = 10;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = playerPos.add(x, y, z);
                    if (world.getBlockState(checkPos).getBlock() == ModBlocks.MAGIC_NULLIFIER) {
                        nullifier_close = true;
                    }
                }
            }
        }
        if(!nullifier_close) return false;
        LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightningBolt.setPosition(player.getPos());
        world.spawnEntity(lightningBolt);
        Objects.requireNonNull(player.dropItem(stack, false)).setInvulnerable(true);
        player.getInventory().removeOne(stack);
        return true;
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
