package com.delu.ancienttimes.server.entity.goal;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import org.reaper.skulllib.server.anim.IAnimateable;
import org.reaper.skulllib.server.anim.MobAnimator;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimatableMeleeAttackGoal<M extends Mob & IAnimateable<M>> extends MeleeAttackGoal {
    final IAnimateable<M> animable;
    @Setter
    @Accessors(chain = true, fluent = true)
    Predicate<LivingEntity> attackCondition = target -> true;
    @Setter
    @Accessors(chain = true, fluent = true)
    BiConsumer<LivingEntity, MobAnimator<M>> onAttackAction = (target, animator) -> {};
    @Setter
    @Accessors(chain = true, fluent = true)
    int attackCooldownTicks = 0, currentCooldownTicks = 0;

    @SuppressWarnings("unchecked")
    public AnimatableMeleeAttackGoal(@NotNull PathfinderMob mob, float speed, boolean followEvenIfNotSeen) {
        super(mob, speed, followEvenIfNotSeen);
        if (!(mob instanceof IAnimateable)) {
            throw new IllegalArgumentException("Mob must implement IAnimateable class!");
        }
        this.animable = (IAnimateable<M>) mob;
    }

    @Override
    public void tick() {
        super.tick();
        this.currentCooldownTicks = Math.max(this.currentCooldownTicks - 1, 0);
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double dist) {
        if (dist <= this.getAttackReachSqr(enemy) && this.ticksUntilNextAttack <= 0 && this.currentCooldownTicks <= 0 && this.attackCondition.test(enemy)) {
            this.resetAttackCooldown();
            this.currentCooldownTicks = this.attackCooldownTicks;
            this.onAttackAction.accept(enemy, this.animable.getAnimator());
        }
    }

    @Override
    protected boolean isTimeToAttack() {
        return super.isTimeToAttack() && this.currentCooldownTicks <= 0;
    }

    @Override
    public void start() {
        super.start();
        this.currentCooldownTicks = 0;
    }
}