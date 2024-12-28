package com.delu.ancienttimes.common.tree;

import com.delu.ancienttimes.registries.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.util.RandomSource;

public class MealSapling extends SaplingBlock {

    public MealSapling(AbstractTreeGrower pTreeGrower, BlockBehaviour.Properties pProperties) {
        super(pTreeGrower, pProperties);
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        // Only grow the tree if it's part of a 2x2 configuration
        if (state.getValue(STAGE) == 0) {
            level.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            if (isPartOf2x2(level, pos)) {
                growLargeTree(level, pos, random);
            }
        }

    }

    private boolean isPartOf2x2(Level level, BlockPos pos) {
        // Check for a valid 2x2 sapling arrangement
        return is2x2Sapling(level, pos) || is2x2Sapling(level, pos.offset(-1, 0, 0)) ||
                is2x2Sapling(level, pos.offset(0, 0, -1)) || is2x2Sapling(level, pos.offset(-1, 0, -1));
    }

    private boolean is2x2Sapling(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(this) &&
                level.getBlockState(pos.offset(1, 0, 0)).is(this) &&
                level.getBlockState(pos.offset(0, 0, 1)).is(this) &&
                level.getBlockState(pos.offset(1, 0, 1)).is(this);
    }

    private void growLargeTree(ServerLevel level, BlockPos pos, RandomSource random) {
        // Find the bottom-left corner of the 2x2 sapling arrangement
        BlockPos basePos = findBasePosition(level, pos);

        // Replace the saplings with air
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                level.setBlock(basePos.offset(x, 0, z), Blocks.AIR.defaultBlockState(), 4);
            }
        }

        // Generate the tree using your custom feature
        ConfiguredFeature<?, ?> feature = getLargeTreeFeature(random, level);
        if (feature != null) {
            feature.place(level, level.getChunkSource().getGenerator(), random, basePos);
        }
    }

    private BlockPos findBasePosition(Level level, BlockPos pos) {
        if (is2x2Sapling(level, pos)) return pos;
        if (is2x2Sapling(level, pos.offset(-1, 0, 0))) return pos.offset(-1, 0, 0);
        if (is2x2Sapling(level, pos.offset(0, 0, -1))) return pos.offset(0, 0, -1);
        return pos.offset(-1, 0, -1);
    }

    private ConfiguredFeature<?, ?> getLargeTreeFeature(RandomSource random, Level level) {
        // Fetch the MEAL_TREE feature from ModConfiguredFeatures
        Holder<ConfiguredFeature<?, ?>> featureHolder =
                level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(ModConfiguredFeatures.MEAL_TREE);

        // Return the feature if available
        return featureHolder.value();
    }
}
