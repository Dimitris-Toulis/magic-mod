package net.toulis.magic.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toulis.magic.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class MagicNullifier extends BlockWithEntity {
    public MagicNullifier(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends MagicNullifier> getCodec() {
        return createCodec(MagicNullifier::new);
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MagicNullifierBlockEntity(pos, state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.MAGIC_NULLIFIER_BLOCK_ENTITY, MagicNullifierBlockEntity::tick);
    }
}
