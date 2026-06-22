package com.delu.ancienttimes.client.model;

import com.delu.ancienttimes.ATConstants;
import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.server.entity.Calciferoptera;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;import net.minecraft.world.phys.Vec3;import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.reaper.skulllib.client.model.AutoLayer;
import org.reaper.skulllib.client.model.IBakableModel;
import org.reaper.skulllib.client.model.ModelUtils;

@AutoLayer(modid = AncientTimes.ID)
@OnlyIn(Dist.CLIENT)
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public final class CalciferopteraModel extends ATAnimalModel<Calciferoptera> implements IBakableModel {
    public static final ResourceLocation CALCIFEROPTERA_LOCATION = ATConstants.RESOURCE.entity("calciferoptera/calciferoptera");

    private final ModelPart root;
    private final ModelPart Calci;
    private final ModelPart body;
    private final ModelPart left_wing1;
    private final ModelPart left_wing2;
    private final ModelPart tail;
    private final ModelPart tail_tip;
    private final ModelPart head;
    private final ModelPart tongue;
    private final ModelPart tongue2;
    private final ModelPart tongue3;
    private final ModelPart tongue4;
    private final ModelPart tongue_tip;
    private final ModelPart right_wing1;
    private final ModelPart right_wing2;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg3;

    public CalciferopteraModel() {
        this.root = this.bake(AncientTimes.ID);
        ModelPart mainRoot = this.root.getChild("root");
        this.Calci = mainRoot.getChild("Calci");
        this.body = this.Calci.getChild("body");
        this.left_wing1 = this.body.getChild("left_wing1");
        this.left_wing2 = this.body.getChild("left_wing2");
        this.tail = this.body.getChild("tail");
        this.tail_tip = this.tail.getChild("tail_tip");
        this.head = this.body.getChild("head");
        this.tongue = this.head.getChild("tongue");
        this.tongue2 = this.tongue.getChild("tongue2");
        this.tongue3 = this.tongue2.getChild("tongue3");
        this.tongue4 = this.tongue3.getChild("tongue4");
        this.tongue_tip = this.tongue4.getChild("tongue_tip");
        this.right_wing1 = this.body.getChild("right_wing1");
        this.right_wing2 = this.body.getChild("right_wing2");
        this.left_leg1 = this.Calci.getChild("left_leg1");
        this.left_leg2 = this.Calci.getChild("left_leg2");
        this.right_leg1 = this.Calci.getChild("right_leg1");
        this.right_leg2 = this.Calci.getChild("right_leg2");
        this.left_leg3 = this.Calci.getChild("left_leg3");
        this.right_leg3 = this.Calci.getChild("right_leg3");
    }

    public static @NotNull LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition Calci = root.addOrReplaceChild("Calci", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 4.0F));
        PartDefinition body = Calci.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 35).addBox(-3.5F, -7.0F, -8.0F, 7.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left_wing1 = body.addOrReplaceChild("left_wing1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -5.0F, 25.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -7.0F, -5.0F, 0.0F, -0.4363F, 0.0F));
        PartDefinition left_wing2 = body.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(48, 10).addBox(-1.0F, 0.0F, -2.0F, 15.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -6.0F, 1.0F, 0.0F, -0.5236F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(48, 17).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 2.0F));
        PartDefinition tail_tip = tail.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 21.0F, new CubeDeformation(0.0F)).texOffs(34, 35).addBox(-1.5F, -9.0F, 8.0F, 3.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 10.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(56, 55).addBox(-2.5F, -2.0F, -6.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 65).addBox(-0.5F, -5.0F, -7.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(66, 45).addBox(-1.5F, 0.0F, -10.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -8.0F));
        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 65).mirror().addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -2.0F, -3.0F, 0.0F, 0.0F, -0.829F));
        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(56, 65).addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -2.0F, -3.0F, 0.0F, 0.0F, 0.829F));
        PartDefinition tongue = head.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(30, 55).addBox(-0.5F, 0.0F, -12.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -10.0F));
        PartDefinition tongue2 = tongue.addOrReplaceChild("tongue2", CubeListBuilder.create().texOffs(30, 55).addBox(-0.5F, 0.0F, -12.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));
        PartDefinition tongue3 = tongue2.addOrReplaceChild("tongue3", CubeListBuilder.create().texOffs(30, 55).addBox(-0.5F, 0.0F, -12.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));
        PartDefinition tongue4 = tongue3.addOrReplaceChild("tongue4", CubeListBuilder.create().texOffs(30, 55).addBox(-0.5F, 0.0F, -12.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));
        PartDefinition tongue_tip = tongue4.addOrReplaceChild("tongue_tip", CubeListBuilder.create().texOffs(0, 53).addBox(-1.5F, 0.0F, -12.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));
        PartDefinition right_wing1 = body.addOrReplaceChild("right_wing1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-23.0F, 0.0F, -5.0F, 25.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -7.0F, -5.0F, 0.0F, 0.4363F, 0.0F));
        PartDefinition right_wing2 = body.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(48, 10).mirror().addBox(-14.0F, 0.0F, -2.0F, 15.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -6.0F, 1.0F, 0.0F, 0.5236F, 0.0F));
        PartDefinition left_leg1 = Calci.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(66, 33).addBox(0.0F, -1.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 1.0F, -6.0F, -0.7791F, 0.3854F, -1.2071F));
        PartDefinition left_leg2 = Calci.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(26, 67).addBox(0.0F, -1.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 1.0F, -2.0F, 0.228F, -0.1298F, -1.0621F));
        PartDefinition right_leg1 = Calci.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(66, 33).mirror().addBox(0.0F, -1.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 1.0F, -6.0F, -0.7791F, -0.3854F, 1.2071F));
        PartDefinition right_leg2 = Calci.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(26, 67).mirror().addBox(0.0F, -1.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 1.0F, -2.0F, 0.228F, 0.1298F, 1.0621F));
        PartDefinition left_leg3 = Calci.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(26, 74).addBox(0.0F, -1.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.0F, 2.0F, 0.8012F, -0.3931F, -1.2154F));
        PartDefinition right_leg3 = Calci.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(26, 74).mirror().addBox(0.0F, -1.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 1.0F, 2.0F, 0.8012F, 0.3931F, 1.2154F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(@NotNull Calciferoptera mob, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setupAnim(mob, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        this.tongue.visible = mob.getAnimator().isAnyPlaying(mob.attackAnim, mob.flyAttackAnim);
        ModelUtils.applyHeadRotations(this.head, headYaw, headPitch, 45.0F, 20.0F);
        float yawVelocity = Mth.degreesDifference(mob.yRotO, mob.getYRot());
        float targetRoll = Mth.clamp(yawVelocity * 0.15F, -0.4F, 0.4F);
        mob.bodyRoll = Mth.lerp(0.02F, mob.bodyRoll, -targetRoll);
        this.root.zRot = mob.bodyRoll;
        Vec3 velocity = mob.getDeltaMovement();
        float forwardSpeed = (float) (velocity.x * Math.sin(mob.getYRot() * Mth.DEG_TO_RAD) + velocity.z * -Math.cos(mob.getYRot() * Mth.DEG_TO_RAD));
        float targetPitch = (forwardSpeed * 2.5F) - (velocity.y > 0 ? (float)velocity.y * 3.0F : (float)velocity.y * 1.5F);
        targetPitch = -targetPitch;
        targetPitch = Mth.clamp(targetPitch, -0.6F, 0.6F);
        mob.bodyPitch = Mth.lerp(0.03F, mob.bodyPitch, targetPitch);
        this.root.xRot = mob.bodyPitch;
    }

    @Contract(pure = true)
    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    @Contract(pure = true)
    @Override
    protected void dynamicTail(@NotNull Calciferoptera mob) {

    }

    @Contract(pure = true)
    @Override
    protected void applyHeadRotation(@NotNull Calciferoptera mob, float headYaw, float headPitch, float ageInTicks) {

    }

    @Contract(pure = true)
    @Override
    public @NotNull ResourceLocation getTexture(@NotNull Calciferoptera mob) {
        return CALCIFEROPTERA_LOCATION;
    }
}