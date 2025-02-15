package com.delu.ancienttimes.common.entity;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.util.NbtUtils;
import com.delu.ancienttimes.registries.ModEntities;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class Scalemouflis extends AnimalEntity implements GeoEntity, Angerable {
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation SWIM = RawAnimation.begin().thenLoop("swim");

    public static final RawAnimation ATTACK_AIR = RawAnimation.begin().thenPlay("attack-1-blending");
    public static final RawAnimation ATTACK_WATER = RawAnimation.begin().thenPlay("attack-2-water");

    private static final UniformIntProvider ANGRY_TIMER = TimeHelper.betweenSeconds(30, 60);

    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(Scalemouflis.class, TrackedDataHandlerRegistry.INTEGER);

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40d)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1d)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.8);
    }
    private int remainingPersistentAngerTime = 0;
    private UUID persistentAngerTarget;
    protected AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public Scalemouflis(EntityType<? extends AnimalEntity> pEntityType, World world) {
        super(pEntityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 1F, 1F, false);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, Variant.generateRandom(this.getWorld().random).ordinal());
    }

    protected <E extends Scalemouflis> PlayState testAnimController(AnimationState<E> event){ // TODO add attack animations
        if (event.isMoving()) {
            if (this.isTouchingWater()) {
                return event.setAndContinue(SWIM);
            } else {
                return event.setAndContinue(WALK);
            }
        } else {
            return event.setAndContinue(IDLE);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity ageableMob) {
        return ModEntities.SCALEMOUFLIS.create(serverWorld);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "scallywag", 10, this::testAnimController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        //this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.9, false));
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.1D));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1.5D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 7.5f));

        this.targetSelector.add(1,new ActiveTargetGoal<>(this, CodEntity.class, 10, true, true, null));
        //this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public int getAngerTime() {
        return remainingPersistentAngerTime;
    }

    @Override
    public void setAngerTime(int i) {
        remainingPersistentAngerTime = i;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return persistentAngerTarget;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        persistentAngerTarget = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGRY_TIMER.get(this.getWorld().random));
    }

    public Variant getVariant() {
        return Variant.values()[dataTracker.get(VARIANT)];
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);
        NbtUtils.setIfExists(pCompound, "variant", NbtCompound::getInt, this.dataTracker, VARIANT);
        NbtUtils.setIfExists(pCompound, "persistentAngerTarget", NbtCompound::getUuid, this::setAngryAt);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putInt("variant", getVariant().ordinal());
        if (this.getAngryAt() != null)
            pCompound.putUuid("persistentAngerTarget", this.getAngryAt());
    }

    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.ordinal());
    }

    public enum Variant{
        VARIANT1(AncientTimes.entityTexture("scalemouflis/scalemouflis_blood_texture.png")),
        VARIANT2(AncientTimes.entityTexture("scalemouflis/scalemouflis_pond_snail_texture.png")),
        VARIANT3(AncientTimes.entityTexture("scalemouflis/scalemouflis_normal_texture.png"));

        private final Identifier texture;

        Variant(Identifier texture) {
            this.texture = texture;
        }

        public Identifier getTexture() {
            return texture;
        }

        public static Variant generateRandom(Random random){
            return Variant.values()[random.nextInt(Variant.values().length)];
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousSwimNavigation(this, world);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public void baseTick() {
        this.setAir(this.getMaxAir());
        super.baseTick();
    }

    @Override
    public int getMaxAir() {
        return 1000; // Duration in ticks (300 = 15 seconds underwater without suffocating)
    }

    @Override
    public boolean isPushedByFluids() {
        return false; // Prevent the entity from being pushed by water currents
    }

    @Override
    public boolean isTouchingWater() {
        return this.getWorld().isWater(this.getBlockPos());
    }

    @Override
    public boolean isSwimming() {
        return this.isTouchingWater();
    }

    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    public void travel(Vec3d pTravelVector) {
        if (this.isLogicalSideForUpdatingMovement() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), pTravelVector);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));
        } else {
            super.travel(pTravelVector);
        }

    }

}
