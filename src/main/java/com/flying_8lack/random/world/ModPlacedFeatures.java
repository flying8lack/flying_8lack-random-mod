package com.flying_8lack.random.world;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> GUM_ORE_PLACED = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "gum_ore_placed")
    );


    public static void bootstrap(BootstrapContext<PlacedFeature> c){

        var configLookup = c.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?,?>> gum_ore_config = configLookup.getOrThrow(ModFeatures.GUM_ORE_CONFIG);
        c.register(GUM_ORE_PLACED, new PlacedFeature(
                gum_ore_config,
                List.of(
                        CountPlacement.of(7),
                        InSquarePlacement.spread(),

                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(60),
                                VerticalAnchor.absolute(-60))


                )
        ));
    }
}
