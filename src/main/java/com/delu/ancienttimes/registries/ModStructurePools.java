package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Objects;

public class ModStructurePools {


    public static final ResourceKey<StructureTemplatePool> JUNGLE_RUINS = create("jungle_ruins");
    public static final ResourceKey<StructureTemplatePool> MUDDY_HUT = create("muddy_hut");
    public static final ResourceKey<StructureTemplatePool> FROZEN_TOWER = create("frozen_tower");

    public static void generate(BootstapContext<StructureTemplatePool> ctx){
        HolderGetter<StructureTemplatePool> holderGetter = ctx.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> structureProcessorListHolderGetter = ctx.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);

        var jungleRuinProcessorList = structureProcessorListHolderGetter.getOrThrow(ModStructureProcessorLists.JUNGLE_RUIN_PROCESSORS);
        var muddyHutProcessorList = structureProcessorListHolderGetter.getOrThrow(ModStructureProcessorLists.MUDDY_HUT_PROCESSORS);

        ctx.register(JUNGLE_RUINS, new StructureTemplatePool(
                empty,
                List.of(
                        Pair.of(StructurePoolElement.legacy(AncientTimes.modLoc("jungle_ruin").toString(), jungleRuinProcessorList), 9),
                        Pair.of(StructurePoolElement.legacy(AncientTimes.modLoc("jungle_ruin_big").toString(), jungleRuinProcessorList), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));

        ctx.register(MUDDY_HUT, new StructureTemplatePool(
                empty,
                List.of(
                        Pair.of(StructurePoolElement.legacy(AncientTimes.modLoc("muddy_hut_small").toString(), muddyHutProcessorList), 1),
                        Pair.of(StructurePoolElement.legacy(AncientTimes.modLoc("muddy_hut_big").toString(), muddyHutProcessorList), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));

        ctx.register(FROZEN_TOWER, new StructureTemplatePool(
                empty,
                List.of(
                        Pair.of(StructurePoolElement.legacy(AncientTimes.modLoc("frozen_tower_small").toString(), muddyHutProcessorList), 5),
                        Pair.of(StructurePoolElement.legacy(AncientTimes.modLoc("frozen_tower_big").toString(), muddyHutProcessorList), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));


    }

    private static ResourceKey<StructureTemplatePool> create(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
