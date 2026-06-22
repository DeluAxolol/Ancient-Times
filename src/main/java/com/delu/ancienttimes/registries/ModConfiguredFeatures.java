package com.delu.ancienttimes.registries;
import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.server.tree.MealFoliagePlacer;
import com.delu.ancienttimes.server.tree.MealTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEAL_TREE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            new ResourceLocation(AncientTimes.ID, "meal_tree")
    );


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(
                MEAL_TREE,
                new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.MEAL_LOG.get()),
                        new MealTrunkPlacer(12, 1, 1), // Custom trunk placer for the structure
                        BlockStateProvider.simple(ModBlocks.MEAL_LEAVES.get()),
                        new MealFoliagePlacer(UniformInt.of(3, 5), UniformInt.of(1, 2)), // Random range
                        new TwoLayersFeatureSize(2, 0, 2)
                ) // Adds vines to leaves
                        //.ignoreVines()
                        .build())
        );
    }
}