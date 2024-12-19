package com.delu.ancienttimes.common.worldgen.tree;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.worldgen.tree.custom.MealFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, AncientTimes.MODID);

    public static final RegistryObject<FoliagePlacerType<MealFoliagePlacer>> WALNUT_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("meal_foliage_placer", () -> new FoliagePlacerType<>(MealFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
    }
}
