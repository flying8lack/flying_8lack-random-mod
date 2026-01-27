package com.flying_8lack.random.blockentity;

import com.flying_8lack.random.main.ModBlockEntity;
import com.flying_8lack.random.menu.HElevatorMenu;
import com.flying_8lack.random.menu.SillyMinerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.flying_8lack.random.main.flying8lacksrandommod.lg;

public class SillyMinerBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler pickaxe = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.is(Tags.Items.MINING_TOOL_TOOLS);
        }
    };

    private BlockCapabilityCache<IItemHandler, @Nullable Direction> outputStorage;

    private BlockPos position;

    private int coolDown = 8;
    private int currentZ = 0;
    private int currentX = 0;
    private int currentY = 0;
    private DataSlot data = new DataSlot() {
        @Override
        public int get() {
            return currentY;
        }

        @Override
        public void set(int i) {
            currentY = i;
        }
    };
    public boolean mine = false;

    public SillyMinerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.SILLY_MINER_BE.get(), pos, blockState);
        this.position = pos;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if(this.level instanceof ServerLevel server) {
            this.outputStorage = BlockCapabilityCache.create(
                    Capabilities.ItemHandler.BLOCK,
                    server,
                    this.position.above(),
                    Direction.UP
            );
        }

    }

    public List<ItemStack> mine(Level level, BlockPos pos, ItemStack tool, BlockState state){
        LootParams.Builder b = new LootParams.Builder((ServerLevel) level)
                .withParameter(LootContextParams.ORIGIN, pos.getBottomCenter())
                .withParameter(LootContextParams.TOOL, tool);

        LootTable lt = level.getServer().reloadableRegistries().getLootTable(state.getBlock().getLootTable());

        List<ItemStack> loot = lt.getRandomItems(b.withParameter(LootContextParams.BLOCK_STATE, state)
                .create(LootContextParamSets.BLOCK));

        return loot;
    }

    private void advance(){
        this.currentX += 1;
        if(this.currentX > 8){
            this.currentX = 0;
            this.currentZ += 1;
        }

        if(this.currentZ > 8){
            this.currentZ = 0;
            this.currentY += 1;
        }
    }

    public void tick(Level level, BlockState state, BlockPos pos, SillyMinerBlockEntity be){
        if(coolDown > 0){
            coolDown -= 1;
            return;
        }
        ItemStack p = be.pickaxe.getStackInSlot(0);
        if(!p.isEmpty()){
            BlockPos target = pos
                    .offset(this.currentX-4,
                            -this.currentY,
                            this.currentZ-4);

            if (target == pos) {
                coolDown = 2;
                this.advance();
                return;

            }

            BlockState target_blockstate = level.getBlockState(target);


            List<ItemStack> drops = this.mine(level, target, p, target_blockstate);

            p.hurtAndBreak(2, (ServerLevel) level, null, (i) -> {});

            if(this.outputStorage.getCapability() != null) {

                for(ItemStack item : drops) {

                    ItemStack left_over = ItemHandlerHelper.insertItemStacked(this.outputStorage.getCapability(),item,false);
                    if(!left_over.isEmpty()){
                        level.addFreshEntity(new ItemEntity(level, pos.getX()+0.5
                                , pos.getY()+1.8, pos.getZ()+0.5, left_over));
                    }
                }
            } else {
                level.invalidateCapabilities(pos.above());
                drops.forEach(
                        c -> level.addFreshEntity(new ItemEntity(level, pos.getX()+0.5
                                , pos.getY()+1.8, pos.getZ()+0.5, c)));
            }



            level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);

            ((ServerLevel) level).sendParticles(ParticleTypes.OMINOUS_SPAWNING,
                    target.getX(), target.getY(), target.getZ(), 16, 0, 1.1, 0,
                    0.4);

            level.playSound(null, target, SoundEvent.createVariableRangeEvent(
                    SoundEvents.COW_STEP.getLocation()
            ), SoundSource.BLOCKS, 0.75f, 1.0f);

            this.advance();

            be.setChanged();


        }

        coolDown = 8;
    }

    public ItemStackHandler getInv(){
        return this.pickaxe;
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("pickaxe", this.pickaxe.serializeNBT(registries));
        tag.putInt("X", this.currentX);
        tag.putInt("Y", this.currentY);
        tag.putInt("Z", this.currentZ);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(tag.contains("pickaxe")) {
            this.pickaxe.deserializeNBT(registries, tag.getCompound("pickaxe"));
        }
        if(tag.contains("X")) this.currentX = tag.getInt("X");
        if(tag.contains("Y")) this.currentY = tag.getInt("Y");
        if(tag.contains("Z")) this.currentZ = tag.getInt("Z");
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Silly Miner");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SillyMinerMenu(i, inventory, this, data);
    }
}
