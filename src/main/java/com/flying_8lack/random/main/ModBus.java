package com.flying_8lack.random.main;

import com.flying_8lack.random.data.*;
import com.flying_8lack.random.entity.goals.FireProjectileGoal;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;
import static com.flying_8lack.random.world.World.BUILDER;

@EventBusSubscriber(modid = MODID)
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

    @SubscribeEvent
    public static void mobCreator (EntityAttributeCreationEvent e){
        e.put(ModEntity.FIG_ENTITY.get(),
                LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 8.0f)
                        .add(Attributes.FOLLOW_RANGE)
                        .add(Attributes.MOVEMENT_SPEED, 0.15)
                        .add(Attributes.ATTACK_DAMAGE, 5)
                        .build());


    }

    @SubscribeEvent
    public static void modifyAttributes(EntityAttributeModificationEvent event) {
        // Check if the Giant has the attribute first, then add/modify it
        // Giants by default have health/attack but you can boost them
        event.add(EntityType.GIANT, Attributes.MAX_HEALTH, 200.0);
        event.add(EntityType.GIANT, Attributes.ATTACK_DAMAGE, 8.0);
        event.add(EntityType.GIANT, Attributes.MOVEMENT_SPEED, 0.2);

    }


    @SubscribeEvent
    public static void modMobs (EntityJoinLevelEvent e){
        if(e.getEntity() instanceof Giant g){
            g.goalSelector.addGoal(0, new MeleeAttackGoal(g, 1.1f, true));
            g.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(g, Player.class, false));


        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public static void datagenEvent (GatherDataEvent event){
        // Do something when the server starts
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(),
                new ModRecipeProvider(output, lookupProvider));


        generator.addProvider(event.includeClient(),
                new ModBlockModelProvider(output, existingFileHelper));

        generator.addProvider(event.includeClient(),
                new ModBlockStateProvider(output, existingFileHelper));

        generator.addProvider(event.includeServer(),
                new MyLootProvider(output,
                        Set.of(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,
                                        LootContextParamSets.BLOCK),
                                new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new,
                                        LootContextParamSets.ENTITY)
                        ),
                        lookupProvider)
        );



        generator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(output,lookupProvider, BUILDER, Set.of(MODID)));

        generator.addProvider(event.includeClient(),
                new ModItemModelProvider(output, existingFileHelper));


    }
}
