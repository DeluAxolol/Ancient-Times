package com.delu.ancienttimes.common.events;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.entity.Scalemouflis;
import com.delu.ancienttimes.common.entity.Trioclantus;
import com.delu.ancienttimes.registries.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AncientTimes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegisterEvents {

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.TRIOCLANTUS.get(), Trioclantus.createAttributes().build());
        event.put(ModEntities.SCALEMOUFLIS.get(), Scalemouflis.createAttributes().build());
    }
}
