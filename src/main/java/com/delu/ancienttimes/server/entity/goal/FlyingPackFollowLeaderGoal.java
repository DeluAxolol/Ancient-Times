package com.delu.ancienttimes.server.entity.goal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.reaper.skulllib.server.entity.IPackAnimal;
import org.reaper.skulllib.server.entity.PackController;

import java.util.EnumSet;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlyingPackFollowLeaderGoal<T extends Mob & IPackAnimal> extends Goal {
    T mob;
    double speedModifier;
    float spacing;
    @NonFinal
    int timeToRecalcPath;

    {
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean canUse() {
        PackController<T> controller = (PackController<T>) this.mob.getPackController();
        boolean result = false;
        if (controller.hasLeader()) {
            T leader = controller.getLeader();
            result = leader != null && leader.isAlive() && this.mob.distanceToSqr(this.getSlotPosition(leader)) > 2.25F;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void tick() {
        PackController<T> controller = (PackController<T>) this.mob.getPackController();
        T leader = controller.getLeader();
        if (leader != null) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                Vec3 pos = this.getSlotPosition(leader);
                this.mob.getNavigation().moveTo(pos.x, pos.y, pos.z, this.speedModifier);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private @NotNull Vec3 getSlotPosition(@NotNull T leader) {
        Vec3 result = leader.position();
        PackController<T> leaderController = (PackController<T>) leader.getPackController();
        int index = leaderController.getMembers().indexOf(this.mob);
        if (index != -1) {
            float angleOffset = ((index % 2) == 0) ? 135.0F : -135.0F;
            float totalAngle = (leader.yBodyRot + angleOffset) * (Mth.PI / 180.0F);
            double dist = (((double) index / 2) + 1) * this.spacing;
            result = new Vec3(leader.getX() + -Math.sin(totalAngle) * dist, leader.getY(), leader.getZ() + Math.cos(totalAngle) * dist);
        }
        return result;
    }
}