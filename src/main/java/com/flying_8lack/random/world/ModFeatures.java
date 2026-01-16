package com.flying_8lack.random.world;

import com.flying_8lack.random.main.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> GUM_ORE_CONFIG = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "gum_ore")
    );

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> c){
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        // 2. Define the target (Replace Stone with MyBlock)
        List<OreConfiguration.TargetBlockState> targetList = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlock.GUM_ORE.get().defaultBlockState())
        );

        // 3. Register the feature: (Key, new ConfiguredFeature(FeatureType, Configuration))
        c.register(GUM_ORE_CONFIG, new ConfiguredFeature<>(
                Feature.ORE,
                new OreConfiguration(targetList, 6) // 9 is the size of the vein
        ));



    }
}
