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

    //look in the ModItems and ModBlocks class as u see there are the tabs already defined,
    // so no need to register them here in the tab-registration, cause that might become very shit
    // when we might have more tabs, believe me
    public static final RegistryObject<CreativeModeTab> ANCIENT_TIMES_TAB = CREATIVE_MODE_TABS.register("ancient_times",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CREATIVE_TAB_LOGO.get()))
                    .title(Component.translatable(createTranslationKey("ancient_times"))).build());


    /**
     * this creates the translation key for the creative tabs
     * for example: input: "test_tab" -> "creativetab.(modid).test_tab"
     * this is used so the LanguageProvider can actually use this as translation key
     */
    public static String createTranslationKey(String name) {
        return "creativetab." + AncientTimes.MODID + "." + name;
    }
}
