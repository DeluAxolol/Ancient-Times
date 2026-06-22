package com.delu.ancienttimes.server.entity.goal;

import com.delu.ancienttimes.server.entity.Calciferoptera;
import com.delu.ancienttimes.server.entity.FlyingMoveControl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Accessors(chain = true)
public class PackSwarmAttackGoal extends Goal {
    final Calciferoptera mob;
    final double speedModifier;
    LivingEntity target;
    int attackCooldown;
    boolean swooping;
    double angleOffset;
    Vec3 orbitNormal;

    public PackSwarmAttackGoal(Calciferoptera mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackCooldown = this.mob.getRandom().nextInt(60) + 40;
        this.swooping = false;
        this.angleOffset = this.mob.getId() * 45.0D;
        this.orbitNormal = new Vec3(0.0D, 1.0D, 0.0D);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
    }

    @Override
    public void start() {
        this.target = this.mob.getTarget();
        this.mob.getNavigation().stop();
        this.mob.setOrbitDist(10.0D);
        this.mob.setOrbitClockwise(this.mob.getRandom().nextBoolean());
        this.orbitNormal = new Vec3(this.mob.getRandom().nextDouble() - 0.5D, 1.0D, this.mob.getRandom().nextDouble() - 0.5D).normalize();
    }

    @Override
    public void stop() {
        this.target = null;
        this.swooping = false;
        this.mob.getNavigation().stop();
        if (this.mob.getMoveControl() instanceof FlyingMoveControl) {
            ((FlyingMoveControl) this.mob.getMoveControl()).setLockLookTarget(false);
        }
    }

    @Override
    public void tick() {
        this.target = this.mob.getTarget();
        if (this.target != null) {
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            if (this.mob.getPackController().hasLeader() || this.mob.getPackController().isTrueLeader()) {
                if (this.attackCooldown > 0) {
                    this.attackCooldown--;
                }
                if (this.attackCooldown <= 0 && !this.swooping) {
                    this.swooping = true;
                }
                if (this.swooping) {
                    if (this.mob.getMoveControl() instanceof FlyingMoveControl) {
                        ((FlyingMoveControl) this.mob.getMoveControl()).setLockLookTarget(false);
                    }
                    if (this.mob.isFlightPathClear(this.target.getEyePosition().add(this.target.getDeltaMovement().scale(5.0D)), 1.0D)) {
                        this.mob.getMoveControl().setWantedPosition(this.target.getEyePosition().add(this.target.getDeltaMovement().scale(5.0D)).x, this.target.getEyePosition().add(this.target.getDeltaMovement().scale(5.0D)).y, this.target.getEyePosition().add(this.target.getDeltaMovement().scale(5.0D)).z, this.speedModifier * 1.6D);
                    } else {
                        this.swooping = false;
                        this.attackCooldown = 20;
                    }
                    if (this.mob.distanceToSqr(this.target) < 5.0D) {
                        if (!this.mob.getAnimator().isAnyPlaying(this.mob.attackAnim, this.mob.flyAttackAnim)) {
                            this.mob.getAnimator().play(this.mob.isFlying() ? this.mob.flyAttackAnim : this.mob.attackAnim);
                        }
                        this.swooping = false;
                        this.attackCooldown = 80 + this.mob.getRandom().nextInt(100);
                        this.mob.setOrbitClockwise(this.mob.getRandom().nextBoolean());
                        this.orbitNormal = new Vec3(this.mob.getRandom().nextDouble() - 0.5D, 1.0D, this.mob.getRandom().nextDouble() - 0.5D).normalize();
                    }
                } else {
                    if (this.mob.getMoveControl() instanceof FlyingMoveControl) {
                        ((FlyingMoveControl) this.mob.getMoveControl()).setLockLookTarget(true);
                    }
                    Vec3 orbitPos = this.mob.lerpToOrbit(this.mob.position(), this.mob.calculateOrbitPosition(this.target.getEyePosition().add(0.0D, 4.0D + Math.sin((double) this.mob.tickCount * 0.05D) * 2.0D, 0.0D), this.orbitNormal, ((double) this.mob.tickCount * 0.05D + Math.toRadians(this.angleOffset))), 0.15D);
                    this.mob.getMoveControl().setWantedPosition(orbitPos.x, orbitPos.y, orbitPos.z, this.speedModifier);
                }
            } else {
                if (this.mob.getMoveControl() instanceof FlyingMoveControl) {
                    ((FlyingMoveControl) this.mob.getMoveControl()).setLockLookTarget(false);
                }
                this.mob.getMoveControl().setWantedPosition(this.target.getEyePosition().x, this.target.getEyePosition().y, this.target.getEyePosition().z, this.speedModifier * 1.2D);
                if (this.mob.distanceToSqr(this.target) < 5.0D && !this.mob.getAnimator().isAnyPlaying(this.mob.attackAnim, this.mob.flyAttackAnim)) {
                    this.mob.getAnimator().play(this.mob.isFlying() ? this.mob.flyAttackAnim : this.mob.attackAnim);
                }
            }
        }
    }
}