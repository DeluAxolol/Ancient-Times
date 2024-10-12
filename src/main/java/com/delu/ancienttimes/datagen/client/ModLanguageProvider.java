package com.delu.ancienttimes.datagen.client;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModCreativeTabs;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLanguageProvider extends BetterLanguageProvider {


    public ModLanguageProvider(PackOutput output) {super(output, AncientTimes.MODID, "en_us");}

    @Override
    protected void addTranslations() {
        ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(this::simpleItem);
        add(ModCreativeTabs.ANCIENT_TIMES_TAB);
    }

    protected void add(RegistryObject<CreativeModeTab> tab){
        String name = tab.getId().getPath();
        add(ModCreativeTabs.createTranslationKey(name), toTitleCase(name));
    }
}
