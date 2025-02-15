package com.delu.ancienttimes.client.renderer;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.item.RavenheadsFruitEntity;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RavenheadsFruitRenderer extends EntityRenderer<RavenheadsFruitEntity> {

    private static final Identifier TEXTURE = new Identifier(AncientTimes.MODID, "textures/item/ravenheads_fruit.png");

    public RavenheadsFruitRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(RavenheadsFruitEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider buffer, int pPackedLight) {
        // Retrieve the item being thrown
        ItemStack itemStack = new ItemStack(ModItems.RAVENHEADS_FRUIT);

        // Check if the item is not empty
        // Render the item using Minecraft's ItemRenderer
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(itemStack, entity.getWorld(), null, 0);
        itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND, false, poseStack, buffer, pPackedLight, 0, model);


        super.render(entity, entityYaw, partialTick, poseStack, buffer, pPackedLight);
    }

    @Override
    public Identifier getTexture(RavenheadsFruitEntity entity) {
        return TEXTURE;  // Change to your texture path
    }
}