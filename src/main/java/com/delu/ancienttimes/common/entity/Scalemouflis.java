package com.delu.ancienttimes.common.entity;

import com.delu.ancienttimes.registries.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.util.TimeUtil;
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

import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class Scalemouflis extends Animal implements GeoEntity, NeutralMob {
    private int remainingPersistentAngerTime = 0;
    private UUID persistenAngerTarget;
    private static final UniformInt ANGRY_TIMER = TimeUtil.rangeOfSeconds(30, 60);
    final Random random = new Random();

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40d)
                .add(Attributes.MOVEMENT_SPEED, 0.1d)
                .add(Attributes.ATTACK_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 4.8);
    }

    public Scalemouflis(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected <E extends Scalemouflis> PlayState testAnimController(AnimationState<E> event){
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.SCALEMOUFLIS.get().create(serverLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "he", 10, this::testAnimController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1,new NearestAttackableTargetGoal<>(this, Cod.class, 10, true, true, null));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 7.5f));
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        remainingPersistentAngerTime = i;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return persistenAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        persistenAngerTarget = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGRY_TIMER.sample((RandomSource) this.random));
    }

    @Override
    public void tick() {
        super.tick();
    }
}
