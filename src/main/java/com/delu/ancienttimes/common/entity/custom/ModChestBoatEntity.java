package com.delu.ancienttimes.common.entity.custom;

import com.delu.ancienttimes.registries.ModEntities;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class ModChestBoatEntity extends ChestBoatEntity {
    private static final TrackedData<Integer> DATA_ID_TYPE = DataTracker.registerData(ModChestBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ModChestBoatEntity(EntityType<? extends ChestBoatEntity> pEntityType, World world) {
        super(pEntityType, world);
    }

    public ModChestBoatEntity(World world, double pX, double pY, double pZ) {
        this(ModEntities.MOD_CHEST_BOAT, world);
        this.setPos(pX, pY, pZ);
        this.prevX = pX;
        this.prevY = pY;
        this.prevZ = pZ;
    }

    @Override
    public Item asItem() {
        switch (getModVariant()) {
            case MEAL -> {
                return ModItems.MEAL_CHEST_BOAT;
            }
        }
        return super.asItem();
    }

    public void setVariant(ModBoatEntity.Type pVariant) {
        this.dataTracker.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE, ModBoatEntity.Type.MEAL.ordinal());
    }

    protected void addAdditionalSaveData(NbtCompound pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(NbtCompound pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(ModBoatEntity.Type.byName(pCompound.getString("Type")));
        }
    }

    public ModBoatEntity.Type getModVariant() {
        return ModBoatEntity.Type.byId(this.dataTracker.get(DATA_ID_TYPE));
    }
}