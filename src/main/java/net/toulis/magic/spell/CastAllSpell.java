package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Objects;

import static net.toulis.magic.ModComponents.CAST_UNTIL;
import static net.toulis.magic.ModComponents.SPELLS;

public class CastAllSpell extends Item implements SpellItem {
    public CastAllSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player, int wandTier, ItemStack stack) {
        stack.set(CAST_UNTIL, Objects.requireNonNull(stack.get(SPELLS)).size()-1);
    }

    @Override
    public int getCooldown() {
        return -1;
    }

    @Override
    public int getTier() {
        return 2;
    }
}
