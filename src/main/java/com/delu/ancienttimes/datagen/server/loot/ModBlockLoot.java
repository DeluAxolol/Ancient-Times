package com.delu.ancienttimes.datagen.server.loot;

import com.delu.ancienttimes.registries.ModBlocks;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModBlockLoot extends BlockLootSubProvider {

    protected List<Block> knownBlocks = new ArrayList<>();

    public ModBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        makeBushBlocks();
        dropSelf(
                ModBlocks.STRIPPED_MEAL_LOG,
                ModBlocks.MEAL_DOOR,
                ModBlocks.MEAL_TRAPDOOR,
                ModBlocks.MEAL_LOG,
                ModBlocks.MEAL_PLANKS,
                ModBlocks.MEAL_LEAVES,
                ModBlocks.MEAL_SAPLING,
                ModBlocks.MEAL_STAIRS,
                ModBlocks.MEAL_SLAB
        );
    }

    protected void makeBushBlocks() {
        add(ModBlocks.MARD_FLOWER.get(), PlantLootBuilder.builder(BlockStateProperties.AGE_3, ModItems.MARD_BULB.get())
                .defaultWhenAge(0)
                .defaultWhenAge(1)
                .defaultWhenAge(2)
                .defaultWhenAge(3, 0.4f)
                .whenAge(3, ModItems.MARD_FLOWER.get())
        );
    }

    @SafeVarargs
    public final void dropSelf(Supplier<? extends Block>... blocks) {
        for (Supplier<? extends Block> supp : blocks) {
            dropSelf(supp.get());
        }
    }

    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.generate();
        Set<ResourceLocation> set = new HashSet<>();

        for (Block block : getKnownBlocks()) {
            if (block.isEnabled(this.enabledFeatures)) {
                ResourceLocation resourcelocation = block.getLootTable();
                if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
                    LootTable.Builder loottable$builder = this.map.remove(resourcelocation);
                    if (loottable$builder == null) {
                        throw new IllegalStateException(String.format(Locale.ROOT, "Missing loottable '%s' for '%s'", resourcelocation, BuiltInRegistries.BLOCK.getKey(block)));
                    }

                    consumer.accept(resourcelocation, loottable$builder);
                }
            }
        }

        if (!this.map.isEmpty()) {
            for (Map.Entry<ResourceLocation, LootTable.Builder> entry : this.map.entrySet()) {
                consumer.accept(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @param drop              the item we want to drop
     * @param chance            the chance this item drops
     * @param lootingMultiplier the multiplier which is added to the chance so the total chance is <b>chance + _fortuneLevel_ * lootingMultiplier</b>
     *                          if the total chance is higher or equal to 1 this will always drop
     */
    protected LootPool.Builder simpleItemPool(ItemLike drop, float chance, float lootingMultiplier) {
        return LootPool.lootPool().add(LootItem.lootTableItem(drop)).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(chance, lootingMultiplier));
    }

    /**
     * @param drop   the item we want to drop
     * @param chance the chance with which the item will drop
     */
    protected LootPool.Builder simpleItemPool(ItemLike drop, float chance) {
        return LootPool.lootPool().add(LootItem.lootTableItem(drop)).when(LootItemRandomChanceCondition.randomChance(chance));
    }

    /**
     * @param drop hte item this pool will always drop
     * @return
     */
    protected LootPool.Builder simpleItemPool(ItemLike drop) {
        return LootPool.lootPool().add(LootItem.lootTableItem(drop));
    }

    protected void add(Block pBlock, PlantLootBuilder<?> builder) {
        this.add(pBlock, builder.build(pBlock).apply(ApplyExplosionDecay.explosionDecay()));
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pBuilder) {
        super.add(pBlock, pBuilder);
        this.knownBlocks.add(pBlock);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
    }
}
