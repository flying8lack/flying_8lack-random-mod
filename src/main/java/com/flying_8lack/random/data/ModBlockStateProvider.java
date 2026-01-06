package com.flying_8lack.random.data;

import com.flying_8lack.random.main.ModBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.
        horizontalBlock(ModBlock.SILLY_MINER.get(),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_side"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_front"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_top"));
    }
}
