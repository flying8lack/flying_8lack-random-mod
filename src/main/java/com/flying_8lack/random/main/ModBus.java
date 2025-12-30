package com.flying_8lack.random.main;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


@EventBusSubscriber(modid = flying8lacksrandommod.MODID)
public class ModBus {

    @SubscribeEvent
    public static void mobCreator(EntityAttributeCreationEvent e){
        e.put(ModEntity.FIG_ENTITY.get(),
                LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 8.0f)
                        .add(Attributes.FOLLOW_RANGE)
                        .add(Attributes.MOVEMENT_SPEED, 0.15)
                        .add(Attributes.ATTACK_DAMAGE, 5)
                        .build());
    }

}
