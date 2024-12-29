package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AncientTimes.MODID);


    //public static final RegistryObject<SimpleParticleType> HIGHLIGHT_PARTICLE = register("highlight_particle", false);



    public static RegistryObject<SimpleParticleType> register(String name, boolean pOverrideLimiter){
        RegistryObject<SimpleParticleType> ret = PARTICLES.register(name, () -> new SimpleParticleType(pOverrideLimiter));
        return ret;
    }
}

