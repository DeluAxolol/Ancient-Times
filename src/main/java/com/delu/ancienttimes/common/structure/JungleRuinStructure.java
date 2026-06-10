package com.delu.ancienttimes.common.structure;

import com.delu.ancienttimes.common.util.FilterHolderSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JungleRuinStructure extends Structure {

    public static final MapCodec<StructureSettings> CUSTOM_STRUCTURE_SETTINGS_CODEC = RecordCodecBuilder.mapCodec(
            codecBuilder -> codecBuilder.group(
                            // This is where we swapped in our custom codec that will apply the exclude structure tag to remove entries from the has structure tag.
                            FilterHolderSet.codec(Registries.BIOME, Biome.CODEC, false).fieldOf("biomes").forGetter(x -> x.biomes() instanceof FilterHolderSet<Biome> filterHolderSet ? filterHolderSet : new FilterHolderSet<>(x.biomes(), HolderSet.direct(List.of()))),
                            Codec.simpleMap(MobCategory.CODEC, StructureSpawnOverride.CODEC, StringRepresentable.keys(MobCategory.values()))
                                    .fieldOf("spawn_overrides")
                                    .forGetter(StructureSettings::spawnOverrides),
                            GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(StructureSettings::step),
                            TerrainAdjustment.CODEC
                                    .optionalFieldOf("terrain_adaptation", new StructureSettings(
                                            HolderSet.direct(), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE
                                    ).terrainAdaptation())
                                    .forGetter(StructureSettings::terrainAdaptation)
                    )
                    .apply(codecBuilder, StructureSettings::new));


    public static final Codec<JungleRuinStructure> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CUSTOM_STRUCTURE_SETTINGS_CODEC.forGetter(structureInfo -> structureInfo.modifiableStructureInfo().getOriginalStructureInfo().structureSettings()),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool)

    ).apply(instance, JungleRuinStructure::new));

    protected final Holder<StructureTemplatePool> startPool;

    public JungleRuinStructure(StructureSettings pSettings, Holder<StructureTemplatePool> startPool) {
        super(pSettings);
        this.startPool = startPool;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext generationContext) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return null;
    }
}
