package com.delu.ancienttimes;

import com.delu.ancienttimes.common.worldgen.tree.ModTrunkPlacerTypes;
import com.delu.ancienttimes.registries.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AncientTimes.MODID)
public class AncientTimes {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "ancienttimes";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String name){
        return new ResourceLocation(MODID, name);
    }

    public static ResourceLocation geo(String name) {
        return modLoc("geo/" + name);
    }

    public static ResourceLocation animations(String name) {
        return modLoc("animations/" + name);
    }

    public static ResourceLocation entityTexture(String name) {
        return modLoc("textures/entity/" + name);
    }

    public AncientTimes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIER_SERIALIZER.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModTrunkPlacerTypes.register(modEventBus);

        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


}
