package com.delu.ancienttimes.datagen.server.loot;

import com.google.common.collect.Lists;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PlantLootBuilder<T extends Comparable<T>> {

    public static <R extends Comparable<R>> PlantLootBuilder<R> builder(Property<R> property, ItemLike defaultDrop) {
        return new PlantLootBuilder<>(property, defaultDrop);
    }

    protected final Property<T> age;
    protected final ItemLike defaultDrop;

    protected Map<T, List<LootPool.Builder>> pools = new HashMap<>();

    public PlantLootBuilder(Property<T> age, ItemLike defaultDrop) {
        this.age = age;
        this.defaultDrop = defaultDrop;
    }

    public PlantLootBuilder<T> whenAge(T age, LootPool.Builder pool) {
        checkValue(age);
        if (!pools.containsKey(age)) {
            pools.put(age, Lists.newArrayList(pool));
        } else {
            pools.get(age).add(pool);
        }
        return this;
    }

    public PlantLootBuilder<T> whenAge(T age, Function<ItemLike, LootPool.Builder> generator) {
        return whenAge(age, generator.apply(this.defaultDrop));
    }

    public PlantLootBuilder<T> whenAge(T age, BiFunction<T, ItemLike, LootPool.Builder> generator) {
        return whenAge(age, generator.apply(age, this.defaultDrop));
    }

    public PlantLootBuilder<T> whenAge(T age, ItemLike item){
        return whenAge(age, LootPool.lootPool().add(LootItem.lootTableItem(item)));
    }

    public PlantLootBuilder<T> whenAge(T age, ItemLike item, float chance){
        return whenAge(age, LootPool.lootPool().add(LootItem.lootTableItem(item)).when(LootItemRandomChanceCondition.randomChance(Mth.clamp(chance, 0f, 1f))));
    }

    public PlantLootBuilder<T> defaultWhenAge(T age, float chance){
        return whenAge(age, defaultDrop, chance);
    }

    public PlantLootBuilder<T> defaultWhenAge(T age){
        return whenAge(age, this.defaultDrop);
    }

    public LootTable.Builder build(Block block) {
        LootTable.Builder builder = LootTable.lootTable();
        for (Map.Entry<T, List<LootPool.Builder>> entry : this.pools.entrySet()) {
            StatePropertiesPredicate.Builder propertiesBuilder = StatePropertiesPredicate.Builder.properties();
            if (entry.getKey() instanceof Integer intValue) {
                propertiesBuilder.hasProperty(this.age, Integer.toString(intValue));
            } else if (entry.getKey() instanceof Boolean boolValue) {
                propertiesBuilder.hasProperty(this.age, Boolean.toString(boolValue));
            } else if (entry.getKey() instanceof StringRepresentable representer) {
                propertiesBuilder.hasProperty(this.age, representer.getSerializedName());
            } else {
                propertiesBuilder.hasProperty(this.age, entry.getKey().toString());
            }
            for (LootPool.Builder pool : entry.getValue()) {
                builder.withPool(pool.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(propertiesBuilder)));
            }
        }
        return builder;
    }

    protected void checkValue(T age) {
        if (!this.age.getPossibleValues().contains(age)) {
            throw new IllegalArgumentException(String.format("the parameter [%s] isnt allowed on the property: [%s]", age, this.age.getName()));
        }
    }


}
