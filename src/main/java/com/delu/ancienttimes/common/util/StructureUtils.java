package com.delu.ancienttimes.common.util;

import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureUtils {



    public static boolean checkSpawnHeight(Structure.GenerationContext context, int maxHeight) {
        // Grabs the chunk position we are at
        ChunkPos chunkpos = context.chunkPos();
        int clampedHeight = Mth.clamp(maxHeight, context.heightAccessor().getMinBuildHeight(), context.heightAccessor().getMaxBuildHeight());

        // Checks to make sure our structure does not spawn above land that's higher than y = 150
        // to demonstrate how this method is good for checking extra conditions for spawning
        return context.chunkGenerator().getFirstOccupiedHeight(
                chunkpos.getMinBlockX(),
                chunkpos.getMinBlockZ(),
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                context.heightAccessor(),
                context.randomState()) < clampedHeight;
    }
}
