package com.delu.ancienttimes.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlyingMoveControl extends MoveControl {
    float currentRoll = 0.0F;
    float targetRoll = 0.0F;
    float currentPitch = 0.0F;
    float targetPitch = 0.0F;
    double dragFactor = 0.95D;
    double accelerationFactor = 0.04D;
    float rotationLerpSpeed = 0.05F;
    boolean lockLookTarget = false;

    public FlyingMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        double dx = this.wantedX - this.mob.getX();
        double dy = this.wantedY - this.mob.getY();
        double dz = this.wantedZ - this.mob.getZ();
        double distanceSq = dx * dx + dy * dy + dz * dz;
        if (this.operation == Operation.MOVE_TO && distanceSq > 0.0001D) {
            double distance = Math.sqrt(distanceSq);
            double targetSpeed = this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.speedModifier;
            double targetVx = (dx / distance) * targetSpeed;
            double targetVy = (dy / distance) * targetSpeed;
            double targetVz = (dz / distance) * targetSpeed;
            Vec3 currentVel = this.mob.getDeltaMovement();
            double nextX = Mth.lerp(this.accelerationFactor, currentVel.x, targetVx);
            double nextY = Mth.lerp(this.accelerationFactor, currentVel.y, targetVy);
            double nextZ = Mth.lerp(this.accelerationFactor, currentVel.z, targetVz);
            this.mob.setDeltaMovement(nextX, nextY, nextZ);
            if (this.lockLookTarget) {
                this.mob.setYRot(this.mob.yHeadRot);
            } else {
                float moveYaw = (float) (Mth.atan2(targetVz, targetVx) * (180.0D / Math.PI)) - 90.0F;
                float smoothYaw = this.mob.getYRot() + Mth.wrapDegrees(moveYaw - this.mob.getYRot()) * this.rotationLerpSpeed;
                this.mob.setYRot(smoothYaw);
            }
            this.mob.yBodyRot = this.mob.yBodyRot + Mth.wrapDegrees(this.mob.getYRot() - this.mob.yBodyRot) * this.rotationLerpSpeed;
            this.mob.yHeadRot = this.mob.yHeadRot + Mth.wrapDegrees(this.mob.getYRot() - this.mob.yHeadRot) * this.rotationLerpSpeed;
            double ax = nextX - currentVel.x;
            double az = nextZ - currentVel.z;
            float radYaw = this.mob.getYRot() * (float) (Math.PI / 180.0D);
            double cosYaw = Mth.cos(radYaw);
            double sinYaw = Mth.sin(radYaw);
            double localForward = -(ax * sinYaw) + (az * cosYaw);
            double localRight = (ax * cosYaw) + (az * sinYaw);
            this.targetPitch = Mth.clamp((float) (-localForward * 45.0D), -30.0F, 30.0F);
            this.targetRoll = Mth.clamp((float) (localRight * 45.0D), -30.0F, 30.0F);
            this.currentPitch = Mth.lerp(this.rotationLerpSpeed, this.currentPitch, this.targetPitch);
            this.currentRoll = Mth.lerp(this.rotationLerpSpeed, this.currentRoll, this.targetRoll);
            this.mob.setXRot(this.currentPitch);
            this.mob.setSpeed(0.0F);
            this.mob.setZza(0.0F);
            this.mob.setYya(0.0F);
            this.mob.setXxa(0.0F);
        } else {
            this.targetPitch = 0.0F;
            this.targetRoll = 0.0F;
            this.currentPitch = Mth.lerp(this.rotationLerpSpeed, this.currentPitch, 0.0F);
            this.currentRoll = Mth.lerp(this.rotationLerpSpeed, this.currentRoll, 0.0F);
            this.mob.setXRot(this.currentPitch);
            this.mob.yBodyRot = this.mob.yBodyRot + Mth.wrapDegrees(this.mob.getYRot() - this.mob.yBodyRot) * this.rotationLerpSpeed;
            this.mob.yHeadRot = this.mob.yHeadRot + Mth.wrapDegrees(this.mob.getYRot() - this.mob.yHeadRot) * this.rotationLerpSpeed;
            Vec3 currentVel = this.mob.getDeltaMovement();
            this.mob.setDeltaMovement(currentVel.x * this.dragFactor, currentVel.y * this.dragFactor, currentVel.z * this.dragFactor);
            this.mob.setSpeed(0.0F);
            this.mob.setZza(0.0F);
            this.mob.setYya(0.0F);
            this.mob.setXxa(0.0F);

            if (this.operation == Operation.MOVE_TO) {
                this.operation = Operation.WAIT;
            }
        }
    }
}