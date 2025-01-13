package com.delu.ancienttimes.common.entity;

import com.delu.ancienttimes.registries.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
public class ModHangingSignBlockEntity extends SignBlockEntity {
    public ModHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityTypes.MOD_HANGING_SIGN.get(), pPos, pBlockState);
    }
    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntityTypes.MOD_HANGING_SIGN.get();
    }
}