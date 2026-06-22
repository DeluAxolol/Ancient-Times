package com.delu.ancienttimes.server.events;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.server.entity.Rumoroxl;
import com.delu.ancienttimes.server.entity.Scalemouflis;
import com.delu.ancienttimes.server.entity.Trioclantus;
import com.delu.ancienttimes.registries.ATEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AncientTimes.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegisterEvents {

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event){
        event.put(ATEntities.TRIOCLANTUS.get(), Trioclantus.createAttributes().build());
        event.put(ATEntities.SCALEMOUFLIS.get(), Scalemouflis.createAttributes().build());
        event.put(ATEntities.RUMOROXL.get(), Rumoroxl.createAttributes().build());
    }
}
