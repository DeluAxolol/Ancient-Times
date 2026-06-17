package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ModTags {


    public static class Biomes{


        public static final TagKey<Biome> JUNGLE_RUIN_CAN_SPAWN = create("jungle_ruin_can_spawn");
        public static final TagKey<Biome> MUDDY_HUT_CAN_SPAWN = create("muddy_hut_can_spawn");
        public static final TagKey<Biome> FROZEN_TOWER_CAN_SPAWN = create("frozen_tower_can_spawn");

        public static TagKey<Biome> create(String name){
            return TagKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
        }

    }
}
