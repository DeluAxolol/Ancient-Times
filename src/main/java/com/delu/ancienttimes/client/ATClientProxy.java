package com.delu.ancienttimes.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Contract;
import org.reaper.skulllib.IModProxy;

@OnlyIn(Dist.CLIENT)
public final class ATClientProxy implements IModProxy {
    @Contract(pure = true)
    @Override
    public void commonInit() {

    }

    @Contract(pure = true)
    @Override
    public void clientInit() {

    }
}