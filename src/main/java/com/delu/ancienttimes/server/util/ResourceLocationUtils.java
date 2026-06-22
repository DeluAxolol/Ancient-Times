package com.delu.ancienttimes.server.util;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtils {

    public static ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    public static ResourceLocation prepend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), suffix + rl.getPath());
    }
}
