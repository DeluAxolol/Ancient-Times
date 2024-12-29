package com.delu.ancienttimes.common.block;

import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
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

import javax.annotation.ParametersAreNonnullByDefault;



public class RavenheadSprouts extends CropBlock {
    public RavenheadSprouts(Properties p_52247_) {
        super(p_52247_);
    }
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;

    public boolean canSurvive(BlockState state, LevelReader p_51029_, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) {//Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return (state.hasProperty(getAgeProperty()) && state.getValue(getAgeProperty()) == getMaxAge() && p_51029_.getBlockState(blockpos).is(BlockTags.DIRT)) || p_51029_.getBlockState(blockpos).canSustainPlant(p_51029_, blockpos, Direction.UP, this);
        }
        return this.mayPlaceOn(p_51029_.getBlockState(blockpos), p_51029_, blockpos);
    }

    @NotNull
    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }


    @Override
    public int getMaxAge() {
        return 4;
    }

    @ParametersAreNonnullByDefault
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @ParametersAreNonnullByDefault
    @Override
    protected int getBonemealAgeIncrease(Level p_52262_) {
        return 1;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, net.minecraft.world.entity.player.Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS; // Return early for the client side.
        }

        int age = state.getValue(getAgeProperty());
        ItemStack heldItem = player.getItemInHand(hand);

        if (age == 3 || age == 4) {
            ItemStack drop = (age == 3)
                    ? new ItemStack(ForgeRegistries.ITEMS.getValue(ModItems.RAVENHEADS_FRUIT.getId())) // Replace with your registry reference.
                    : new ItemStack(ForgeRegistries.ITEMS.getValue(ModItems.ROTTEN_RAVENHEADS_FRUIT.getId())); // Replace with your registry reference.

            if (!player.getInventory().add(drop)) {
                // Drop the item in the world if the player's inventory is full.
                player.drop(drop, false);
            }

            // Reset the crop to age 0.
            level.setBlock(pos, this.defaultBlockState().setValue(getAgeProperty(), 0), 2);

            return InteractionResult.SUCCESS;
        }

        // If right-clicking with bonemeal, allow the normal bonemeal interaction.
        if (heldItem.is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        }

        return InteractionResult.CONSUME; // Consume the interaction, but do nothing if not age 3 or 4.
    }

}
