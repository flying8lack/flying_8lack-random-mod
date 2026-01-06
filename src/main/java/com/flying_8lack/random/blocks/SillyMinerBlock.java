package com.flying_8lack.random.blocks;

import com.flying_8lack.random.blockentity.HElevatorBlockEntity;
import com.flying_8lack.random.blockentity.SillyMinerBlockEntity;
import com.flying_8lack.random.main.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.flying_8lack.random.main.flying8lacksrandommod.lg;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.EYE;

public class SillyMinerBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public SillyMinerBlock() {
        super(BlockBehaviour.Properties.of().jumpFactor(1.1f)
                .noOcclusion());



        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        // this is where the properties are actually added to the state
        pBuilder.add(FACING);
    }


    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if(level.getBlockEntity(pos) instanceof SillyMinerBlockEntity be){
            be.mine = level.hasNeighborSignal(pos);
            lg().debug("Signal: {}", be.mine);

        }

    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        Optional<SillyMinerBlockEntity> m = level.getBlockEntity(pos, ModBlockEntity.SILLY_MINER_BE.get());
        if(m.isEmpty()) return InteractionResult.SUCCESS;


        if(!level.isClientSide() && player instanceof ServerPlayer sp){
            sp.openMenu(new SimpleMenuProvider(m.get(), Component.literal("Silly Miner")), pos);
        }
        return InteractionResult.SUCCESS;
    }



    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level.getBlockEntity(pos) instanceof SillyMinerBlockEntity be){
            ItemEntity m = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), be.getInv()
                    .extractItem(0, 64, false));
            level.addFreshEntity(m);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SillyMinerBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide()? (level1, blockPos, blockState, t) -> {
            if(t instanceof SillyMinerBlockEntity be){
                be.tick(level1, blockState, blockPos, be);
            }
        } : null;
    }
}
