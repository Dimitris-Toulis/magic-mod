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

public class ExplodingArrowSpell extends Item implements SpellItem {
    public ExplodingArrowSpell(Settings settings) {
        super(settings);
    }

    public void cast(World world, PlayerEntity player, int wandTier, ItemStack stack) {
        Vec3d pos = player.getEyePos().add(player.getRotationVector().multiply(1.0));
        ArrowEntity arrowEntity = new ExplodingArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.ARROW), null, 3.0F + wandTier);
        arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
        arrowEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 0.0F);
        world.spawnEntity(arrowEntity);
    }

    public int getCooldown() {
        return 7;
    }
    @Override
    public int getTier() {
        return 1;
    }
}
