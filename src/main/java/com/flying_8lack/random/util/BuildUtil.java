package com.flying_8lack.random.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BuildUtil {

    public static void addBlock(Block block, BlockPos pos, Level level){
        if(!level.isClientSide()) {
            level.setBlock(pos, block.defaultBlockState(), 3);
        }
    }
}
