package com.delu.ancienttimes.client.model;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Triclantus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TrioclantusModel extends GeoModel<Triclantus> {
    @Override
    public ResourceLocation getModelResource(Triclantus animatable) {
        return animatable.isBaby() ? AncientTimes.geo("trioclantus_baby.geo.json") : AncientTimes.geo("trioclantus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Triclantus animatable) {
        return animatable.isBaby() ? AncientTimes.entityTexture("trioclantus/trioclantus_baby_texture.png") : AncientTimes.entityTexture("trioclantus/trioclantus_adult.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Triclantus animatable) {
        return animatable.isBaby() ?  AncientTimes.animations("trioclantus.animation.json") : AncientTimes.animations("trioclantus.animation.json");
    }
}
