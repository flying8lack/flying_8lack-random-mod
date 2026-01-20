package com.flying_8lack.random.client.entity.render;

import com.flying_8lack.random.client.entity.layer.FigLayer;
import com.flying_8lack.random.client.entity.model.FigEntityModel;
import com.flying_8lack.random.entity.FigEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static com.flying_8lack.random.client.entity.model.FigEntityModel.LAYER_LOCATION;
import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class FigRenderer extends LivingEntityRenderer<FigEntity, FigEntityModel<FigEntity>> {

    public FigRenderer(EntityRendererProvider.Context context) {

        super(context, new FigEntityModel<>(context.bakeLayer(LAYER_LOCATION)), 0.5f);
        this.addLayer(new FigLayer(this));

    }

    @Override
    protected @Nullable RenderType getRenderType(FigEntity livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutout(this.getTextureLocation(livingEntity));
    }

    @Override
    public ResourceLocation getTextureLocation(FigEntity figEntity) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/fig_entity.png");
    }
}
