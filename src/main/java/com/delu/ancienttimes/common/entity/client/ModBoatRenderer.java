package com.delu.ancienttimes.common.entity.client;

import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class ModBoatRenderer extends BoatEntityRenderer {
    public ModBoatRenderer(EntityRendererFactory.Context pContext, boolean pChestBoat) {
        super(pContext, pChestBoat);
    }
}
