package com.flying_8lack.random.blockentity;

import com.flying_8lack.random.items.AbstractUpgradeItem;
import com.flying_8lack.random.main.ModBlockEntity;
import com.flying_8lack.random.main.ModItem;
import com.flying_8lack.random.menu.HElevatorMenu;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;



public class HElevatorBlockEntity extends BlockEntity implements MenuProvider {

    private BlockPos target = null;
    private int cooldown = 0;
    private boolean teleporting = false;
    private final ItemStackHandler upgrade = new ItemStackHandler(){
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof AbstractUpgradeItem;
        }
    };

    public void setTeleporting(boolean b){
        this.teleporting = b;
    }

    public HElevatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.H_ELEVATOR_BE.get(), pos, blockState);

    }

    public ItemStackHandler getUpgrade(){
        return upgrade;
    }

    public void removeLink(Level level){
        if(this.target == null) return;
        if(level.getBlockEntity(this.target) instanceof HElevatorBlockEntity be){
            be.resetTarget();
        }
    }

    public void coolDown(){
        this.cooldown = 20*5;
    }

    public void resetTarget(){
        this.target = null;
    }

    public void teleport(Entity entity, Level level){
        if(this.cooldown > 0 || this.teleporting) return;

        if(target == null){
            this.search(this.getLevel(), entity);
            return;
        }
        ItemStack upg = this.upgrade.getStackInSlot(0);


        if(level.getBlockEntity(target) instanceof HElevatorBlockEntity be) {
            entity.setShiftKeyDown(false);
            be.coolDown();
            this.coolDown();
            be.setTeleporting(true);
            entity.moveTo(target.above().getBottomCenter());
            if(!upg.isEmpty() && upg.getItem() instanceof AbstractUpgradeItem u){
                u.postOperation(entity, level);

            }
            be.setTeleporting(false);


        } else {
            //no longer exists
            this.resetTarget();
        }

    }

    public void search(Level level, Entity entity){
        BlockPos init = this.getBlockPos();
        this.target = null;
        Direction a = entity.getDirection();
        Vec3i directionView = new Vec3i(a.getStepX(), a.getStepY(), a.getStepZ());
        for(int i = 1; i < 32; i++){
            if (level.getBlockEntity(init.offset(directionView.multiply(i))) instanceof HElevatorBlockEntity b){
                if (b.target == null) {
                    this.target = init.offset(directionView.multiply(i));
                    b.target = this.getBlockPos();
                    break;
                }
            }
        }

        if(this.target == null && entity instanceof Player p){
            p.displayClientMessage(Component.literal("No target elevator was found in that direction"),true);
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
        tag.put("upgrades", this.upgrade.serializeNBT(registries));

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        upgrade.deserializeNBT(registries, tag.getCompound("upgrades"));

        if(!(tag.contains("target_x") && tag.contains("target_z"))){
            return;
        }

        target = new BlockPos(tag.getInt("target_x"),
                tag.getInt("target_y"),
                tag.getInt("target_z"));



    }



    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if(t instanceof HElevatorBlockEntity be){
            if(be.cooldown > 0 && !be.teleporting){
                be.cooldown -= 1;
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("J");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new HElevatorMenu(i, inventory, this, this.upgrade);
    }
}
