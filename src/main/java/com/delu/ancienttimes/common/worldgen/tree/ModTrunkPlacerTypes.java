package com.delu.ancienttimes.common.worldgen.tree;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.worldgen.tree.custom.MealTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, AncientTimes.MODID);

    public static final RegistryObject<TrunkPlacerType<MealTrunkPlacer>> MEAL_TRUNK_PLACER =
            TRUNK_PLACERS.register("meal_trunk_placer", () -> new TrunkPlacerType<>(MealTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
    }
}
