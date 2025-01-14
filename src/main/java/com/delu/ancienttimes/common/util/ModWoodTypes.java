package com.delu.ancienttimes.common.util;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
public class ModWoodTypes {
    public static final WoodType MEAL = WoodType.register(new WoodType(AncientTimes.MODID + ":meal", BlockSetType.OAK));
}