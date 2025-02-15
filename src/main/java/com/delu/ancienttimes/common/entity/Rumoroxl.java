package com.delu.ancienttimes.common.entity;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.util.NbtUtils;
import com.delu.ancienttimes.registries.ModEntities;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Rumoroxl extends AnimalEntity implements GeoEntity {

    protected AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(Rumoroxl.class, TrackedDataHandlerRegistry.INTEGER);

    public static DefaultAttributeContainer.Builder createAttributes(){
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40d)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1d)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.8);
    }

    public Rumoroxl(EntityType<? extends AnimalEntity> pEntityType, World world) {
        super(pEntityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 1F, 1F, false);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        //this.goalSelector.addGoal(1, new BreathAirGoal(this));
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(2, new WanderAroundGoal(this, 1.8));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 7.5f));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, Rumoroxl.Variant.generateRandom(this.getWorld().random).ordinal());
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity ageableMob) {
        return ModEntities.RUMOROXL.create(serverWorld);
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
        return Rumoroxl.Variant.values()[dataTracker.get(VARIANT)];
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);
        NbtUtils.setIfExists(pCompound, "variant", NbtCompound::getInt, this.dataTracker, VARIANT);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putInt("variant", getVariant().ordinal());
    }

    public enum Variant {
        VARIANT1(AncientTimes.entityTexture("rumoroxl/romuroxl_black_texture.png")),
        VARIANT2(AncientTimes.entityTexture("rumoroxl/romuroxl_newt_texture.png"));

        private final Identifier texture;

        Variant(Identifier texture) {
            this.texture = texture;
        }

        public Identifier getTexture() {
            return texture;
        }

        public static Rumoroxl.Variant generateRandom(Random random){
            return Rumoroxl.Variant.values()[random.nextInt(Rumoroxl.Variant.values().length)];
        }
    }

    @Override
    protected EntityNavigation createNavigation(World level) {
        return new AmphibiousSwimNavigation(this, level);
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

    public EntityGroup getMobType() {
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
