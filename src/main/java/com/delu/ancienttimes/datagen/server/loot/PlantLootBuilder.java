package com.delu.ancienttimes.datagen.server.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PlantLootBuilder<T extends Comparable<T>> {

    public static <R extends Comparable<R>> PlantLootBuilder<R> builder(Property<R> property, ItemLike defaultDrop) {
        return new PlantLootBuilder<>(property, defaultDrop);
    }

    protected final Property<T> age;
    protected final ItemLike defaultDrop;

    protected Map<T, LootPool.Builder> pools = new HashMap<>();

    public PlantLootBuilder(Property<T> age, ItemLike defaultDrop) {
        this.age = age;
        this.defaultDrop = defaultDrop;
    }

    public PlantLootBuilder<T> whenAge(T age, LootPool.Builder pool) {
        checkValue(age);
        pools.put(age, pool);
        return this;
    }

    public PlantLootBuilder<T> whenAge(T age, Function<ItemLike, LootPool.Builder> generator) {
        return whenAge(age, generator.apply(this.defaultDrop));
    }

    public PlantLootBuilder<T> whenAge(T age, BiFunction<T, ItemLike, LootPool.Builder> generator) {
        return whenAge(age, generator.apply(age, this.defaultDrop));
    }

    public LootTable.Builder build(Block block) {
        LootTable.Builder builder = LootTable.lootTable();
        for (Map.Entry<T, LootPool.Builder> entry : this.pools.entrySet()) {
            StatePropertiesPredicate.Builder propertiesBuilder = StatePropertiesPredicate.Builder.properties();
            if (entry.getKey() instanceof Integer intValue){
                propertiesBuilder.hasProperty(this.age, Integer.toString(intValue));
            }else if (entry.getKey() instanceof Boolean boolValue){
                propertiesBuilder.hasProperty(this.age, Boolean.toString(boolValue));
            }else if (entry.getKey() instanceof  StringRepresentable representer){
                propertiesBuilder.hasProperty(this.age, representer.getSerializedName());
            }else {
                propertiesBuilder.hasProperty(this.age, entry.getKey().toString());
            }
            builder.withPool(entry.getValue().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(propertiesBuilder)));
        }
        return builder;
    }

    protected void checkValue(T age) {
        if (!this.age.getPossibleValues().contains(age)) {
            throw new IllegalArgumentException(String.format("the parameter [%s] isnt allowed on the property: [%s]", age, this.age.getName()));
        }
    }


}
