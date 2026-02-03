package com.flying_8lack.random.data;

import com.flying_8lack.random.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBiomeTagModifer extends BiomeTagsProvider {
    public ModBiomeTagModifer(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p) {
       tag(ModTags.Biomes.FIG_SPAWN_BIOMES)
               .addTag(Tags.Biomes.IS_PLAINS)
               .addTag(Tags.Biomes.IS_FLORAL)
               .addTag(Tags.Biomes.IS_FOREST);
    }
}
