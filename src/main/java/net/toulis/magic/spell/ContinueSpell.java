package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

import static net.toulis.magic.ModComponents.*;

public class ContinueSpell extends Item implements SpellItem{
    public ContinueSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player, int wandTier, ItemStack stack) {
        int castingIndex = Objects.requireNonNull(stack.get(CASTING_INDEX));
        List<String> spells = Objects.requireNonNull(stack.get(SPELLS));
        if(castingIndex == spells.size() - 1) return;
        stack.set(CAST_UNTIL, castingIndex+1);
        if(castingIndex != 0) {
            SpellItem previousSpell = (SpellItem) Registries.ITEM.get(Identifier.of(spells.get(castingIndex-1))).asItem();
            int previousExtraCooldown = stack.getOrDefault(EXTRA_COOLDOWN,0);
            if(previousExtraCooldown != -1) stack.set(EXTRA_COOLDOWN, previousExtraCooldown + previousSpell.getCooldown());
        }
    }

    @Override
    public int getCooldown() {
        return -1;
    }

    @Override
    public int getTier() {
        return 1;
    }
}
