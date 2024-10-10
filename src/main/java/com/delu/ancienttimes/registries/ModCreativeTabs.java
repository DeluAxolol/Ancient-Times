package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AncientTimes.MODID);

    public static final RegistryObject<CreativeModeTab> ANCIENT_TIMES_TAB = CREATIVE_MODE_TABS.register("ancient_times_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CREATIVE_TAB_LOGO.get()))
                    .title(Component.translatable("creativetab.ancient_times_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MARD_BULB.get());
                        pOutput.accept(ModItems.MARD_FLOWER.get());

                        pOutput.accept(ModBlocks.MARD_FLOWER.get());

                    }).build());

}
