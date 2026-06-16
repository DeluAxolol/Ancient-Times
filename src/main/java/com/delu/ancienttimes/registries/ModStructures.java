package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;
import java.util.Objects;

public class ModStructures {

    public static final ResourceKey<Structure> JUNGLE_RUIN_STRUCTURE = create("jungle_ruins");



    public static void generate(BootstapContext<Structure> ctx){
        ctx.register(JUNGLE_RUIN_STRUCTURE, new JigsawStructure(
                new Structure.StructureSettings(
                        ctx.lookup(Registries.BIOME).getOrThrow(ModTags.Biomes.JUNGLE_RUIN_CAN_SPAWN),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN
                ),
                ctx.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructurePools.JUNGLE_RUINS_POOL),
                7,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));
        
    }

    private static ResourceKey<Structure> create(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
