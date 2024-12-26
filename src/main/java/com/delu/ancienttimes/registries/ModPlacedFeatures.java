package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.tree.MealFoliagePlacer;
import com.delu.ancienttimes.common.tree.MealTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacedFeatures {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, AncientTimes.MODID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, AncientTimes.MODID);

    public static final RegistryObject<FoliagePlacerType<MealFoliagePlacer>> MEAL_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("meal_foliage_placer",
                    () -> new FoliagePlacerType<>(MealFoliagePlacer.CODEC));

    public static final RegistryObject<TrunkPlacerType<MealTrunkPlacer>> MEAL_TRUNK_PLACER =
            TRUNK_PLACERS.register("meal_trunk_placer",
                    () -> new TrunkPlacerType<>(MealTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
        TRUNK_PLACERS.register(eventBus);
    }

}