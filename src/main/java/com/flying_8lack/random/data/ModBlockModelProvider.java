package com.flying_8lack.random.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(PackOutput output,  ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        this.orientable("silly_miner",
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_side"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_front"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_top"));
    }
}
