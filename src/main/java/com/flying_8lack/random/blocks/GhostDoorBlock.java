package com.flying_8lack.random.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.flying_8lack.random.main.flying8lacksrandommod.lg;

public class GhostDoorBlock extends Block {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public GhostDoorBlock() {
        super(Properties.of().noOcclusion());
        this.registerDefaultState(stateDefinition.any()
                .setValue(POWERED, false)
        );
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return state.getValue(POWERED) ? RenderShape.INVISIBLE : RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(state.getValue(POWERED)){
            return Shapes.empty();
        }

        return super.getCollisionShape(state, level, pos, context);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!(level.hasNeighborSignal(pos) || player.isShiftKeyDown())) {
            level.setBlock(pos, state.cycle(POWERED), 3);
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }



    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if(neighborBlock instanceof GhostDoorBlock && level.getBlockState(neighborPos) != null) {
            level.setBlock(pos, state.setValue(POWERED, level.getBlockState(neighborPos).getValue(POWERED)), 3);
        }
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }
}
