package com.delu.ancienttimes.client.model;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Rumoroxl;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RumoroxlModel extends GeoModel<Rumoroxl> {
    @Override
    public ResourceLocation getModelResource(Rumoroxl rumoroxl) {
        return AncientTimes.geo("rumoroxl/rumoroxl.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Rumoroxl rumoroxl) {
        return rumoroxl.getVariant().getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(Rumoroxl rumoroxl) {
        return AncientTimes.animations("rumoroxl.animation.json");
    }
}
