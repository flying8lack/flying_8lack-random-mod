package com.flying_8lack.random.main;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = flying8lacksrandommod.MODID)
public class ModBus {
    @SubscribeEvent
    public static void onCap(RegisterCapabilitiesEvent event) {
        // Do something when the server starts
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntity.H_ELEVATOR_BE.get(),
                (be, d) -> be.getUpgrade()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntity.SILLY_MINER_BE.get(),
                (be, d) -> be.getInv()
        );

    }
}
