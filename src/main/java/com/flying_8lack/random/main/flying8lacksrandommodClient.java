package com.flying_8lack.random.main;

import com.flying_8lack.random.client.screen.HElevatorScreen;
import com.flying_8lack.random.client.screen.SillyMinerScreen;
import com.flying_8lack.random.client.entity.model.FigEntityModel;
import com.flying_8lack.random.client.entity.render.FigRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = flying8lacksrandommod.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = flying8lacksrandommod.MODID, value = Dist.CLIENT)
public class flying8lacksrandommodClient {
    public flying8lacksrandommodClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void regsiterMenuScreen(FMLClientSetupEvent event) {


    }

    @SubscribeEvent
    static void regsiterMenuScreen(RegisterMenuScreensEvent event) {
        event.register(ModMenu.H_ELEVATOR_MENU.get(),
                HElevatorScreen::new);

        event.register(ModMenu.SILLY_MINER_MENU.get(),
                SillyMinerScreen::new);
    }

    @SubscribeEvent
    static  void registerLayerDef(EntityRenderersEvent.RegisterLayerDefinitions e){
        e.registerLayerDefinition(FigEntityModel.LAYER_LOCATION, FigEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    static  void registerRenderer(EntityRenderersEvent.RegisterRenderers e){
        e.registerEntityRenderer(ModEntity.FIG_ENTITY.get(), FigRenderer::new);
    }
}
