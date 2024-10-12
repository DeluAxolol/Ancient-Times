package com.delu.ancienttimes.common.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.function.Predicate;

public class BlockFindGoal<T extends PathfinderMob> extends Goal {

    protected final T animal;
    protected final int searchRange;
    protected BlockPos target;
    protected int stuckCooldown;

    protected final Predicate<BlockState> finder;
    protected final Predicate<T> canStart;

    public BlockFindGoal(T animal, int searchRange, Predicate<BlockState> finder, Predicate<T> canStart) {
        this.animal = animal;
        this.searchRange = searchRange;
        this.finder = finder;
        this.canStart = canStart;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public BlockFindGoal(T animal, int searchRange, Predicate<BlockState> finder) {
       this(animal, searchRange, finder, ignored -> true);
    }

    public BlockFindGoal(T animal, int searchRange, TagKey<Block> possibleBlocks) {
        this(animal, searchRange, state -> state.is(possibleBlocks), ignored -> true);
    }

    @Override
    public boolean canUse() {
        if (!finder.test(this.animal.getBlockStateOn()) && isPanicing() && animal.getLastHurtByMob() == null && !animal.isBaby() && canStart.test(animal)) {
            findTarget();
            return this.target != null;
        }
        return false;
    }


    protected void findTarget() {
        this.target = null;
        for (int i = 0; i < 10; i++) {
            RandomSource rand = animal.level().random;
            BlockPos pos = new BlockPos((int) (this.animal.getX() + rand.nextInt(2 * searchRange + 1) - searchRange), animal.level().getMaxBuildHeight(), (int) (this.animal.getZ() + rand.nextInt(2 * searchRange + 1) - searchRange));
            while (pos.getY() > 0 && !finder.test(animal.level().getBlockState(pos)) && !animal.level().getBlockState(pos).isCollisionShapeFullBlock(animal.level(), pos)) {
                pos = pos.below();
            }
            if (finder.test(animal.level().getBlockState(pos))) {
                this.target = pos;
                return;
            }
        }
    }

    @Override
    public void start() {
        this.animal.getNavigation().moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 1d);
    }


    @Override
    public void tick() {
        this.animal.getLookControl().setLookAt(Vec3.atCenterOf(this.target));
        if (!this.animal.getNavigation().moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 1d)) {
            stuckCooldown++;
        } else {
            stuckCooldown = 0;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (stuckCooldown > 200) {
            return false;
        }if (this.animal.distanceToSqr(Vec3.atCenterOf(this.target)) <= .25d || this.animal.getNavigation().isDone()) {
            return false;
        }
        return !finder.test(this.animal.getBlockStateOn()) && isPanicing() && animal.getLastHurtByMob() == null && !animal.isBaby() && canStart.test(animal);
    }

    @Override
    public void stop() {
        this.animal.getNavigation().stop();
        this.target = null;
        this.stuckCooldown = 0;
    }

    protected boolean isPanicing(){
        return animal.getLastHurtByMob() == null && !animal.isFreezing() && !animal.isOnFire();
    }
}
