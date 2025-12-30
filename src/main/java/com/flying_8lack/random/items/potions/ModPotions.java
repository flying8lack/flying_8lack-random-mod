package com.flying_8lack.random.items.potions;

import com.flying_8lack.random.main.ModEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModPotions {

    public static final DeferredRegister<Potion> POTION =
            DeferredRegister.create(BuiltInRegistries.POTION, MODID);

    public static final Holder<Potion> FIGIFICATION = POTION.register("figification",
            () -> new Potion(new MobEffectInstance(ModEffect.FIGIFICATION, 600)));
}
