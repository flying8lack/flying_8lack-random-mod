package com.flying_8lack.random.blockentity;

import com.flying_8lack.random.main.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HElevatorBlockEntity extends BlockEntity {

    private BlockPos target = null;
    private int cooldown = 0;


    public HElevatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.H_ELEVATOR_BE.get(), pos, blockState);

    }

    public void coolDown(){
        this.cooldown = 20*6;
    }

    public void resetTarget(){
        this.target = null;
    }

    public void teleport(Entity entity, Level level){
        if(cooldown > 0) return;

        if(target == null){
            this.search(this.getLevel(), entity);
            return;
        }
        if(level.getBlockEntity(target) instanceof HElevatorBlockEntity be) {
            entity.setShiftKeyDown(false);
            this.coolDown();
            be.coolDown();
            entity.moveTo(target.above().getBottomCenter());
        } else {
            //no longer exists
            this.resetTarget();
        }

    }

    public void search(Level level, Entity entity){
        BlockPos init = this.getBlockPos();
        this.target = null;
        Direction.Axis a = entity.getDirection().getAxis();
        for(int i = -31; i < 32; i++){
            if(i == 0) continue;
            if (level.getBlockEntity(init.relative(a, i)) instanceof HElevatorBlockEntity b){
                if (b.target == null) {
                    this.target = init.relative(a, i);
                    b.target = this.getBlockPos();
                    break;
                }
            }
        }


    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if(target != null) {
            tag.putInt("target_x", target.getX());
            tag.putInt("target_y", target.getY());
            tag.putInt("target_z", target.getZ());
        }

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(!(tag.contains("target_x") && tag.contains("target_z"))){
            return;
        }

        target = new BlockPos(tag.getInt("target_x"),
                tag.getInt("target_y"),
                tag.getInt("target_z"));

    }



    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if(t instanceof HElevatorBlockEntity be){
            if(be.cooldown > 0){
                be.cooldown -= 1;
            }
        }
    }
}
