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

import static com.flying_8lack.random.main.flying8lacksrandommod.lg;


public class HElevatorBlock extends Block implements EntityBlock {
    public HElevatorBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof HElevatorBlockEntity be){
            be.resetTarget();
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {

        if(entity.isShiftKeyDown() && level.getBlockEntity(pos) instanceof HElevatorBlockEntity be){
            be.teleport(entity, level);
        }

    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new HElevatorBlockEntity(blockPos, blockState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntity.H_ELEVATOR_BE.get()? HElevatorBlockEntity::tick: null;
    }
}
