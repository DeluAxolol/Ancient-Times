package com.delu.ancienttimes.common.worldgen.tree.custom;

import com.delu.ancienttimes.common.worldgen.tree.ModTrunkPlacerTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class MealTrunkPlacer extends TrunkPlacer {
    public static final Codec<MealTrunkPlacer> CODEC = RecordCodecBuilder.create(mealTrunkPlacerInstance ->
            trunkPlacerParts(mealTrunkPlacerInstance).apply(mealTrunkPlacerInstance, MealTrunkPlacer::new));

    public MealTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.MEAL_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter,
                                                            RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        // THIS IS WHERE THE BLOCK PLACING LOGIC IS!
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        int height = pFreeTreeHeight + pRandom.nextInt(heightRandA, heightRandA + 3) + pRandom.nextInt(heightRandB - 1, heightRandB + 1);

        placeLog(pLevel, pBlockSetter, pRandom, pPos, pConfig);

        for(int i = 0; i < height; i++) {
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);

            if(i % 2 == 0 && pRandom.nextBoolean()) {
                if(pRandom.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.NORTH, x), pConfig);
                    }
                }

                if(pRandom.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.SOUTH, x), pConfig);
                    }
                }

                if(pRandom.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.EAST, x), pConfig);
                    }
                }

                if(pRandom.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.WEST, x), pConfig);
                    }
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));
    }
}
