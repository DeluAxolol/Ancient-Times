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

    public static final ResourceKey<Structure> JUNGLE_RUIN = create("jungle_ruins");
    public static final ResourceKey<Structure> MUDDY_HUT = create("muddy_hut");
    public static final ResourceKey<Structure> FROZEN_TOWER = create("frozen_tower");



    public static void generate(BootstapContext<Structure> ctx){
        ctx.register(JUNGLE_RUIN, new JigsawStructure(
                new Structure.StructureSettings(
                        ctx.lookup(Registries.BIOME).getOrThrow(ModTags.Biomes.JUNGLE_RUIN_CAN_SPAWN),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN
                ),
                ctx.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructurePools.JUNGLE_RUINS),
                7,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        ctx.register(MUDDY_HUT, new JigsawStructure(
                new Structure.StructureSettings(
                        ctx.lookup(Registries.BIOME).getOrThrow(ModTags.Biomes.MUDDY_HUT_CAN_SPAWN),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN
                ),
                ctx.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructurePools.MUDDY_HUT),
                7,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        ctx.register(FROZEN_TOWER, new JigsawStructure(
                new Structure.StructureSettings(
                        ctx.lookup(Registries.BIOME).getOrThrow(ModTags.Biomes.FROZEN_TOWER_CAN_SPAWN),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN
                ),
                ctx.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructurePools.FROZEN_TOWER),
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
