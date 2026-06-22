package com.delu.ancienttimes.server.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.reaper.skulllib.math.SLIBMath;
import org.reaper.skulllib.math.Vec3Line;
import org.reaper.skulllib.math.Vec3Ring;

public interface ATFlyingAnimal extends FlyingAnimal {
    @Nullable BlockPos getOrbitPos();

    void setOrbitPos(@Nullable BlockPos pos);

    double getOrbitDist();

    boolean isOrbitClockwise();

    boolean isGrounded();

    void setGrounded(boolean grounded);

    @Nullable BlockPos getLandingTarget();

    void setLandingTarget(@Nullable BlockPos target);

    int getGroundTicks();

    void setGroundTicks(int ticks);

    int getAirTicks();

    void setAirTicks(int ticks);

    default @NotNull BlockHitResult raycastFlightPath(@NotNull Vec3 dir, double dist) {
        Mob mob = (Mob) this;
        Vec3 start = new Vec3(mob.getX(), mob.getEyeY(), mob.getZ());
        Vec3 end = start.add(dir.normalize().scale(dist));
        return mob.level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
    }

    default @NotNull BlockPos getActualGroundBelow(@NotNull BlockPos pos) {
        Mob mob = (Mob) this;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
        while (mutablePos.getY() > mob.level.getMinBuildHeight()) {
            if (!mob.level.isEmptyBlock(mutablePos)) {
                return mutablePos.immutable();
            }
            mutablePos.move(0, -1, 0);
        }
        return mutablePos.immutable();
    }

    default @Nullable Vec3 calculateLandPos(int radius, int yRange) {
        Mob mob = (Mob) this;
        Vec3 result = null;
        if (mob instanceof PathfinderMob pathfinder) {
            boolean isRestricted = GoalUtils.mobRestricted(pathfinder, radius);
            result = RandomPos.generateRandomPos(() -> {
                BlockPos candidate = null;
                Vec3 up = new Vec3(0.0F, 1.0F, 0.0F);
                Vec3 normal = mob.getLookAngle().cross(up).normalize();
                Vec3 pos = new Vec3Ring(mob.position(), normal, radius * mob.getRandom().nextDouble()).getPointAtAngle(mob.getRandom().nextDouble() * 2.0F * Mth.PI);
                BlockPos target = BlockPos.containing(pos.x, pos.y + (mob.getRandom().nextInt(yRange * 2) - yRange), pos.z);
                if (!GoalUtils.isOutsideLimits(target, pathfinder) && !GoalUtils.isRestricted(isRestricted, pathfinder, target) && !GoalUtils.isNotStable(pathfinder.getNavigation(), target)) {
                    BlockPos movedTarget = RandomPos.moveUpOutOfSolid(target, pathfinder.level().getMaxBuildHeight(), p -> GoalUtils.isSolid(pathfinder, p));
                    if (!GoalUtils.hasMalus(pathfinder, movedTarget) && this.isFlightPathClear(Vec3.atBottomCenterOf(movedTarget), 1.0F)) {
                        candidate = movedTarget;
                    }
                }
                return candidate;
            }, pathfinder::getWalkTargetValue);
        }
        return result;
    }

    default double getDistToGround() {
        Mob mob = (Mob) this;
        return mob.getY() - this.getActualGroundBelow(mob.blockPosition()).getY();
    }

    default boolean isOutOfBounds(int y) {
        Mob mob = (Mob) this;
        return y < mob.level.getMinBuildHeight() || y > mob.level.getMaxBuildHeight();
    }

    default boolean isOverDanger() {
        Mob mob = (Mob) this;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(mob.getBlockX(), mob.getBlockY(), mob.getBlockZ());
        while (mutablePos.getY() > mob.level.getMinBuildHeight()) {
            if (!mob.level.isEmptyBlock(mutablePos)) {
                return !mob.level.getFluidState(mutablePos).isEmpty();
            }
            mutablePos.move(0, -1, 0);
        }
        return true;
    }

    default boolean isSafeToLand() {
        Mob mob = (Mob) this;
        BlockPos below = this.getActualGroundBelow(mob.blockPosition());
        return mob.level.getBlockState(below).getFluidState().isEmpty();
    }

    default void performTakeoff() {
        Mob mob = (Mob) this;
        this.setGrounded(false);
        this.setAirTicks(0);
        mob.setNoGravity(true);
    }

    default void performLanding() {
        Mob mob = (Mob) this;
        this.setGrounded(true);
        this.setGroundTicks(0);
        mob.setNoGravity(false);
    }

    default @NotNull Vector2f getRotationsToTarget(@NotNull Vec3 target) {
        Mob mob = (Mob) this;
        return SLIBMath.getYawPitch(new Vec3(mob.getX(), mob.getEyeY(), mob.getZ()), target);
    }

    default @NotNull Vec3 calculateOrbitPosition(@NotNull Vec3 center, @NotNull Vec3 normal, double currentAngleRad) {
        return new Vec3Ring(center, normal.normalize(), this.getOrbitDist()).getPointAtAngle(this.isOrbitClockwise() ? currentAngleRad : -currentAngleRad);
    }

    default @NotNull Vec3 calculateOrbitVelocity(@NotNull Vec3 center, @NotNull Vec3 normal, double currentAngleRad) {
        return new Vec3Ring(center, normal.normalize(), this.getOrbitDist()).getTangentAtAngle(this.isOrbitClockwise() ? currentAngleRad : -currentAngleRad);
    }

    default @NotNull Vec3 lerpToOrbit(@NotNull Vec3 currentPos, @NotNull Vec3 targetOrbitPos, double factor) {
        return currentPos.add(targetOrbitPos.subtract(currentPos).scale(factor));
    }

    default boolean isFlightPathClear(@NotNull Vec3 target, double stepSize) {
        Mob mob = (Mob) this;
        for (Vec3 point : new Vec3Line(new Vec3(mob.getX(), mob.getEyeY(), mob.getZ()), target).getPoints(stepSize)) {
            if (!mob.level.isEmptyBlock(BlockPos.containing(point.x, point.y, point.z))) {
                return false;
            }
        }
        return true;
    }

    default boolean isAtSafeFlightHeight(int minHeight, int maxHeight) {
        Mob mob = (Mob) this;
        float y = (float) mob.getY();
        return y >= minHeight && y <= maxHeight;
    }
}