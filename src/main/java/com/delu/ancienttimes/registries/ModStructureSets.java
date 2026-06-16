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



    public static final ResourceKey<StructureSet> JUNGLE_RUINS_SET = create("jungle_ruins_set");

    public static void generate(BootstapContext<StructureSet> ctx){
        ctx.register(JUNGLE_RUINS_SET, new StructureSet(
                ctx.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.JUNGLE_RUIN_STRUCTURE),
                new RandomSpreadStructurePlacement(30, 15, RandomSpreadType.LINEAR, 342342)
        ));
    }


    private static ResourceKey<StructureSet> create(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
