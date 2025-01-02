package net.toulis.magic.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
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
                Box.of(pos.toCenterPos(),5.0,5.0,5.0),
                entity -> entity.getItemAge() > 20 && entity.getStack().getItem() instanceof MagicWand && !entity.isInvulnerable()
        );
        for(ItemEntity wand: droppedItems) {
            ItemStack stack = wand.getStack();
            if(!stack.getComponents().contains(ModComponents.SPELLS_COMPONENT)){
                continue;
            }
            for(String spell: stack.remove(ModComponents.SPELLS_COMPONENT)){
                RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(spell));
                wand.dropItem((ServerWorld) world, Registries.ITEM.get(registryKey)).setInvulnerable(true);
            }
            wand.setInvulnerable(true);
            LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightningBolt.setPosition(wand.getPos());
            world.spawnEntity(lightningBolt);
        }
    }
}

