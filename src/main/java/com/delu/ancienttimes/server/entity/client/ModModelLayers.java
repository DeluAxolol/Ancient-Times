package com.delu.ancienttimes.server.entity.client;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation MEAL_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(AncientTimes.ID, "boat/meal"), "main");
    public static final ModelLayerLocation MEAL_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(AncientTimes.ID, "chest_boat/meal"), "main");

}