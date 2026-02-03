package com.flying_8lack.random.world;

import com.flying_8lack.random.main.ModEntity;
import com.flying_8lack.random.tags.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBiomeModifier {
    public static final ResourceKey<BiomeModifier> GUM_ORE_BM = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(MODID, "gum_ore_bm")
    );

    public static final ResourceKey<BiomeModifier> SPAWN_FIG_BM = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(MODID, "spawn_fig_bm")
    );

    public static void bootstrap(BootstrapContext<BiomeModifier> c){
        var placedfeature = c.lookup(Registries.PLACED_FEATURE);
        var biome = c.lookup(Registries.BIOME);

        c.register(SPAWN_FIG_BM, new BiomeModifiers.AddSpawnsBiomeModifier(
                biome.getOrThrow(ModTags.Biomes.FIG_SPAWN_BIOMES),
                List.of(
                        new MobSpawnSettings.SpawnerData(ModEntity.FIG_ENTITY.get(),
                                64, 3, 7)
                )

        ));

        c.register(GUM_ORE_BM, new BiomeModifiers.AddFeaturesBiomeModifier(
                biome.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                HolderSet.direct(placedfeature.getOrThrow(ModPlacedFeatures.GUM_ORE_PLACED)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }
}
