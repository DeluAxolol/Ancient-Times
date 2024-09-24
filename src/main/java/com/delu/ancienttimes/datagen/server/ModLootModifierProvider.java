package com.delu.ancienttimes.datagen.server;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.loot.AddItemModifier;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {
    public ModLootModifierProvider(PackOutput output) {
        super(output, AncientTimes.MODID);
    }

    @Override
    protected void start() {
        add("add_sniffer_drops", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.35f).build()
        }, ModItems.MARD_BULB.get()));
    }
}
