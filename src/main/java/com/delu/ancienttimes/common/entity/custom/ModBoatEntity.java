package com.delu.ancienttimes.common.entity.custom;

import com.delu.ancienttimes.registries.ModBlocks;
import com.delu.ancienttimes.registries.ModEntities;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.minecraft.world.World;

import java.util.function.IntFunction;
public class ModBoatEntity extends BoatEntity {
    private static final TrackedData<Integer> DATA_ID_TYPE = DataTracker.registerData(ModBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public ModBoatEntity(EntityType<? extends BoatEntity> pEntityType, World world) {
        super(pEntityType, world);
    }
    public ModBoatEntity(World world, double pX, double pY, double pZ) {
        this(ModEntities.MOD_BOAT, world);
        this.setPos(pX, pY, pZ);
        this.prevX = pX;
        this.prevY = pY;
        this.prevZ = pZ;
    }

    @Override
    public Item asItem() {
        return switch (getModVariant()) {
            case MEAL -> ModItems.MEAL_BOAT;
        };
    }
    public void setVariant(Type pVariant) {
        this.dataTracker.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.dataTracker.get(DATA_ID_TYPE));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE, Type.MEAL.ordinal());
    }

    protected void addAdditionalSaveData(NbtCompound pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(NbtCompound pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(Type.byName(pCompound.getString("Type")));
        }
    }

    public static enum Type implements StringIdentifiable {
        MEAL(ModBlocks.MEAL_PLANKS, "meal");
        private final String name;
        private final Block planks;
        public static final StringIdentifiable.Codec<ModBoatEntity.Type> CODEC = StringIdentifiable.createCodec(ModBoatEntity.Type::values);
        private static final IntFunction<ModBoatEntity.Type> BY_ID = ValueLists.createIdToValueFunction(Enum::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
        Type(Block pPlanks, String pName) {
            this.name = pName;
            this.planks = pPlanks;
        }

        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }
        /**
         * Get a boat type by its enum ordinal
         */
        public static ModBoatEntity.Type byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static ModBoatEntity.Type byName(String pName) {
            return CODEC.byId(pName, MEAL);
        }

        @Override
        public String asString() {
            return "";
        }
    }
}