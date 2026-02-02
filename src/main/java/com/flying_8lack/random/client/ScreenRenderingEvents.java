package com.flying_8lack.random.client;

import com.flying_8lack.random.main.ModEffect;
import com.flying_8lack.random.main.flying8lacksrandommod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = flying8lacksrandommod.MODID, value = Dist.CLIENT)
public class ScreenRenderingEvents {
    @SubscribeEvent
    public static void renderOverlay(RenderGuiLayerEvent.Post event){
        Minecraft mc = Minecraft.getInstance();

        if(mc.player == null || !mc.player.hasEffect(ModEffect.FIGIFICATION)) return;



        float ticks = mc.level.getGameTime() + event.getPartialTick().getGameTimeDeltaTicks();

        // 2. Calculate pulse (speed = 0.1f, change to make it faster/slower)
        // We use Mth.sin from Minecraft's math utils
        float pulse = (Mth.sin(ticks * 0.2f) + 1.0f) / 2.0f; // Range: 0.0 to 1.0

        // 3. Define min and max opacity (0.1 to 0.4)
        float minAlpha = 0.0f;
        float maxAlpha = 0.015f;
        float finalAlpha = minAlpha + (pulse * (maxAlpha - minAlpha));

        // 4. Convert float alpha to hex (0-255)
        int alphaInt = (int)(finalAlpha * 255.0f);
        int color = (alphaInt << 24) | 0xFF00FF; // ARGB: Alpha, Red (FF), Green (00), Blue (FF)


        // 5. Render
        event.getGuiGraphics().fill(0, 0, event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight(), color);


    }
}
