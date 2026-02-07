package com.flying_8lack.random.blocks;

import com.flying_8lack.random.blockentity.PotionMixerBlockEntity;
import com.flying_8lack.random.blockentity.SillyMinerBlockEntity;
import com.flying_8lack.random.main.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PotionMixerBlock extends Block implements EntityBlock {
    public PotionMixerBlock(Properties properties) {
        super(properties);
    }



    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        Optional<PotionMixerBlockEntity> m = level.getBlockEntity(pos, ModBlockEntity.POTION_MIXER_BE.get());
        if(m.isEmpty()) return InteractionResult.SUCCESS;


        if(!level.isClientSide() && player instanceof ServerPlayer sp){
            sp.openMenu(new SimpleMenuProvider(m.get(), Component.literal("Silly Miner")), pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : (level1, blockPos, blockState, t) -> {
            if(t instanceof PotionMixerBlockEntity be){
                be.tick(level1, blockPos, blockState, be);
            }
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PotionMixerBlockEntity(blockPos, blockState);
    }
}
