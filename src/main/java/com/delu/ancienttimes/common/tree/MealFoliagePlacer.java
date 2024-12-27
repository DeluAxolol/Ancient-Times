package com.delu.ancienttimes.common.tree;
import com.delu.ancienttimes.registries.ModPlacedFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import java.util.function.BiConsumer;


public class MealFoliagePlacer extends FoliagePlacer {
    // Define codec for serialization
    public static final Codec<MealFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            foliagePlacerParts(instance).apply(instance, MealFoliagePlacer::new)
    );
    public MealFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModPlacedFeatures.MEAL_FOLIAGE_PLACER.get(); // Replace with your foliage placer registration
    }


    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random,
                                 TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment,
                                 int foliageHeight, int foliageRadius, int offset) {
        BlockPos trunkBase = attachment.pos();
        // Generate three layers of foliage with decreasing radii
        placeLayer(level, blockSetter, random, config, trunkBase, 4, 0);  // Bottom layer with radius 4
        placeLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 4, 0);  // Bottom layer with radius 4
        placeLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 4, 0);  // Bottom layer with radius 4
        placeLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 4, 0);  // Bottom layer with radius 4
        placeLayer(level, blockSetter, random, config, trunkBase, 3, 1);  // Middle layer with radius 3
        placeLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 3, 1);  // Middle layer with radius 3
        placeLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 3, 1);  // Middle layer with radius 3
        placeLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 3, 1);  // Middle layer with radius 3
        placeLayer(level, blockSetter, random, config, trunkBase, 2, 2);  // Top layer with radius 2
        placeLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 2, 2);  // Top layer with radius 2
        placeLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 2, 2);  // Top layer with radius 2
        placeLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 2, 2);  // Top layer with radius 2
        placeBottomLayer(level, blockSetter, random, config, trunkBase, 4);  // Bottom layer with radius 4
        placeBottomLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 4);  // Bottom layer with radius 4
        placeBottomLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 4);  // Bottom layer with radius 4
        placeBottomLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 4);  // Bottom layer with radius 4
        // Vines
        placeVineLayer(level, blockSetter, random, config, trunkBase, 4, 0);  // Bottom layer with radius 4
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 4, 0);  // Bottom layer with radius 4
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 4, 0);  // Bottom layer with radius 4
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 4, 0);  // Bottom layer with radius 4
        placeVineLayer(level, blockSetter, random, config, trunkBase, 3, 1);  // Middle layer with radius 3
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 3, 1);  // Middle layer with radius 3
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 3, 1);  // Middle layer with radius 3
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 3, 1);  // Middle layer with radius 3
        placeVineLayer(level, blockSetter, random, config, trunkBase, 2, 2);  // Top layer with radius 2
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 2, 2);  // Top layer with radius 2
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 2, 2);  // Top layer with radius 2
        placeVineLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 2, 2);  // Top layer with radius 2
        placeBottomVineLayer(level, blockSetter, random, config, trunkBase, 4);  // Bottom layer with radius 4
        placeBottomVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , -1), 4);  // Bottom layer with radius 4
        placeBottomVineLayer(level, blockSetter, random, config, trunkBase.offset(-1, 0 , 0), 4);  // Bottom layer with radius 4
        placeBottomVineLayer(level, blockSetter, random, config, trunkBase.offset(0, 0 , -1), 4);  // Bottom layer with radius 4

    }

    private void placeVinesAround(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (random.nextInt(4) == 0) { // 25% chance to place a vine
                BlockPos vinePos = pos.relative(direction);
                if (level.isStateAtPosition(vinePos, BlockState::isAir)) {
                    foliageSetter.set(vinePos, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true));
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 3;  // We want 3 layers of foliage
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;  // For simplicity, we won't skip any locations in this example
    }

    private void placeVineLayer(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random,
                            TreeConfiguration config, BlockPos trunkBase, int radius, int heightOffset) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        // Generate a circle of leaves at the given radius and height offset
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (Math.pow(x, 2) + Math.pow(z, 2) <= Math.pow(radius, 2)) {
                    mutablePos.setWithOffset(trunkBase, x, heightOffset, z);
                    placeVinesAround(level, blockSetter, random, mutablePos);
                }
            }
        }

    }
    private void placeLayer(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random,
                            TreeConfiguration config, BlockPos trunkBase, int radius, int heightOffset) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        // Generate a circle of leaves at the given radius and height offset
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (Math.pow(x, 2) + Math.pow(z, 2) <= Math.pow(radius, 2)) {
                    mutablePos.setWithOffset(trunkBase, x, heightOffset, z);
                    tryPlaceLeaf(level, blockSetter, random, config, mutablePos);
                }
            }
        }

    }
    private void placeBottomLayer(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random,
                                  TreeConfiguration config, BlockPos trunkBase, int radius) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        // Generate a circle of leaves at the given radius and height offset
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                // Check if the position is within the circle radius
                if (Math.pow(x, 2) + Math.pow(z, 2) <= Math.pow(radius, 2)) {
                    mutablePos.setWithOffset(trunkBase, x, -1, z);

                    // Check if the block above this position has a neighboring air block in both X and Z directions
                    BlockPos abovePos = mutablePos.above();
                    boolean hasAirNeighborX = level.isStateAtPosition(abovePos.east(), BlockState::isAir) || level.isStateAtPosition(abovePos.west(), BlockState::isAir);
                    boolean hasAirNeighborZ = level.isStateAtPosition(abovePos.north(), BlockState::isAir) || level.isStateAtPosition(abovePos.south(), BlockState::isAir);

                    // Only place the leaf if both conditions are met
                    if (hasAirNeighborX && hasAirNeighborZ) {
                        tryPlaceLeaf(level, blockSetter, random, config, mutablePos);
                    }
                }
            }
        }
    }
    private void placeBottomVineLayer(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random,
                                  TreeConfiguration config, BlockPos trunkBase, int radius) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        // Generate a circle of leaves at the given radius and height offset
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                // Check if the position is within the circle radius
                if (Math.pow(x, 2) + Math.pow(z, 2) <= Math.pow(radius, 2)) {
                    mutablePos.setWithOffset(trunkBase, x, -1, z);

                    // Check if the block above this position has a neighboring air block in both X and Z directions

                    // Only place the leaf if both conditions are met
                        placeVinesAround(level, blockSetter, random, mutablePos);

                }
            }
        }
    }
    

}
