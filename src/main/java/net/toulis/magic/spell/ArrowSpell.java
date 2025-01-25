package net.toulis.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.toulis.magic.entity.ExplodingArrowEntity;

public class ArrowSpell extends Item implements SpellItem {
    public ArrowSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        Vec3d pos = player.getEyePos();
        ArrowEntity arrowEntity = new ExplodingArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.ARROW), null);
        arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
        arrowEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 0.0F);
        world.spawnEntity(arrowEntity);
    }

}
