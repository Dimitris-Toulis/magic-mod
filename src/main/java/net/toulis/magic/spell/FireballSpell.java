package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.toulis.magic.MagicMod;

public class FireballSpell extends SpellItem{
    public FireballSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        MagicMod.LOGGER.info("Fireball cast");
    }
}
