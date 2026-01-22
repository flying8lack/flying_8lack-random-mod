package com.flying_8lack.random.main;

import com.flying_8lack.random.entity.FigEntity;
import com.flying_8lack.random.entity.projectiles.FigBlobProjectile;
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
                    .sized(1, 0.9f)
                    .build("fig_entity"));

    public static final Supplier<EntityType<FigBlobProjectile>> FIG_BLOB_ENTITY = ET.register("fig_blob",
            () -> EntityType.Builder.of(FigBlobProjectile::new, MobCategory.CREATURE)
                    .build("fig_blob"));


}