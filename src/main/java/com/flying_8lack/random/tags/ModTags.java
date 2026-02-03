package com.flying_8lack.random.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModTags {
    public static class Biomes {
        // This defines the tag: "modid:biome_tags/fig_spawn_biomes"
        public static final TagKey<Biome> FIG_SPAWN_BIOMES = tag("fig_spawn_biomes");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MODID, name));
        }
    }
}
