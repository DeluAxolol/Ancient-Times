package com.delu.ancienttimes.server.entity;

import com.delu.ancienttimes.client.anim.CalciferopteraAnims;
import com.delu.ancienttimes.server.entity.goal.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reaper.skulllib.client.sound.inst.GlideSoundInstance;
import org.reaper.skulllib.server.anim.*;
import org.reaper.skulllib.server.entity.EntityUtils;
import org.reaper.skulllib.server.entity.IPackAnimal;
import org.reaper.skulllib.server.entity.PackController;
import org.reaper.skulllib.server.tag.EntityTag;
import org.reaper.skulllib.server.tag.TagBuilder;
import org.reaper.skulllib.server.tag.TagType;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

public final class Calciferoptera extends ATAnimal<Calciferoptera> implements ATFlyingAnimal, ISyncedStepHandler, IPackAnimal {
    public static final EntityTag<Optional<UUID>> LEADER_UUID = new EntityTag<>(Calciferoptera.class, "LeaderUUID", TagType.UUID);
    public static final EntityTag<Boolean> FLYING = new EntityTag<>(Calciferoptera.class, "Flying", TagType.BOOLEAN);
    public final PackController<Calciferoptera> pack;
    @OnlyIn(Dist.CLIENT)
    public float bodyRoll = 0.0F, bodyPitch = 0.0F;
    @Getter
    @Setter
    @Nullable BlockPos orbitPos = null;
    @Getter
    @Setter
    double orbitDist = 8.0F;
    @Getter
    @Setter
    boolean orbitClockwise = true;
    @Getter
    @Setter
    boolean grounded = true;
    @Getter
    @Setter
    int groundTicks = 0, airTicks = 0;
    @Getter
    @Setter
    @Nullable BlockPos landingTarget = null;
    public final AnimData idleData = new AnimData(() -> CalciferopteraAnims.IDLE);
    public BaseAnimation idleAnim = new SimpleAnimation("idle", this.idleData, Loop.CYCLE, 0, 2, 0.8F).setCanInterrupt(true).setPlayCondition(anim -> !this.isFlying());
    public final AnimData flyData = new AnimData(() -> CalciferopteraAnims.FLY);
    public BaseAnimation flyAnim = new SimpleAnimation("fly", this.flyData, Loop.CYCLE, 0, 1, 0.8F).setCanInterrupt(true).setPlayCondition(anim -> this.isFlying());
    @OnlyIn(Dist.CLIENT)
    @Nullable
    private GlideSoundInstance<Calciferoptera> glideSoundInst = null;
    public final AnimData wingFlapData = new AnimData(() -> CalciferopteraAnims.WING_FLAP);
    public BaseAnimation wingFlapAnim = new SimpleAnimation("wing_flap", this.wingFlapData, Loop.CYCLE, 2, 0, 0.7917F).setCanInterrupt(true).setPlayCondition(anim -> this.isFlying()).addListener(new AnimationListener() {
        @OnlyIn(Dist.CLIENT)
        private final SoundManager manager = Minecraft.getInstance().getSoundManager();

        @Override
        public void onStart(@NotNull BaseAnimation anim) {
            Calciferoptera mob = (Calciferoptera) anim.getAnimator().getEntity();
            if (level.isClientSide && glideSoundInst == null) {
                glideSoundInst = new GlideSoundInstance<>(mob, SoundEvents.PHANTOM_FLAP);
                this.manager.play(glideSoundInst);
            }
        }

        @Override
        public void onEnd(@NotNull BaseAnimation anim) {
            if (level.isClientSide && glideSoundInst != null) {
                this.manager.stop(glideSoundInst);
                glideSoundInst = null;
            }
        }
    });
    private AnimationListener attackListener = new AnimationListener() {
        @Override
        public void onStart(@NotNull BaseAnimation anim) {
            playSound(SoundEvents.FROG_TONGUE);
        }

        @Override
        public void onTick(@NotNull BaseAnimation anim, int tick) {
            Calciferoptera mob = (Calciferoptera) anim.getAnimator().getEntity();
            if (!level.isClientSide) {
                boolean stop = getTarget() == null;
                Vec3 start = mob.getEyePosition();
                Vec3 dir = mob.getViewVector(1.0F).scale(tick * 0.8F);
                Vec3 end = start.add(dir);
                EntityHitResult result = ProjectileUtil.getEntityHitResult(mob.level(), mob, start, end, mob.getBoundingBox().inflate(tick * 0.5F), entity -> {
                    return entity != mob && !(entity instanceof Calciferoptera) && !entity.isSpectator();
                });
                if (mob.level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob)).getType() != HitResult.Type.MISS || result != null) {
                    stop = false;
                    if (result != null && result.getEntity() instanceof LivingEntity target) {
                        mob.doHurtTarget(target);
                    }
                }
                if (stop) {
                    anim.stop();
                }
            }
        }
    };
    public final AnimData attackData = new AnimData(() -> CalciferopteraAnims.ATTACK);
    public BaseAnimation attackAnim = new SimpleAnimation("attack", this.attackData, Loop.ONCE, 1, 0, 0.75F).addListener(this.attackListener);
    public final AnimData flyAttackData = new AnimData(() -> CalciferopteraAnims.FLY_ATTACK);
    public BaseAnimation flyAttackAnim = new SimpleAnimation("fly_attack", this.flyAttackData, Loop.ONCE, 1, 0, 0.8F).addListener(this.attackListener);
    public final WalkState walkState = new WalkState();
    public final WalkTracker<Calciferoptera> tracker = WalkTracker.<Calciferoptera>builder().add(mob -> !this.isFlying(), walk -> {
        walk.animLength(1.2F).speedMod(1.0F).addGlobal("step_0", 6.0F).addGlobal("step_1", 12.0F).addGlobal("step_2", 18.0F).addGlobal("step_3", 23.0F);
    }).build();

    public Calciferoptera(@NotNull EntityType<? extends Animal> type, @NotNull Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this);
        this.pack = new PackController<>(this, 5, 16, LEADER_UUID);
        this.setNoGravity(false);
    }

    @Override
    public void initAnimations() {
        MobAnimator<Calciferoptera> animator = this.getAnimator();
        animator.register(this.idleAnim, this.attackAnim, this.flyAnim, this.wingFlapAnim, this.flyAttackAnim);
        animator.registerWalk("walk", CalciferopteraAnims.WALK).registerWalk("fly_walk", CalciferopteraAnims.FLY_WALK);
        animator.setWalkSelector((mob, anim) -> {
            if (!this.isFlying()) {
                animator.setWalk("walk", 1.0F);
            } else {
                animator.setWalk("fly_walk", 1.0F);
            }
        });
    }


    @Override
    public void travel(@NotNull Vec3 vec) {
        if (this.isControlledByLocalInstance()) {
            float speed = (this.isInWater() || this.isInLava()) ? 0.02F : this.getSpeed();
            double friction = this.isInWater() ? 0.8 : this.isInLava() ? 0.5 : 0.97;
            this.moveRelative(speed, vec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(friction));
        }
        this.calculateEntityAnimation(false);
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        this.walkAnimation.update(this.isAlive() ? Math.min(partialTick * 30.0F, 1.0F) : 0.0F, 0.4F);
    }

    @Override
    public void addTags(@NotNull TagBuilder builder) {
        builder.add(FLYING).add(LEADER_UUID);
    }

    @Override
    public boolean isFlying() {
        return FLYING.get(this);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new AnimatableMeleeAttackGoal<Calciferoptera>(this, 1.0F, false).attackCooldownTicks(40).onAttackAction(new BiConsumer<>() {
            @Override
            public void accept(@NotNull LivingEntity enemy, MobAnimator<Calciferoptera> animator) {
                Calciferoptera mob = animator.getEntity();
                if (EntityUtils.isInFront(mob, enemy, Mth.PI / 2.5F)) {
                    if (!animator.isAnyPlaying(attackAnim, flyAttackAnim)) {
                        animator.play(!isFlying() ? attackAnim : flyAttackAnim);
                    }
                }
            }
        }));
        this.goalSelector.addGoal(1, new PackSwarmAttackGoal(this, 1.5));
        this.goalSelector.addGoal(3, new FlyingPackFollowLeaderGoal<>(this, 1.2F, 8.5F));
        this.goalSelector.addGoal(4, new FlyingPackWanderGoal<>(this, 1.0F, 60, 6.0F));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public void tick() {
        super.tick();
        this.pack.update();
        FLYING.set(this, false);
        this.tracker.tick(this, this.walkState, Minecraft.getInstance().getPartialTick());
    }

    @Override
    public void onServerStep(@NotNull StepContext ctx) {
        this.playSound(SoundEvents.CHICKEN_STEP, 1.0F, 0.8F);
    }

    @Contract(pure = true)
    @Override
    public void onClientStep(@NotNull StepContext ctx) {

    }

    @Contract(pure = true)
    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {

    }

    @Contract(pure = true)
    @Override
    public @NotNull PackController<? extends Mob> getPackController() {
        return this.pack;
    }
}