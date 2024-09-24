package com.delu.ancienttimes.common.item;

import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class StateModificationBlockItem extends ItemNameBlockItem {

    protected final Function<BlockState, BlockState> modificator;
    public StateModificationBlockItem(Block p_41579_, Function<BlockState, BlockState> modificator, Properties p_41580_) {
        super(p_41579_, p_41580_);
        this.modificator = modificator;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockState blockstate = this.getBlock().getStateForPlacement(ctx);
        if (blockstate == null)
            return null;
        blockstate = modificator.apply(blockstate);
        return this.canPlace(ctx, blockstate) ? blockstate : null;
    }
}
