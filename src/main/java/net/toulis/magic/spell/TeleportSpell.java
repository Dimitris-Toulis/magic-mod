package net.toulis.magic.spell;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class TeleportSpell extends Item implements SpellItem{
    public TeleportSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player, int wandTier, ItemStack stack) {
        Vec3d pos = player.getEyePos();
        Vec3d end = pos.add(player.getRotationVector(player.getPitch(), player.getYaw()).multiply(5*player.getBlockInteractionRange()));
        BlockHitResult blockHitResult =  world.raycast(new RaycastContext(pos, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));

        if (blockHitResult.getType() != HitResult.Type.MISS) {
            Vec3d tpPosition;
            Direction side = blockHitResult.getSide();
            if(side == Direction.DOWN || side == Direction.UP) {
                tpPosition = blockHitResult.getPos();
            } else {
                tpPosition = blockHitResult.getBlockPos().offset(blockHitResult.getSide()).toBottomCenterPos();
            }
            player.teleport(tpPosition.getX(),tpPosition.getY(),tpPosition.getZ(),true);
        } else {
            Vec3d tpPosition = player.getEyePos().offset(player.getFacing(), 5).offset(Direction.DOWN,1);
            player.requestTeleport(tpPosition.getX(),tpPosition.getY(),tpPosition.getZ());
            world.sendEntityStatus(player, EntityStatuses.ADD_PORTAL_PARTICLES);
        }
    }

    @Override
    public int getCooldown() {
        return 5;
    }

    @Override
    public int getTier() {
        return 2;
    }
}
