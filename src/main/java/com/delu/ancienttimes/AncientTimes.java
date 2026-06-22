package com.delu.ancienttimes;

import com.delu.ancienttimes.client.ATClientProxy;
import com.delu.ancienttimes.registries.*;
import com.delu.ancienttimes.server.ATServerProxy;
import com.mojang.logging.LogUtils;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.reaper.skulllib.BaseMod;
import org.reaper.skulllib.IModProxy;
import org.slf4j.Logger;

@Slf4j
@Mod(AncientTimes.ID)
public final class AncientTimes extends BaseMod<IModProxy> {
    public static final String ID = "ancienttimes";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String name){
        return new ResourceLocation(ID, name);
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
        super(ATServerProxy::new);
        ModItems.ITEMS.register(this.bus);
        ModBlocks.BLOCKS.register(this.bus);
        ModLootModifiers.LOOT_MODIFIER_SERIALIZER.register(this.bus);
        ATEntities.ENTITIES.register(this.bus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(this.bus);
        ModPlacedFeatures.register(this.bus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(this.bus);
        ModParticles.PARTICLES.register(this.bus);
    }

    @Contract(value = " -> new", pure = true)
    @Override
    protected @NotNull IModProxy createClientProxy() {
        return new ATClientProxy();
    }
}
