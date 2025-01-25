package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.toulis.magic.MagicMod;

public class FireballSpell extends Item implements SpellItem {
    public FireballSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        MagicMod.LOGGER.info("Fireball cast");
    }
    public int getCooldown() {
        return 7;
    }
}
