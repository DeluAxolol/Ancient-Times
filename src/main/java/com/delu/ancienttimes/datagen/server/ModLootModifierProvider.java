package com.delu.ancienttimes.datagen.server;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.loot.AddItemModifier;
import com.delu.ancienttimes.common.loot.AddSusSandItemModifier;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModLootModifierProvider extends GlobalLootModifierProvider {
    public ModLootModifierProvider(PackOutput output) {
        super(output, AncientTimes.MODID);
    }

    @Override
    protected void start() {
        add("add_sniffer_drops", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.35f).build(),
                new LootTableIdCondition.Builder(BuiltInLootTables.SNIFFER_DIGGING).build()
        }, ModItems.MARD_BULB.get()));
        add("add_sniffer_drops_from_suspicious_sand", new AddSusSandItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.5f).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("archaeology/desert_pyramid")).build()
        }, ModItems.MARD_BULB.get()));
        add("add_sniffer_drops", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.4f).build(),
                new LootTableIdCondition.Builder(BuiltInLootTables.SNIFFER_DIGGING).build()
        }, ModItems.RAVENHEAD_SEEDS.get()));
    }
}
