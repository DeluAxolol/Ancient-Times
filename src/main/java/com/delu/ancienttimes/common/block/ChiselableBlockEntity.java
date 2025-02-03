package com.delu.ancienttimes.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ChiselableBlockEntity extends BrushableBlockEntity {
    public ChiselableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public boolean brush(long pStartTick, PlayerEntity pPlayer, Direction pHitDirection) {
        ItemStack heldItem = pPlayer.getMainHandStack();

        // todo: Fix this
        return true;
    }
}
