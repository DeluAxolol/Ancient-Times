package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AncientTimes.MODID);
    public static final RegistryObject<Item> MARD_FLOWER = registerWithCreativeModeTab("mard_flower", CreativeModeTabs.NATURAL_BLOCKS, () -> new ItemNameBlockItem(ModBlocks.MARD_FLOWER.get(), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64)));

    public static <T extends Item> RegistryObject<T> registerWithCreativeModeTab(String name, Supplier<CreativeModeTab> creativeModeTab, Supplier<T> itemGenerator) {
        RegistryObject<T> item = ITEMS.register(name, itemGenerator);
        Consumer<BuildCreativeModeTabContentsEvent> eventHooker = event -> {
            if (event.getTab() == creativeModeTab.get()) {
                event.accept(item);
            }
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventHooker);
        return item;
    }

    public static <T extends Item> RegistryObject<T> registerWithCreativeModeTab(String name, ResourceKey<CreativeModeTab> creativeModeTab, Supplier<T> itemGenerator) {
        RegistryObject<T> item = ITEMS.register(name, itemGenerator);
        Consumer<BuildCreativeModeTabContentsEvent> eventHooker = event -> {
            if (event.getTabKey().equals(creativeModeTab)) {
                event.accept(item);
            }
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventHooker);
        return item;
    }


}