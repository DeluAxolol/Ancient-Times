package com.delu.ancienttimes.common.block;

import com.delu.ancienttimes.registries.ModBlocks;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
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

    @Override
    public boolean canSurvive(BlockState state, LevelReader p_51029_, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockBelow = p_51029_.getBlockState(blockpos);

        if (state.getBlock() == this) {
            // Check if the block below is dirt or if the block below is a RavenheadSprouts with an age of 1 or higher
            return (blockBelow.is(BlockTags.DIRT)) ||
                    (blockBelow.getBlock() == this && blockBelow.getValue(getAgeProperty()) >= 1) ||
                    blockBelow.canSustainPlant(p_51029_, blockpos, Direction.UP, this);
        }
        // Ensure that it can place on the block below
        return this.mayPlaceOn(p_51029_.getBlockState(blockpos), p_51029_, blockpos);
    }

    @NotNull
    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    // Implement random growth, similar to sugar cane
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        int age = state.getValue(getAgeProperty());
        if (age > 2) { // 10% chance to grow randomly
            BlockPos above = pos.above();
            BlockPos below = pos.below();

            if (level.isEmptyBlock(above) && !level.getBlockState(below).is(ModBlocks.RAVENHEAD_SPROUTS.get())) {
                // Place the block above at the next stage
                level.setBlock(above, this.defaultBlockState().setValue(getAgeProperty(), 1), 2);
            }
        }
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
        int randomBinary = new java.util.Random().nextInt(2);

        if (heldItem.is(Items.SHEARS) && age == 1) {
            // Drop Ravenhead Thorns on the ground
            ItemStack drop = new ItemStack(ForgeRegistries.ITEMS.getValue(ModItems.RAVENHEADS_THORNS.getId()), 1);
            Block.popResource(level, pos, drop);

            // Reset the crop to stage 0
            level.setBlock(pos, this.defaultBlockState().setValue(getAgeProperty(), 0), 2);

            // Play shear sound
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }

        if (age == 3 || age == 4) {
            ItemStack drop = (age == 3)
                    ? new ItemStack(ForgeRegistries.ITEMS.getValue(ModItems.RAVENHEADS_FRUIT.getId()), 3 + randomBinary) // Replace with your registry reference.
                    : new ItemStack(ForgeRegistries.ITEMS.getValue(ModItems.ROTTEN_RAVENHEADS_FRUIT.getId()), 3 + randomBinary); // Replace with your registry reference.

            if (!player.getInventory().add(drop)) {
                // Drop the item in the world if the player's inventory is full.
                player.drop(drop, false);
            }

            // Reset the crop to age 0.
            level.setBlock(pos, this.defaultBlockState().setValue(getAgeProperty(), 1), 2);

            return InteractionResult.SUCCESS;
        }

        // If right-clicking with bonemeal, allow the normal bonemeal interaction.
        if (heldItem.is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        }

        return InteractionResult.CONSUME; // Consume the interaction, but do nothing if not age 3 or 4.
    }

}
