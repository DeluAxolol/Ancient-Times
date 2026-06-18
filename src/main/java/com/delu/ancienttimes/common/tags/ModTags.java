package com.delu.ancienttimes.common.tags;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.minecraft.tags.TagEntry.tag;

public class ModTags {

    public static class Biomes{

        public static final TagKey<Biome> JUNGLE_RUIN_CAN_SPAWN = create("jungle_ruin_can_spawn");
        public static final TagKey<Biome> MUDDY_HUT_CAN_SPAWN = create("muddy_hut_can_spawn");
        public static final TagKey<Biome> FROZEN_TOWER_CAN_SPAWN = create("frozen_tower_can_spawn");
        public static final TagKey<Biome> DRIED_RIVER_RUINED_WHEEL_CAN_SPAWN = create("dried_river_ruined_wheel_can_spawn");

        public static TagKey<Biome> create(String name){
            return TagKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
        }

    }

    public static class Blocks {
        public static final TagKey<Block> SCALEMOUFLIS_EGG_HATCH_BOOST = blockTag("scalemouflis_egg_hatch_boost");
        public static final TagKey<Block> TRIOCLANTUS_DIGGABLES = blockTag("tiroclantus_diggables");
        public static final TagKey<Block> SUSPICIOUS_BLOCKS = blockTag("suspicious_blocks");

        public static final TagKey<Block> MAGNIFYING_GLASS_CAN_DETECT = blockTag("magnifying_glass_can_detect");


        private static TagKey<Block> blockTag(String name) {
            return BlockTags.create(new ResourceLocation(AncientTimes.MODID, name));
        }
    }
}
