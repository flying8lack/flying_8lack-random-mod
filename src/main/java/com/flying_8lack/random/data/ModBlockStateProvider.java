package com.flying_8lack.random.data;

import com.flying_8lack.random.blocks.FigPlantBlock;
import com.flying_8lack.random.blocks.GhostDoorBlock;
import com.flying_8lack.random.main.ModBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        this.simpleBlock(ModBlock.GUM_ORE.get());
        this.simpleBlock(ModBlock.FIG_BLOCK.get());
        this.simpleBlock(ModBlock.WALL_DOOR.get());
        this.simpleBlock(ModBlock.POTION_MIXER_BLOCK.get());


        //this.simpleBlock();

        cropModel(ModBlock.FIG_PLANT_BLOCK.get(), "fig_plant_stage");


        this.
        horizontalBlock(ModBlock.SILLY_MINER.get(),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_side"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_top"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_front"));
    }

    private void cropModel(Block block, String textureName){
        VariantBlockStateBuilder build = getVariantBuilder(block);
        for(int i = 0; i <= 3; i++){
            build.partialState().with(FigPlantBlock.AGE, i)
                    .modelForState()
                    .modelFile(models().crop("fig_plant_stage"+i,
                                    modLoc("block/crop/" + textureName + i))
                            .renderType("cutout"))
                    .addModel();
        }
    }
    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
