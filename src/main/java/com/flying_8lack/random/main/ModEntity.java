package com.flying_8lack.random.main;

import com.flying_8lack.random.entity.FigEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModEntity {

    public static final DeferredRegister<EntityType<?>> ET = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE,
            MODID);


    public static final Supplier<EntityType<FigEntity>> FIG_ENTITY = ET.register("fig_entity",
            () -> EntityType.Builder.of(FigEntity::new, MobCategory.CREATURE)
                    .build("fig_entity"));


}