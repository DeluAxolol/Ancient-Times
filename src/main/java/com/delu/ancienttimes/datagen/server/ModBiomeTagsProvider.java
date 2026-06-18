package com.delu.ancienttimes.datagen.server;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(PackOutput p_255800_, CompletableFuture<HolderLookup.Provider> p_256205_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_255800_, p_256205_, AncientTimes.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Biomes.JUNGLE_RUIN_CAN_SPAWN).add(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE);
        tag(ModTags.Biomes.MUDDY_HUT_CAN_SPAWN).add(Biomes.MANGROVE_SWAMP, Biomes.SWAMP);
        tag(ModTags.Biomes.FROZEN_TOWER_CAN_SPAWN).add(Biomes.FROZEN_PEAKS, Biomes.ICE_SPIKES, Biomes.SNOWY_TAIGA, Biomes.SNOWY_PLAINS);
        tag(ModTags.Biomes.DRIED_RIVER_RUINED_WHEEL_CAN_SPAWN).add(Biomes.PLAINS);
    }
}
