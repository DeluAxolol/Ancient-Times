package com.delu.ancienttimes.common.structure;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.util.FilterHolderSet;
import com.delu.ancienttimes.common.util.StructureUtils;
import com.delu.ancienttimes.registries.ModStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
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
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            Codec.INT.fieldOf("max_spawn_height").forGetter(structure -> structure.maxSpawnHeigh),
            HeightProvider.CODEC.fieldOf("generation_height").forGetter(structure -> structure.generationHeight)

    ).apply(instance, JungleRuinStructure::new));

    protected final Holder<StructureTemplatePool> startPool;
    protected final int maxSpawnHeigh;
    protected final HeightProvider generationHeight;

    public JungleRuinStructure(StructureSettings pSettings, Holder<StructureTemplatePool> startPool, int maxSpawnHeigh, HeightProvider generationHeight) {
        super(pSettings);
        this.startPool = startPool;
        this.maxSpawnHeigh = maxSpawnHeigh;
        this.generationHeight = generationHeight;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext generationContext) {
        if (StructureUtils.checkSpawnHeight(generationContext, this.maxSpawnHeigh)) {
            return Optional.empty();
        }
        var chunkPos = generationContext.chunkPos();

        int startY = this.generationHeight.sample(generationContext.random(), new WorldGenerationContext(generationContext.chunkGenerator(), generationContext.heightAccessor()));
        int occupiedYPos = generationContext.chunkGenerator().getFirstOccupiedHeight(
                chunkPos.getMinBlockX(),
                chunkPos.getMinBlockZ(),
                Heightmap.Types.WORLD_SURFACE_WG,
                generationContext.heightAccessor(),
                generationContext.randomState());

        // Turns the chunk coordinates into actual coordinates we can use. (Gets corner of that chunk)
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), occupiedYPos, chunkPos.getMinBlockZ());

        AncientTimes.LOGGER.debug("tried spawning jungle ruin at {}", blockPos);

        Optional<Structure.GenerationStub> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        generationContext, // Used for JigsawPlacement to get all the proper behaviors done.
                        this.startPool, // The starting pool to use to create the structure layout from
                        Optional.empty(), // Can be used to only spawn from one Jigsaw block. But we don't need to worry about this.
                        2, // How deep a branch of pieces can go away from center piece. (5 means branches cannot be longer than 5 pieces from center piece)
                        blockPos, // Where to spawn the structure.
                        false, // "useExpansionHack" This is for legacy villages to generate properly. You should keep this false always.
                        Optional.empty(), // Adds the terrain height's y value to the passed in blockpos's y value. (This uses WORLD_SURFACE_WG heightmap which stops at top water too)
                        // Here at projectStartToHeightmap, start_height's y value is 60 which means the structure spawn 60 blocks above terrain height if start_height and project_start_to_heightmap is defined in structure JSON.
                        // Set projectStartToHeightmap to be empty optional for structure to be place only at the passed in blockpos's Y value instead.
                        // Definitely keep this an empty optional when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
                        20); // Maximum limit for how far pieces can spawn from center. You cannot set this bigger than 128 or else pieces gets cutoff.

        AncientTimes.LOGGER.debug("spawning jungle ruin {}", structurePiecesGenerator);

        return structurePiecesGenerator;
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypes.JUNGLE_RUIN_STRUCTURE.get();
    }
}
