package com.delu.ancienttimes.common.tree;


import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModPlacedFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class MealTrunkPlacer extends TrunkPlacer {
    public MealTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
    }
    public static final Codec<MealTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            trunkPlacerParts(instance).apply(instance, MealTrunkPlacer::new));

    @Override
    protected TrunkPlacerType<?> type() {
        return ModPlacedFeatures.MEAL_TRUNK_PLACER.get(); // Register a new TrunkPlacerType for this
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> replacer,
                                                            RandomSource random, int height, BlockPos pos, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> foliagePositions = new ArrayList<>();
        int base = 5 + random.nextInt(2);
        // Base (4x4) trunk
        for (int y = 0; y < base-2; y++) {
            placeSquareTrunk(reader, replacer, pos.above(y), 4, config, false, random);
        }

        // Second part (reduced 4x4) trunk
        for (int y = base-2; y < base; y++) {
            placeSquareTrunk(reader, replacer, pos.above(y), 4, config, true, random);
        }
        for (int y = base; y < base + heightRandB; y++) {
            placeSquareTrunk(reader, replacer, pos.above(y), 2, config, false, random);
        }
        int leftrightoffset = random.nextInt(2);
        for(int i = 0; i < 3 + random.nextInt(2); i++) {
            placeLog(reader, replacer, random, pos.offset(2, i, leftrightoffset*-1), config);
        }
        leftrightoffset = random.nextInt(2);
        for(int i = 0; i < 3 + random.nextInt(2); i++) {
            placeLog(reader, replacer, random, pos.offset(leftrightoffset*-1, i, -3), config);
        }
        leftrightoffset = random.nextInt(2);
        for(int i = 0; i < 3 + random.nextInt(2); i++) {
            placeLog(reader, replacer, random, pos.offset(-3, i, leftrightoffset*-1), config);
        }
        leftrightoffset = random.nextInt(2);
        for(int i = 0; i < 3 + random.nextInt(2); i++) {
            placeLog(reader, replacer, random, pos.offset(leftrightoffset*-1, i, 2), config);
        }



        // Add foliage positions
        foliagePositions.add(new FoliagePlacer.FoliageAttachment(pos.above(base + heightRandB), 1, false));
        return foliagePositions;
    }

    private void placeSquareTrunk(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> replacer,
                                  BlockPos pos, int size, TreeConfiguration config, boolean removeEdges, RandomSource random) {
        int offset = size / 2;
        for (int x = -offset; x < offset; x++) {  // Adjusted to <= to cover the full range.
            for (int z = -offset; z < offset; z++) {  // Same as above for z axis.
                // If removeEdges is true, skip the four corners of the square.
                if (!removeEdges || !((x == -offset && z == -offset) || (x == offset - 1 && z == -offset)
                        || (x == -offset && z == offset - 1) || (x == offset - 1 && z == offset - 1))) {
                    placeLog(reader, replacer, random, pos.offset(x, 0, z), config);
                }
            }
        }
    }


}

