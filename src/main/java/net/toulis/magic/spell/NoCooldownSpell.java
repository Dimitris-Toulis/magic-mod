package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.toulis.magic.ModComponents;

public class NoCooldownSpell extends Item implements SpellItem {
    public NoCooldownSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player, int wandTier, ItemStack stack) {
        stack.set(ModComponents.EXTRA_COOLDOWN,-1);
    }

    @Override
    public int getCooldown() {
        return -1;
    }

    @Override
    public int getTier() {
        return 3;
    }
}
