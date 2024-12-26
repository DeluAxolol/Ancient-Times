package com.delu.ancienttimes.common.entity;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.util.NbtUtils;
import com.delu.ancienttimes.registries.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Rumoroxl extends Animal implements GeoEntity {

    protected AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Rumoroxl.class, EntityDataSerializers.INT);

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40d)
                .add(Attributes.MOVEMENT_SPEED, 0.1d)
                .add(Attributes.ATTACK_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 4.8);
    }

    public Rumoroxl(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new BreathAirGoal(this));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 7.5f));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, Rumoroxl.Variant.generateRandom(this.level().random).ordinal());
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.RUMOROXL.get().create(serverLevel);
    }

    protected <E extends Rumoroxl> PlayState animController(AnimationState<E> event){
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "stuff", 10, this::animController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public Rumoroxl.Variant getVariant() {
        return Rumoroxl.Variant.values()[entityData.get(VARIANT)];
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        NbtUtils.setIfExists(pCompound, "variant", CompoundTag::getInt, this.entityData, VARIANT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("variant", getVariant().ordinal());
    }

    public enum Variant {
        VARIANT1(AncientTimes.entityTexture("rumoroxl/romuroxl_black_texture.png")),
        VARIANT2(AncientTimes.entityTexture("rumoroxl/romuroxl_newt_texture.png"));

        private final ResourceLocation texture;

        Variant(ResourceLocation texture) {
            this.texture = texture;
        }

        public ResourceLocation getTexture() {
            return texture;
        }

        public static Rumoroxl.Variant generateRandom(RandomSource random){
            return Rumoroxl.Variant.values()[random.nextInt(Rumoroxl.Variant.values().length)];
        }
    }
}