package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireballSpell extends Item implements SpellItem {
    public FireballSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        FireballEntity fireballEntity = new FireballEntity(world,player,new Vec3d(0,0,0),3);
        fireballEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 0.0F);
        world.spawnEntity(fireballEntity);
    }
    public int getCooldown() {
        return 7;
    }
    @Override
    public int getTier() {
        return 1;
    }
}
