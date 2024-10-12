package com.delu.ancienttimes.datagen.server.loot;

import com.delu.ancienttimes.registries.ModLootTables;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.function.BiConsumer;

public class ModArcheologyLoot implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(ModLootTables.TRIOCLANTUS_DIGGING_DROPS, LootTable.lootTable().withPool(createFirstDiggPool()));
    }

    protected LootPool.Builder createFirstDiggPool(){
        return makeItemPools(LootPool.lootPool().setRolls(ConstantValue.exactly(1)), List.of(
                Pair.of(Items.DIRT, 8),
                Pair.of(Items.STICK, 7),
                Pair.of(Items.FLINT, 7),
                Pair.of(Items.BONE, 7),
                Pair.of(Items.COARSE_DIRT, 8),
                Pair.of(Items.IRON_NUGGET, 4),
                Pair.of(Items.IRON_INGOT, 3),
                Pair.of(Items.GOLD_NUGGET, 4),
                Pair.of(Items.GOLD_INGOT, 3),
                Pair.of(Items.DIAMOND, 2)
        ));
    }

    protected LootPool.Builder makeItemPools(LootPool.Builder builder, List<Pair<ItemLike, Integer>> list){
        list.forEach(pair -> builder.add(LootItem.lootTableItem(pair.getFirst().asItem()).setWeight(pair.getSecond())));
        return builder;
    }


}
