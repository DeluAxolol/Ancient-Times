package com.delu.ancienttimes.common.tags;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.minecraft.tags.TagEntry.tag;

public class ModTags {
    public static final TagKey<Block> SCALEMOUFLIS_EGG_HATCH_BOOST = blockTag("scalemouflis_egg_hatch_boost");


    private static TagKey<Block> blockTag(String name) {
        return BlockTags.create(new ResourceLocation(AncientTimes.MODID, name));
    }
}
