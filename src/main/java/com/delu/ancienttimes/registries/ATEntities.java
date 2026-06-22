package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.client.renderer.CalciferopteraRenderer;
import com.delu.ancienttimes.server.entity.Calciferoptera;
import com.delu.ancienttimes.server.entity.Rumoroxl;
import com.delu.ancienttimes.server.entity.Scalemouflis;
import com.delu.ancienttimes.server.entity.Trioclantus;
import com.delu.ancienttimes.server.entity.custom.ModBoatEntity;
import com.delu.ancienttimes.server.entity.custom.ModChestBoatEntity;
import com.delu.ancienttimes.server.item.RavenheadsFruitEntity;
import lombok.experimental.UtilityClass;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.reaper.skulllib.server.builder.EntityBuilder;

@UtilityClass
public class ATEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AncientTimes.ID);
    private static final EntityBuilder BUILDER = new EntityBuilder(ENTITIES);

    public static final RegistryObject<EntityType<Trioclantus>> TRIOCLANTUS = BUILDER
            .of(Trioclantus::new, MobCategory.MONSTER)
            .name("trioclantus")
            .size(2.5f, 3.5f)
            .build();

    public static final RegistryObject<EntityType<Scalemouflis>> SCALEMOUFLIS = BUILDER
            .of(Scalemouflis::new, MobCategory.MONSTER)
            .name("scalemouflis")
            .size(1.75f, 1f)
            .build();

    public static final RegistryObject<EntityType<Rumoroxl>> RUMOROXL = BUILDER
            .of(Rumoroxl::new, MobCategory.MONSTER)
            .name("rumoroxl")
            .size(1.75f, 0.75f)
            .build();

    public static final RegistryObject<EntityType<RavenheadsFruitEntity>> RAVENHEADS_FRUIT_ENTITY = BUILDER
            .of((EntityType<RavenheadsFruitEntity> type, Level level) -> new RavenheadsFruitEntity(type, level), MobCategory.MISC)
            .name("ravenheads_fruit_entity")
            .size(0.25f, 0.25f)
            .clientTrackingRange(8)
            .updateInterval(10)
            .build();

    public static final RegistryObject<EntityType<Calciferoptera>> CALCIFEROPTERA = BUILDER
            .of(Calciferoptera::new, MobCategory.MONSTER)
            .name("calciferoptera")
            .size(1.65F, 1.2F)
            .renderer(() -> CalciferopteraRenderer::new)
            .attributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 10.0F);
                builder.add(Attributes.FOLLOW_RANGE, 48.0F);
                builder.add(Attributes.MOVEMENT_SPEED, 0.6F);
                builder.add(Attributes.ATTACK_KNOCKBACK, 0.0F);
                builder.add(Attributes.ATTACK_DAMAGE, 2.0F);
            }).build();

    public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT = BUILDER
            .of((EntityType<ModBoatEntity> type, Level level) -> new ModBoatEntity(type, level), MobCategory.MISC)
            .name("mod_boat")
            .size(1.375f, 0.5625f)
            .build();

    public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT = BUILDER
            .of((EntityType<ModChestBoatEntity> type, Level level) -> new ModChestBoatEntity(type, level), MobCategory.MISC)
            .name("mod_chest_boat")
            .size(1.375f, 0.5625f)
            .build();
}
