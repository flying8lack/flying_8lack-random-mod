package com.flying_8lack.random.client.entity.layer;

import com.flying_8lack.random.client.entity.model.FigEntityModel;
import com.flying_8lack.random.entity.FigEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class FigLayer extends RenderLayer<FigEntity, FigEntityModel<FigEntity>> {
    public FigLayer(RenderLayerParent<FigEntity, FigEntityModel<FigEntity>> renderer) {
        super(renderer);

    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, FigEntity figEntity, float v, float v1, float v2, float v3, float v4, float v5) {

    }
}
