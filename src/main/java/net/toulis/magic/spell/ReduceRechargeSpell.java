package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.toulis.magic.ModComponents;

public class ReduceRechargeSpell extends Item implements SpellItem{
    public ReduceRechargeSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player, int wandTier, ItemStack stack) {
        int reduction = stack.getOrDefault(ModComponents.REDUCE_RECHARGE_TIME,0) + 7;
        stack.set(ModComponents.REDUCE_RECHARGE_TIME,reduction);
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public int getTier() {
        return 1;
    }
}
