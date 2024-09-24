package com.delu.ancienttimes.datagen.client;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.registries.RegistryObject;

public class ModLanguageProvider extends BetterLanguageProvider {


    public ModLanguageProvider(PackOutput output) {super(output, AncientTimes.MODID, "en_us");}

    @Override
    protected void addTranslations() {
        ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(this::simpleItem);

    }
}
