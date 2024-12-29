package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Rumoroxl;
import com.delu.ancienttimes.common.entity.Scalemouflis;
import com.delu.ancienttimes.common.entity.Trioclantus;
import com.delu.ancienttimes.common.item.RavenheadsFruitEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AncientTimes.MODID);

    public static final RegistryObject<EntityType<Trioclantus>> TRIOCLANTUS = register("trioclantus", () -> EntityType.Builder.of(Trioclantus::new, MobCategory.MONSTER).sized(2.5f, 3.5f));
    public static final RegistryObject<EntityType<Scalemouflis>> SCALEMOUFLIS = register("scalemouflis", () -> EntityType.Builder.of(Scalemouflis::new, MobCategory.MONSTER).sized(1.75f, 1f));
    public static final RegistryObject<EntityType<Rumoroxl>> RUMOROXL = register("rumoroxl", () -> EntityType.Builder.of(Rumoroxl::new, MobCategory.MONSTER).sized(1.75f, 0.75f));
    public static final RegistryObject<EntityType<RavenheadsFruitEntity>> RAVENHEADS_FRUIT_ENTITY = ENTITIES.register("ravenheads_fruit_entity",
            () -> EntityType.Builder.<RavenheadsFruitEntity>of(RavenheadsFruitEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F) // Size of the projectile
                    .clientTrackingRange(8)
                    .updateInterval(10)
                    .build("ravenheads_fruit_entity"));
    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        return ENTITIES.register(name, () -> builder.get().build(AncientTimes.modLoc(name).toString()));
    }
}
