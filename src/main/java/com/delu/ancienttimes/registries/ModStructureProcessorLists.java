package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.rule_tests.RandomTagMatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;
import java.util.Objects;

public class ModStructureProcessorLists {


    public static final ResourceKey<StructureProcessorList> JUNGLE_RUIN_PROCESSORS = create("jungle_ruin_processors");

    public static void generate(BootstapContext<StructureProcessorList> ctx) {
        ctx.register(JUNGLE_RUIN_PROCESSORS, new StructureProcessorList(List.of(
                new RuleProcessor(
                        List.of(
                                new ProcessorRule(
                                        new RandomTagMatch(BlockTags.STONE_BRICKS, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUS_RED_SAND.get().defaultBlockState()
                                )
                        )
                )
        )));
    }


    private static ResourceKey<StructureProcessorList> create(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
