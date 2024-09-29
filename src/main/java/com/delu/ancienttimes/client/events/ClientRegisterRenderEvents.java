package com.delu.ancienttimes.client.events;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.client.model.TrioclantusModel;
import com.delu.ancienttimes.registries.ModEntities;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Mod.EventBusSubscriber(modid = AncientTimes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegisterRenderEvents {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.TRIOCLANTUS.get(), createGeoRenderer(new TrioclantusModel()));
    }

    public static <T extends LivingEntity & GeoEntity> EntityRendererProvider<T> createGeoRenderer(GeoModel<T> model) {
        return m -> new SimpleGeoRenderer<>(m, model);
    }

    protected static class SimpleGeoRenderer<T extends LivingEntity & GeoEntity> extends GeoEntityRenderer<T> {

        public SimpleGeoRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
            super(renderManager, modelProvider);
        }
    }
}
