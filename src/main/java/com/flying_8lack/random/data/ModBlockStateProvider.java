package com.flying_8lack.random.data;

import com.flying_8lack.random.blocks.GhostDoorBlock;
import com.flying_8lack.random.main.ModBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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

    private void ghostDoorBlock(Block block) {
        // Create the "closed" model (a standard cube)
        ModelFile closedModel = cubeAll(block);

        // Create an "open" model (invisible/empty)
        // Note: Using a built-in air-like model or a very small transparent cube
        ModelFile openModel = models().getBuilder(name(block) + "_open");

        getVariantBuilder(block).forAllStates(state -> {
            boolean powered = state.getValue(GhostDoorBlock.POWERED);
            return ConfiguredModel.builder()
                    .modelFile(powered ? openModel : closedModel)
                    .build();
        });
    }
    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
