package com.delu.ancienttimes.server.util;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
public class ModWoodTypes {
    public static final WoodType MEAL = WoodType.register(new WoodType(AncientTimes.ID + ":meal", BlockSetType.OAK));
}