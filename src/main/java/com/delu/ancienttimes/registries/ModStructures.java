package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.structure.JungleRuinStructure;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;
import java.util.Objects;

public class ModStructures {

    public static final ResourceKey<Structure> JUNGLE_RUIN_STRUCTURE = create("jungle_ruins");



    public static void generate(BootstapContext<Structure> ctx){
        ctx.register(JUNGLE_RUIN_STRUCTURE, new JungleRuinStructure(
                new Structure.StructureSettings(
                        ctx.lookup(Registries.BIOME).getOrThrow(ModTags.Biomes.JUNGLE_RUIN_CAN_SPAWN),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN
                ),
                ctx.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructurePools.JUNGLE_RUINS_POOL),
                200,
                TrapezoidHeight.of(VerticalAnchor.aboveBottom(30), VerticalAnchor.belowTop(30))
        ));
        
    }

    private static ResourceKey<Structure> create(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
