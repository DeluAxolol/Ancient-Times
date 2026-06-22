package com.delu.ancienttimes.server.entity.goal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.reaper.skulllib.server.entity.IPackAnimal;
import org.reaper.skulllib.server.entity.PackController;

import java.util.EnumSet;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlyingPackWanderGoal<T extends PathfinderMob & IPackAnimal> extends Goal {
    T mob;
    double speed;
    int interval;
    float spacing;
    @NonFinal
    boolean forceTrigger;

    {
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean canUse() {
        PackController<T> controller = (PackController<T>) this.mob.getPackController();
        boolean shouldUse = false;

        if (!this.mob.isVehicle()) {
            if (controller.isLeader()) {
                if (controller.getWanderTarget() != null) {
                    shouldUse = true;
                } else if (this.forceTrigger || this.mob.getRandom().nextInt(Goal.reducedTickDelay(this.interval)) == 0) {
                    // Прямой вызов, минуя обертку AirRandomPos
                    Vec3 target = AirAndWaterRandomPos.getPos(
                            this.mob, 15, 7, 7,
                            this.mob.getX(), this.mob.getZ(),
                            (double)(Mth.PI / 2.0F)
                    );

                    if (target != null) {
                        controller.setWanderTarget(target);
                        this.forceTrigger = false;
                        shouldUse = true;
                    }
                }
            } else if (controller.hasLeader()) {
                Vec3 packTarget = controller.getWanderTarget();
                if (packTarget != null && this.mob.distanceToSqr(packTarget) > (Mth.square(this.spacing))) {
                    shouldUse = true;
                }
            }
        }

        return shouldUse;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start() {
        PackController<T> controller = (PackController<T>) this.mob.getPackController();
        Vec3 target = controller.getWanderTarget();
        if (target != null) {
            if (controller.isLeader()) {
                this.mob.getNavigation().moveTo(target.x, target.y, target.z, this.speed);
            } else {
                Vec3 pos = this.getSlotPosition(target, controller);
                this.mob.getNavigation().moveTo(pos.x, pos.y, pos.z, this.speed);
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getPackController().getWanderTarget() != null && !this.mob.getNavigation().isDone();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void stop() {
        PackController<T> controller = (PackController<T>) this.mob.getPackController();
        if (controller.isLeader()) {
            controller.setWanderTarget(null);
        }
        this.mob.getNavigation().stop();
    }

    public void trigger() {
        this.forceTrigger = true;
    }

    @SuppressWarnings("unchecked")
    private @NotNull Vec3 getSlotPosition(Vec3 target, @NotNull PackController<T> controller) {
        Vec3 result = target;
        if (controller.hasLeader()) {
            T leader = controller.getLeader();
            PackController<T> leaderController = (PackController<T>) leader.getPackController();
            int index = leaderController.getMembers().indexOf(this.mob);
            if (index != -1) {
                double angle = index * (Mth.PI * 2.0F / 8);
                result = new Vec3(target.x + Math.cos(angle) * this.spacing, target.y, target.z + Math.sin(angle) * this.spacing);
            }
        }
        return result;
    }
}