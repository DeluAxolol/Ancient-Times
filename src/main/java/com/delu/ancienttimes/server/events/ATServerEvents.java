package com.delu.ancienttimes.server.events;


import com.delu.ancienttimes.AncientTimes;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.minecraftforge.fml.common.Mod;

@Slf4j
@UtilityClass
@Mod.EventBusSubscriber(modid = AncientTimes.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ATServerEvents {

}