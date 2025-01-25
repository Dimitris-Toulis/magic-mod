package net.toulis.magic.spell;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class LightningSpell extends Item implements SpellItem {
    public LightningSpell(Settings settings) {
        super(settings);
    }

    public void cast(World world, PlayerEntity player){
        Vec3d pos = player.getEyePos();
        Vec3d end = pos.add(player.getRotationVector(player.getPitch(), player.getYaw()).multiply(5*player.getBlockInteractionRange()));
        BlockHitResult blockHitResult =  world.raycast(new RaycastContext(pos, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.SOURCE_ONLY, player));

        LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            lightningBolt.setPosition(blockHitResult.getPos());
            world.spawnEntity(lightningBolt);
        } else {
            BlockPos frontOfPlayer = player.getBlockPos().offset(player.getFacing(), 5);
            lightningBolt.setPosition(frontOfPlayer.toCenterPos());
            world.spawnEntity(lightningBolt);
        }
    }

    public int getCooldown() {
        return 10;
    }
}
