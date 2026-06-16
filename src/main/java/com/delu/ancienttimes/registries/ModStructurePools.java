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

import java.util.List;
import java.util.Objects;

public class ModStructurePools {


    public static final ResourceKey<StructureTemplatePool> JUNGLE_RUINS_POOL = create("jungle_ruins_pool");

    public static void generate(BootstapContext<StructureTemplatePool> ctx){
        HolderGetter<StructureTemplatePool> holderGetter = ctx.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);

        ctx.register(JUNGLE_RUINS_POOL, new StructureTemplatePool(
                empty,
                List.of(
                        Pair.of(StructurePoolElement.legacy("ancienttimes:jungle_ruin_small"), 9),
                        Pair.of(StructurePoolElement.legacy("ancienttimes:jungle_ruin_big"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));
    }

    private static ResourceKey<StructureTemplatePool> create(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Objects.requireNonNull(ResourceLocation.tryBuild(AncientTimes.MODID, name)));
    }
}
