package com.flying_8lack.random.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class BuildUtil {

    public static void addBlock(Block block, BlockPos pos, @NotNull ServerLevel level){
        level.setBlock(pos, block.defaultBlockState(), 3);
    }
}
