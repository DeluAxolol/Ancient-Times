package com.delu.ancienttimes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BrushableBlockWithoutFall extends BrushableBlock {
    public BrushableBlockWithoutFall(Block turnsInto, Properties properties, SoundEvent brushSound, SoundEvent brushCompletedSound) {
        super(turnsInto, properties, brushSound, brushCompletedSound);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Do not make the block fall
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BrushableBlockEntity brushableBlockEntity) {
            brushableBlockEntity.checkReset();
        }
    }
}