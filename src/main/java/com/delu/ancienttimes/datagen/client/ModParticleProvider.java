package com.delu.ancienttimes.datagen.client;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModParticles;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;

public class ModParticleProvider extends ParticleDescriptionProvider {
    /**
     * Creates an instance of the data provider.
     *
     * @param output     the expected root directory the data generator outputs to
     * @param fileHelper the helper used to validate a texture's existence
     */
    public ModParticleProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        //sprite(ModParticles.HIGHLIGHT_PARTICLE.get(), AncientTimes.modLoc("highlight_particle"));
    }
}