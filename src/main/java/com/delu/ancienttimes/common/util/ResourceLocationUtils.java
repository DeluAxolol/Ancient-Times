package com.delu.ancienttimes.common.util;

import net.minecraft.util.Identifier;

public class ResourceLocationUtils {

    public static Identifier extend(Identifier rl, String suffix) {
        return new Identifier(rl.getNamespace(), rl.getPath() + suffix);
    }

    public static Identifier prepend(Identifier rl, String suffix) {
        return new Identifier(rl.getNamespace(), suffix + rl.getPath());
    }
}
