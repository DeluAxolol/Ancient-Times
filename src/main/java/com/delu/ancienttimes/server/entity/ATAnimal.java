package com.delu.ancienttimes.server.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reaper.skulllib.server.anim.IAnimateable;
import org.reaper.skulllib.server.anim.MobAnimator;
import org.reaper.skulllib.server.tag.ITagProvider;
import org.reaper.skulllib.server.tag.TagBuilder;

public abstract class ATAnimal <T extends ATAnimal<T>> extends Animal implements IAnimateable<T>, ITagProvider {
    @Nullable
    public MobAnimator<T> animator = null;

    public ATAnimal(@NotNull EntityType<? extends Animal> type, @NotNull Level level) {
        super(type, level);

    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull MobAnimator<T> getAnimator() {
        if (this.animator == null) {
            this.animator = new MobAnimator<>((T) this);
        }
        return this.animator;
    }

    @Override
    public void initAnimations() {

    }

    @Override
    public void addTags(@NotNull TagBuilder builder) {

    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Contract(pure = true)
    @Override
    public boolean canSpawnSprintParticle() {
        return false;
    }

    @Contract(pure = true)
    @Override
    protected void spawnSprintParticle() {

    }
}