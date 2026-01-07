package com.flying_8lack.random.main;

import com.flying_8lack.random.data.ModBlockModelProvider;
import com.flying_8lack.random.data.ModBlockStateProvider;
import com.flying_8lack.random.data.ModItemModelProvider;
import com.flying_8lack.random.data.ModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.ServerChatEvent;

import java.util.concurrent.CompletableFuture;

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

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public static void datagenEvent(GatherDataEvent event) {
        // Do something when the server starts
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(),
                new ModRecipeProvider(output,lookupProvider));

        generator.addProvider(event.includeClient(),
                new ModItemModelProvider(output,existingFileHelper));

        generator.addProvider(event.includeClient(),
                new ModBlockModelProvider(output,existingFileHelper));

        generator.addProvider(event.includeClient(),
                new ModBlockStateProvider(output,existingFileHelper));

    }
}
