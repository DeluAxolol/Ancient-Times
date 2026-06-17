package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.Objects;

public class ModStructureSets {


    public static final ResourceKey<StructureSet> JUNGLE_RUINS = create("jungle_ruins");
    public static final ResourceKey<StructureSet> MUDDY_HUT = create("muddy_hut");
    public static final ResourceKey<StructureSet> FROZEN_TOWER = create("frozen_tower");

    public static void generate(BootstapContext<StructureSet> ctx) {
        ctx.register(JUNGLE_RUINS, new StructureSet(
                ctx.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.JUNGLE_RUIN),
                new RandomSpreadStructurePlacement(30, 15, RandomSpreadType.LINEAR, 342342)
        ));

        ctx.register(MUDDY_HUT, new StructureSet(
                ctx.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.MUDDY_HUT),
                new RandomSpreadStructurePlacement(30, 15, RandomSpreadType.LINEAR, 182840)
        ));

        ctx.register(FROZEN_TOWER, new StructureSet(
                ctx.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.FROZEN_TOWER),
                new RandomSpreadStructurePlacement(30, 15, RandomSpreadType.LINEAR, 193750)
        ));
    }


    private static ResourceKey<StructureSet> create(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
