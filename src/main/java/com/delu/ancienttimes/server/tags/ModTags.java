package com.delu.ancienttimes.server.tags;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> SCALEMOUFLIS_EGG_HATCH_BOOST = blockTag("scalemouflis_egg_hatch_boost");
        public static final TagKey<Block> TRIOCLANTUS_DIGGABLES = blockTag("tiroclantus_diggables");


        private static TagKey<Block> blockTag(String name) {
            return BlockTags.create(new ResourceLocation(AncientTimes.ID, name));
        }
    }
}
