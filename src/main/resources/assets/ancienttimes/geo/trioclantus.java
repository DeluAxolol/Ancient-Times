// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class trioclantus<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "trioclantus"), "main");
	private final ModelPart Trioclantus;
	private final ModelPart Body;
	private final ModelPart Body_size;
	private final ModelPart Tail;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart Arm;
	private final ModelPart Arm2;
	private final ModelPart Leg;
	private final ModelPart Leg2;

	public trioclantus(ModelPart root) {
		this.Trioclantus = root.getChild("Trioclantus");
		this.Body = root.getChild("Body");
		this.Body_size = root.getChild("Body_size");
		this.Tail = root.getChild("Tail");
		this.neck = root.getChild("neck");
		this.head = root.getChild("head");
		this.jaw = root.getChild("jaw");
		this.Arm = root.getChild("Arm");
		this.Arm2 = root.getChild("Arm2");
		this.Leg = root.getChild("Leg");
		this.Leg2 = root.getChild("Leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Trioclantus = partdefinition.addOrReplaceChild("Trioclantus", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -4.0F));

		PartDefinition Body = Trioclantus.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body_size = Body.addOrReplaceChild("Body_size", CubeListBuilder.create().texOffs(0, 186).addBox(-14.0F, -12.0F, -21.0F, 28.0F, 27.0F, 43.0F, new CubeDeformation(0.0F))
		.texOffs(66, 104).addBox(-14.0F, 15.0F, -21.0F, 28.0F, 4.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(182, 219).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 20.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition neck = Body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(153, 166).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 158).mirror().addBox(-5.0F, -20.875F, -10.0F, 10.0F, 16.0F, 9.0F, new CubeDeformation(-0.04F)).mirror(false), PartPose.offset(0.0F, 8.0F, -21.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(198, 34).addBox(-5.0F, -2.0F, -19.0F, 10.0F, 5.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(9, 195).addBox(-5.0F, 3.0F, -19.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 205).addBox(-8.0F, -13.0F, -4.9F, 16.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, -2.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(206, 107).addBox(-5.0F, -1.0F, -15.0F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(234, 71).addBox(-5.0F, -1.0F, -16.0F, 10.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(193, 75).addBox(4.95F, -6.0F, -11.0F, 0.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(193, 75).mirror().addBox(-4.95F, -6.0F, -11.0F, 0.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(151, 46).addBox(-5.0F, -6.0F, -9.0F, 10.0F, 5.0F, 9.0F, new CubeDeformation(-0.03F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition Arm = Body.addOrReplaceChild("Arm", CubeListBuilder.create().texOffs(218, 174).addBox(-3.0F, -3.0F, 0.0F, 5.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, 14.0F, -18.0F));

		PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(106, 160).addBox(-3.0F, -3.0F, 0.0F, 5.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 14.0F, -18.0F));

		PartDefinition Leg = Trioclantus.addOrReplaceChild("Leg", CubeListBuilder.create().texOffs(100, 195).addBox(-5.0F, 0.0F, -10.0F, 12.0F, 14.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(42, 164).addBox(-5.0F, 14.0F, -2.0F, 12.0F, 10.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(88, 168).addBox(-2.0F, 21.0F, -4.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 6.0F, 11.0F));

		PartDefinition cube_r1 = Leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(88, 168).addBox(-3.0F, -1.5F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 22.5F, 3.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = Leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(88, 168).addBox(-4.0F, -2.0F, 0.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 23.0F, 4.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Leg2 = Trioclantus.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(100, 195).mirror().addBox(-7.0F, 0.0F, -10.0F, 12.0F, 14.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(42, 164).mirror().addBox(-7.0F, 14.0F, -2.0F, 12.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(88, 168).addBox(-4.0F, 21.0F, -4.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 6.0F, 11.0F));

		PartDefinition cube_r3 = Leg2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(88, 168).addBox(-3.0F, -1.5F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 22.5F, 3.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = Leg2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(88, 168).addBox(-4.0F, -2.0F, 0.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 23.0F, 4.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Trioclantus.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}