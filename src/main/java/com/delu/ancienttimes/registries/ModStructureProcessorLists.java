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
    public static final ResourceKey<StructureProcessorList> MUDDY_HUT_PROCESSORS = create("muddy_hut_processors");
    public static final ResourceKey<StructureProcessorList> FROZEN_TOWER_PROCESSORS = create("frozen_tower_processors");
    public static final ResourceKey<StructureProcessorList> DRIED_RIVER_RUINED_WHEEL = create("dried_river_ruined_wheel");

    public static void generate(BootstapContext<StructureProcessorList> ctx) {
        ctx.register(JUNGLE_RUIN_PROCESSORS, new StructureProcessorList(List.of(
                new RuleProcessor(
                        List.of(
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.DIRT, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_DIRT.get().defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.GRASS_BLOCK, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_DIRT.get().defaultBlockState()
                                )
                        )
                )
        )));

        ctx.register(MUDDY_HUT_PROCESSORS, new StructureProcessorList(List.of(
                new RuleProcessor(
                        List.of(
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.MUD, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_MUD.get().defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.DIRT, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_DIRT.get().defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.GRASS_BLOCK, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_DIRT.get().defaultBlockState()
                                )
                        )
                )
        )));

        ctx.register(FROZEN_TOWER_PROCESSORS, new StructureProcessorList(List.of(
                new RuleProcessor(
                        List.of(
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.SNOW_BLOCK, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_SNOW.get().defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.DIRT, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_DIRT.get().defaultBlockState()
                                )
                        )
                )
        )));
        ctx.register(DRIED_RIVER_RUINED_WHEEL, new StructureProcessorList(List.of(
                new RuleProcessor(
                        List.of(
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.DIRT, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_DIRT.get().defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.STONE, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_STONE.get().defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.GRASS_BLOCK, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        ModBlocks.SUSPICIOUS_DIRT.get().defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.GRAVEL, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        Blocks.SUSPICIOUS_GRAVEL.defaultBlockState()
                                ),
                                new ProcessorRule(
                                        new RandomBlockMatchTest(Blocks.SAND, 0.3f),
                                        AlwaysTrueTest.INSTANCE,
                                        Blocks.SUSPICIOUS_SAND.defaultBlockState()
                                )
                        )
                )
        )));

    }


    private static ResourceKey<StructureProcessorList> create(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
