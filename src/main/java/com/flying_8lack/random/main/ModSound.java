package com.flying_8lack.random.main;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModSound {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(
            BuiltInRegistries.SOUND_EVENT, MODID
    );

    public static final Holder<SoundEvent>  WET_SOUND = SOUND_EVENT.register(
            "wet_sound", SoundEvent::createVariableRangeEvent
    );


    public static final Holder<SoundEvent>  FIG_WALKING_SOUND = SOUND_EVENT.register(
            "fig_walking", SoundEvent::createVariableRangeEvent
    );

}
