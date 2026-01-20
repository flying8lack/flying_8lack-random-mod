package com.flying_8lack.random.client.entity.model;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.flying_8lack.random.entity.FigEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class FigEntityModel<T extends FigEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID, "figentitymodel"), "main");
	private final ModelPart body;
	private final ModelPart legs;

	public FigEntityModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legs = root.getChild("legs");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -3.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-7.0F, -8.0F, -2.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 21.0F, 0.0F));

		PartDefinition tip_r1 = body.addOrReplaceChild("tip_r1", CubeListBuilder.create().texOffs(14, 24).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -8.0F, 0.0F, -0.5274F, -0.0565F, -0.2699F));

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = legs.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 13).addBox(-5.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -2.0F, 4.0F, -0.3695F, 0.659F, -0.564F));

		PartDefinition cube_r2 = legs.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -2.0F, -3.0F, 0.2877F, -0.5467F, -0.5175F));

		PartDefinition cube_r3 = legs.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 4.0F, -0.3136F, -0.5844F, 0.5314F));

		PartDefinition cube_r4 = legs.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 20).addBox(-1.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, -2.0F, 0.2042F, 0.3378F, 0.5585F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(FigEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
		body.render(poseStack, vertexConsumer, i, i1, i2);
		legs.render(poseStack, vertexConsumer, i, i1, i2);
	}
}