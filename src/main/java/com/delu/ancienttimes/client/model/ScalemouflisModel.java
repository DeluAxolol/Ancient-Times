package com.delu.ancienttimes.client.model;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Scalemouflis;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ScalemouflisModel extends GeoModel<Scalemouflis> {
    @Override
    public ResourceLocation getModelResource(Scalemouflis scalemouflis) {
        return AncientTimes.geo("scalemouflis/scalemouflis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Scalemouflis scalemouflis) {
        Scalemouflis.ScalemouflisTextures.add(AncientTimes.entityTexture("scalemouflis/scalemouflistexturevariant1.png"));
        Scalemouflis.ScalemouflisTextures.add(AncientTimes.entityTexture("scalemouflis/scalemouflistexturevariant2.png"));
        Scalemouflis.ScalemouflisTextures.add(AncientTimes.entityTexture("scalemouflis/scalemouflistexturevariant3.png"));
        Scalemouflis.ScalemouflisTextures.add(AncientTimes.entityTexture("scalemouflis/scalemouflistexturevariant4.png"));
        Scalemouflis.ScalemouflisTextures.add(AncientTimes.entityTexture("scalemouflis/scalemouflistexturevariant5.png"));
        Scalemouflis.ScalemouflisTextures.add(AncientTimes.entityTexture("scalemouflis/scalemouflistexturevariant6.png"));
        return Scalemouflis.ScalemouflisTextures.get(scalemouflis.getVarient());
    }

    @Override
    public ResourceLocation getAnimationResource(Scalemouflis scalemouflis) {
        return AncientTimes.animations("scalemouflis.animation.json");
    }
}
