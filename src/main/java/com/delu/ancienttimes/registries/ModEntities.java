package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Rumoroxl;
import com.delu.ancienttimes.common.entity.Scalemouflis;
import com.delu.ancienttimes.common.entity.Trioclantus;
import com.delu.ancienttimes.common.entity.custom.ModBoatEntity;
import com.delu.ancienttimes.common.entity.custom.ModChestBoatEntity;
import com.delu.ancienttimes.common.item.RavenheadsFruitEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<Trioclantus> TRIOCLANTUS = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(AncientTimes.MODID, "trioclantus"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Trioclantus::new)
                    .dimensions(EntityDimensions.fixed(2.5f, 3.5f)).build());

    public static final EntityType<Scalemouflis> SCALEMOUFLIS = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(AncientTimes.MODID, "scalemouflis"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Scalemouflis::new)
                    .dimensions(EntityDimensions.fixed(1.75f, 1f)).build());

    public static final EntityType<Rumoroxl> RUMOROXL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(AncientTimes.MODID, "rumoroxl"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Rumoroxl::new)
                    .dimensions(EntityDimensions.fixed(1.75f, 0.75f)).build());

    public static final EntityType<RavenheadsFruitEntity> RAVENHEADS_FRUIT_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(AncientTimes.MODID, "ravenheads_fruit_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RavenheadsFruitEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(8)
                    .trackedUpdateRate(10)
                    .build());

    public static final EntityType<ModBoatEntity> MOD_BOAT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(AncientTimes.MODID, "mod_boat"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ModBoatEntity::new)
                    .dimensions(1.375f, 0.5625f).build());

    public static final EntityType<ModChestBoatEntity> MOD_CHEST_BOAT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(AncientTimes.MODID, "mod_chest_boat"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ModChestBoatEntity::new)
                    .dimensions(1.375f, 0.5625f).build());

    public static void registerEntities() {
        AncientTimes.LOGGER.info("Registering Entities for " + AncientTimes.MODID);
    }
}
