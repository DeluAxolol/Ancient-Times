package com.delu.ancienttimes.datagen;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.datagen.client.ModBlockStatesProvider;
import com.delu.ancienttimes.datagen.client.ModItemModelsProvider;
import com.delu.ancienttimes.datagen.client.ModLanguageProvider;
import com.delu.ancienttimes.datagen.client.ModParticleProvider;
import com.delu.ancienttimes.datagen.client.ModWorldGenProvider;
import com.delu.ancienttimes.datagen.server.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = AncientTimes.MODID)
public class DataGenerator {


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> holderLookup = event.getLookupProvider();

        gen.addProvider(event.includeClient(), new ModItemModelsProvider(output, helper));
        gen.addProvider(event.includeClient(), new ModLanguageProvider(output));
        gen.addProvider(event.includeClient(), new ModBlockStatesProvider(output, helper));

        //server
        gen.addProvider(event.includeServer(), new ModLoot(output));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(output));
        gen.addProvider(event.includeServer(), new ModLootModifierProvider(output));
        gen.addProvider(event.includeServer(), new ModWorldGenProvider(output, holderLookup));
        gen.addProvider(event.includeClient(), new ModParticleProvider(output, helper));

        ModBlockTagsProvider provider = gen.addProvider(event.includeServer(), new ModBlockTagsProvider(output, event.getLookupProvider(), helper));
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(output, event.getLookupProvider(), provider.contentsGetter(), helper));

    }
}

