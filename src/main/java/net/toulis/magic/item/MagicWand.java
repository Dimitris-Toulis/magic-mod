package net.toulis.magic.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toulis.magic.ModComponents;

import java.util.List;

public class MagicWand extends Item {
    public MagicWand(Integer tier, Settings settings) {
        super(settings);
        this.tier = tier;
    }
    private final Integer tier;

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        // Temporary functionality

        // Ensure we don't spawn the lightning only on the client.
        // This is to prevent desync.
        if (world.isClient) {
            return ActionResult.PASS;
        }

        BlockPos frontOfPlayer = user.getBlockPos().offset(user.getHorizontalFacing(), 10);

        // Spawn the lightning bolt.
        LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightningBolt.setPosition(frontOfPlayer.toCenterPos());
        world.spawnEntity(lightningBolt);

        ItemStack itemStack = user.getStackInHand(hand);
        itemStack.decrement(1);
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        List<String> spells = stack.get(ModComponents.SPELLS_COMPONENT);
        if (spells != null) {
            spells.forEach(spell -> tooltip.add(Text.translatable(Registries.ITEM.get(Identifier.tryParse(spell)).getTranslationKey()).formatted(Formatting.GOLD)));
        }
    }

    public int getMaxSpells(){
        return switch (tier) {
            case 1 -> 3;
            case 2 -> 8;
            case 3 -> 14;
            default -> 0;
        };
    }

}
