package com.flying_8lack.random.blocks;

import com.flying_8lack.random.blockentity.HElevatorBlockEntity;
import com.flying_8lack.random.main.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


public class HElevatorBlock extends Block implements EntityBlock {
    public HElevatorBlock(Properties properties) {
        super(properties);
    }



    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {

        if(entity.isShiftKeyDown() && level.getBlockEntity(pos) instanceof HElevatorBlockEntity be){
            be.teleport(entity, level);
        }

    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level.getBlockEntity(pos) instanceof HElevatorBlockEntity be){
            be.removeLink(level);
            be.resetTarget();
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new HElevatorBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntity.H_ELEVATOR_BE.get()? HElevatorBlockEntity::tick: null;
    }
}
