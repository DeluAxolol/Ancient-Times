package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Triclantus;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AncientTimes.MODID);

    public static final RegistryObject<EntityType<Triclantus>> TRIOCLANTUS = register("trioclantus", () -> EntityType.Builder.of(Triclantus::new, MobCategory.MONSTER));

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        return ENTITIES.register(name, () -> builder.get().build(AncientTimes.modLoc(name).toString()));
    }
}
