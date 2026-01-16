package com.flying_8lack.random.main;

import com.flying_8lack.random.blocks.GhostDoorBlock;
import com.flying_8lack.random.blocks.HElevatorBlock;
import com.flying_8lack.random.blocks.SillyMinerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying_8lack.random.main.ModItem.ITEMS;
import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBlock {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> H_ELEVATOR = registerBlock("h_elevator",
            () -> new HElevatorBlock(BlockBehaviour.Properties.of().sound(SoundType.COPPER)));

    public static final DeferredBlock<Block> SILLY_MINER = registerBlock("silly_miner",
            SillyMinerBlock::new);

//    public static final DeferredBlock<Block> TEETH_ORE = registerBlock("fig_block",
//            () -> new Block(BlockBehaviour.Properties.of()
//                    .instrument(NoteBlockInstrument.GUITAR)
//                    .mapColor(MapColor.STONE)
//                    .sound(SoundType.MUD)
//                    .strength(1.8f)));


    public static final DeferredBlock<Block> FIG_BLOCK = registerBlock("fig_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .jumpFactor(1.8f)
                    .sound(SoundType.MUD)
                    .strength(1.8f)));

    public static final DeferredBlock<Block> WALL_DOOR = registerBlock("wall_door",
            GhostDoorBlock::new);

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<B> block){
        DeferredBlock<B> toReturn = BLOCKS.register(name, block);
        ITEMS.registerSimpleBlockItem(name, toReturn);

        return toReturn;
    }

}
