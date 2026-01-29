package com.flying_8lack.random.data;

import com.flying_8lack.random.main.ModBlock;
import com.flying_8lack.random.main.ModItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        this.simpleBlockItem(ModBlock.WALL_DOOR.get());
        this.simpleBlockItem(ModBlock.FIG_BLOCK.get());
        withExistingParent(ModBlock.GUM_ORE.getId().getPath(),
                modLoc("block/" + ModBlock.GUM_ORE.getId().getPath()));

        this.basicItem(ModItem.SILLY_GLASS_SHARD.get());


        withExistingParent(ModItem.FIG_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        this.basicItem(ModItem.HARD_GUM.get());
        this.basicItem(ModItem.SOFT_GUM.get());



        this.orientable("silly_miner",
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_side"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_top"),
                ResourceLocation.fromNamespaceAndPath(MODID,"block/silly_miner_front"));

    }
}
