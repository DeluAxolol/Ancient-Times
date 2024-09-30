package com.delu.ancienttimes.client.model;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Trioclantus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TrioclantusModel extends GeoModel<Trioclantus> {
    @Override
    public ResourceLocation getModelResource(Trioclantus animatable) {
        return animatable.isBaby() ? AncientTimes.geo("trioclantus/trioclantus_baby.geo.json") : AncientTimes.geo("trioclantus/trioclantus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Trioclantus animatable) {
        return animatable.isBaby() ? AncientTimes.entityTexture("trioclantus/trioclantus_baby_texture.png") : AncientTimes.entityTexture("trioclantus/trioclantus_adult.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Trioclantus animatable) {
        return AncientTimes.animations("trioclantus.animation.json");
    }
}
