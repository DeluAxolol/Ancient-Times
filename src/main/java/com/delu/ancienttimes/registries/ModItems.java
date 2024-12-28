package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.item.StateModificationBlockItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AncientTimes.MODID);
    public static final RegistryObject<Item> MARD_FLOWER = registerWithCreativeModeTab("mard_flower", ModCreativeTabs.ANCIENT_TIMES_TAB, () -> new StateModificationBlockItem(ModBlocks.MARD_FLOWER.get(), state -> state.setValue(BlockStateProperties.AGE_3, 3), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(32)));
    public static final RegistryObject<Item> MARD_BULB = registerWithCreativeModeTab("mard_bulb", ModCreativeTabs.ANCIENT_TIMES_TAB, () -> new ItemNameBlockItem(ModBlocks.MARD_FLOWER.get(), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(32)));
    public static final RegistryObject<Item> TRIOCLANTUS_SPAWN_EGG = registerWithCreativeModeTab("trioclantus_spawn_egg", ModCreativeTabs.ANCIENT_TIMES_TAB, () -> new ForgeSpawnEggItem(ModEntities.TRIOCLANTUS, 0x835832, 0x668639, new Item.Properties()));
    public static final RegistryObject<Item> SCALEMOUFLIS_SPAWN_EGG = registerWithCreativeModeTab("scalemouflis_spawn_egg", ModCreativeTabs.ANCIENT_TIMES_TAB, () -> new ForgeSpawnEggItem(ModEntities.SCALEMOUFLIS, 0x40643b, 0xa19a5e, new Item.Properties()));
    public static final RegistryObject<Item> RUMOROXL_SPAWN_EGG = registerWithCreativeModeTab("rumoroxl_spawn_egg", ModCreativeTabs.ANCIENT_TIMES_TAB, () -> new ForgeSpawnEggItem(ModEntities.RUMOROXL, 0x6e1425, 0xed767b, new Item.Properties()));
    public static final RegistryObject<Item> RAVENHEAD_SEEDS = registerWithCreativeModeTab("ravenhead_seeds", ModCreativeTabs.ANCIENT_TIMES_TAB, () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64)));

    public static final RegistryObject<Item> CREATIVE_TAB_LOGO = ITEMS.register("creative_tab_logo", () -> new Item(new Item.Properties()));

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