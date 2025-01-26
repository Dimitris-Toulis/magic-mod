package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface SpellItem {
    void cast(World world, PlayerEntity player, int wandTier, ItemStack stack);
    int getCooldown();
    int getTier();
}
