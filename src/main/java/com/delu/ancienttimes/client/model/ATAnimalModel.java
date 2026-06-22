package com.delu.ancienttimes.client.model;

import com.delu.ancienttimes.server.entity.ATAnimal;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reaper.skulllib.client.anim.AnimBlend;
import org.reaper.skulllib.client.model.ModelUtils;
import org.reaper.skulllib.server.anim.IAnimation;
import org.reaper.skulllib.server.anim.MobAnimator;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@OnlyIn(Dist.CLIENT)
public abstract class ATAnimalModel<T extends ATAnimal<T>> extends HierarchicalModel<T> {
    @Override
    public void setupAnim(@NotNull T mob, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        ModelUtils.resetPoses(this);
        this.applyHeadRotation(mob, headYaw, limbSwingAmount, ageInTicks);
        this.dynamicTail(mob);
        MobAnimator<T> animator = mob.getAnimator();
        AnimBlend.applyWalk(this, animator, limbSwing, limbSwingAmount, Minecraft.getInstance().getFrameTime(), this.getWalkBlendDuration());
        for (int layer : animator.getLayers().keySet()) {
            IAnimation current = animator.getCurrent(layer);
            if (current != MobAnimator.EMPTY && animator.isPlaying(current.getName())) {
                AnimBlend.applyBlended(this, animator, ageInTicks, this.getBlendDuration(), layer);
            }
        }
    }

    protected abstract void dynamicTail(@NotNull T mob);

    protected abstract void applyHeadRotation(@NotNull T mob, float headYaw, float headPitch, float ageInTicks);

    public @Nullable ResourceLocation getTexture(@NotNull T mob) {
        return null;
    }

    public float shadowRadius() {
        return 1.0F;
    }

    public long getWalkBlendDuration() {
        return 300L;
    }

    public long getBlendDuration() {
        return 200L;
    }
}