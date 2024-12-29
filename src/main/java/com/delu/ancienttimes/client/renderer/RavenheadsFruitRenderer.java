package com.delu.ancienttimes.client.renderer;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.item.RavenheadsFruitEntity;
import com.delu.ancienttimes.registries.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RavenheadsFruitRenderer extends EntityRenderer<RavenheadsFruitEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("ancienttimes", "textures/item/ravenheads_fruit.png");

    public RavenheadsFruitRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(RavenheadsFruitEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int pPackedLight) {
        // Retrieve the item being thrown
        ItemStack itemStack = new ItemStack(ModItems.RAVENHEADS_FRUIT.get());

        // Check if the item is not empty
        // Render the item using Minecraft's ItemRenderer
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(itemStack, entity.level(), null, 0);
        itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, buffer, pPackedLight, 0, model);


        super.render(entity, entityYaw, partialTick, poseStack, buffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(RavenheadsFruitEntity entity) {
        return TEXTURE;  // Change to your texture path
    }
}