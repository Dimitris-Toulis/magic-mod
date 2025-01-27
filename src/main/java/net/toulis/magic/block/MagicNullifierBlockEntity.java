package net.toulis.magic.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DamageResistantComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.toulis.magic.ModBlockEntities;
import net.toulis.magic.ModComponents;
import net.toulis.magic.item.MagicWand;

import java.util.List;
import java.util.Objects;

public class MagicNullifierBlockEntity extends BlockEntity {
    public MagicNullifierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGIC_NULLIFIER_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MagicNullifierBlockEntity blockEntity) {
        if(world.isClient) {
            return;
        }
        List<ItemEntity> droppedItems = world.getEntitiesByType(
                TypeFilter.instanceOf(ItemEntity.class),
                Box.of(pos.toCenterPos(),10.0,10.0,10.0),
                entity -> entity.getItemAge() > 20 && entity.getStack().getItem() instanceof MagicWand && entity.getStack().contains(ModComponents.SPELLS_COMPONENT)
        );
        for(ItemEntity wand: droppedItems) {
            List<String> spells = wand.getStack().get(ModComponents.SPELLS_COMPONENT);
            if(spells != null) {
                for (String spell : spells) {
                    Item item = Registries.ITEM.get(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(spell)));
                    if(item == null) continue;
                    ItemStack stack = new ItemStack(item,1);
                    stack.set(DataComponentTypes.DAMAGE_RESISTANT, new DamageResistantComponent(DamageTypeTags.IS_FIRE));
                    Objects.requireNonNull(wand.dropStack((ServerWorld) world, stack)).setInvulnerable(true);
                }
                wand.getStack().remove(ModComponents.SPELLS_COMPONENT);
            }
            wand.setInvulnerable(true);
            wand.getStack().set(DataComponentTypes.DAMAGE_RESISTANT, new DamageResistantComponent(DamageTypeTags.IS_FIRE));
            LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightningBolt.setPosition(wand.getPos());
            world.spawnEntity(lightningBolt);
        }
    }
}

