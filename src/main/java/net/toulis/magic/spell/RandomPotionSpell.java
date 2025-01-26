package net.toulis.magic.spell;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.List;

public class RandomPotionSpell extends Item implements SpellItem{
    public RandomPotionSpell(Settings settings) {
        super(settings);
    }

    @Override
    public void cast(World world, PlayerEntity player, int wandTier, ItemStack stack) {
        ItemStack potion = new ItemStack(Items.SPLASH_POTION);
        List<RegistryEntry.Reference<Potion>> potions = Registries.POTION.streamEntries().filter(p -> !p.value().hasInstantEffect()).toList();
        potion.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potions.get(world.random.nextBetween(0, potions.size() - 1))));
        ProjectileEntity.spawnWithVelocity(PotionEntity::new, (ServerWorld) world, potion, player, -20.0F, 0.5F, 1.0F);
    }

    @Override
    public int getCooldown() {
        return 7;
    }

    @Override
    public int getTier() {
        return 1;
    }
}
