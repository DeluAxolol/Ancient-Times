package com.delu.ancienttimes.common.block;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class MardFlower extends CropBlock {
    public MardFlower(Properties p_52247_) {
        super(p_52247_);
    }

    public boolean canSurvive(BlockState state, LevelReader p_51029_, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) {//Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return (state.hasProperty(getAgeProperty()) && state.getValue(getAgeProperty()) == getMaxAge() && p_51029_.getBlockState(blockpos).is(BlockTags.DIRT)) || p_51029_.getBlockState(blockpos).canSustainPlant(p_51029_, blockpos, Direction.UP, this);
        }
        return this.mayPlaceOn(p_51029_.getBlockState(blockpos), p_51029_, blockpos);
    }

    @NotNull
    @Override
    protected IntegerProperty getAgeProperty() {
        return BlockStateProperties.AGE_3;
    }


    @Override
    public int getMaxAge() {
        return 3;
    }

    @ParametersAreNonnullByDefault
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.AGE_3);
    }

    @ParametersAreNonnullByDefault
    @Override
    protected int getBonemealAgeIncrease(Level p_52262_) {
        return 1;
    }
}
