package net.toulis.magic.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ExplodingArrowEntity extends ArrowEntity {
    private final float power;
    public ExplodingArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom, float power) {
        super(world, x, y, z, stack, shotFrom);
        this.power = power;
    }

    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (!this.getWorld().isClient) {
            explode();
        }
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && this.isInGround()) {
            this.explode();
            this.kill((ServerWorld) this.getWorld());
        }
    }

    protected void explode() {
        this.getWorld()
                .createExplosion(
                        this,
                        Explosion.createDamageSource(this.getWorld(), this),
                        null,
                        this.getX(),
                        this.getBodyY(0.0625),
                        this.getZ(),
                        this.power,
                        false,
                        World.ExplosionSourceType.TNT
                );
    }
}
