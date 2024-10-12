package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.item.StateModificationBlockItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
    public static final RegistryObject<Item> MARD_FLOWER = registerWithCreativeModeTab("mard_flower", CreativeModeTabs.NATURAL_BLOCKS, () -> new StateModificationBlockItem(ModBlocks.MARD_FLOWER.get(), state -> state.setValue(BlockStateProperties.AGE_3, 3), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(32)));
    public static final RegistryObject<Item> MARD_BULB = registerWithCreativeModeTab("mard_bulb", CreativeModeTabs.NATURAL_BLOCKS, () -> new ItemNameBlockItem(ModBlocks.MARD_FLOWER.get(), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(32)));

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