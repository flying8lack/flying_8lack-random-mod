package com.flying_8lack.random.main;

import com.flying_8lack.random.blocks.HElevatorBlock;
import com.flying_8lack.random.blocks.SillyMinerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying_8lack.random.main.ModItem.ITEMS;
import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBlock {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> H_ELEVATOR = BLOCKS.register("h_elevator",
            () -> new HElevatorBlock(BlockBehaviour.Properties.of().sound(SoundType.COPPER)));

    public static final DeferredBlock<Block> SILLY_MINER = BLOCKS.register("silly_miner",
            SillyMinerBlock::new);

    //block items
    public static final DeferredItem<BlockItem> H_ELEVATOR_ITEM = ITEMS.registerSimpleBlockItem("h_elevator", H_ELEVATOR);
    public static final DeferredItem<BlockItem> SILLY_MINER_ITEM = ITEMS.registerSimpleBlockItem("silly_miner", SILLY_MINER);
}
