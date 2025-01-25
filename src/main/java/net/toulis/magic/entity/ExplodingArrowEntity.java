package net.toulis.magic.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExplodingArrowEntity extends ArrowEntity {
    public ExplodingArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(world, x, y, z, stack, shotFrom);
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
                        null,
                        null,
                        this.getX(),
                        this.getBodyY(0.0625),
                        this.getZ(),
                        4.0F,
                        true,
                        World.ExplosionSourceType.TRIGGER
                );
    }
}
