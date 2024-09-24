package com.delu.ancienttimes.datagen.server;

import com.delu.ancienttimes.datagen.server.loot.ModBlockLoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ModLoot extends LootTableProvider {
    public ModLoot(PackOutput output) {
        super(output, Set.of(), List.of(
new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }
}
