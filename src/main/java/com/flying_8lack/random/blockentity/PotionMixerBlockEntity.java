package com.flying_8lack.random.blockentity;

import com.flying_8lack.random.menu.PotionMixerMenu;
import com.flying_8lack.random.menu.SillyMinerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.flying_8lack.random.main.ModBlockEntity.POTION_MIXER_BE;

public class PotionMixerBlockEntity extends BlockEntity implements MenuProvider {

    public ItemStackHandler inventory = new ItemStackHandler(3){
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if(!stack.is(Tags.Items.POTIONS)) return stack;
            return super.insertItem(slot, stack, simulate);
        }

    };
    public int coolDown = 120;
    public PotionMixerBlockEntity(BlockPos pos, BlockState blockState) {
        super(POTION_MIXER_BE.get(), pos, blockState);
    }



    private void mix(){
        ItemStack p = inventory.extractItem(0, 1, false);
        ItemStack s = inventory.extractItem(1, 1, false);
        PotionContents pd = p.get(DataComponents.POTION_CONTENTS);
        PotionContents sd = s.get(DataComponents.POTION_CONTENTS);
        if(p.isEmpty() || s.isEmpty()) {
            inventory.insertItem(0, p, false);
            inventory.insertItem(1, s, false);
            return;
        }
        if(pd == null || sd == null) return;

        ArrayList<MobEffectInstance> k = new ArrayList<>();

        pd.forEachEffect(
                k::add
        );
        sd.forEachEffect(
                k::add
        );;


        int color = averageColor(
                p.get(DataComponents.POTION_CONTENTS).getColor(), s.get(DataComponents.POTION_CONTENTS).getColor());

        PotionContents effect = new PotionContents(
                Optional.empty(),
                Optional.of(
                        color
                ),
                k
        );
        MutableComponent name = Component.literal("Potion of ");
        for(MobEffectInstance c : k) {
            Component tn = c.getEffect().value().getDisplayName();

            name.append(Component.literal(tn.getString()
                    .replace(" ", "")
                    .substring(0,4)));




        }



        ItemStack potionStack = new ItemStack(Items.POTION);
        potionStack.set(DataComponents.POTION_CONTENTS, effect);
        potionStack.set(DataComponents.CUSTOM_NAME, name.withStyle(style -> style.withItalic(false)));
        inventory.insertItem(2, potionStack, false);
    }

    private int averageColor(int a, int b){
        int red = ((a >> 16 & 0xFF) + (b >> 16 & 0xFF)) >> 1;
        int green = ((a >> 8 & 0xFF) + (b >> 8 & 0xFF)) >> 1;
        int blue = ((a & 0xFF) + (b & 0xFF)) >> 1;

        return (red << 16) | (green << 8) | blue;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(tag.contains("inventory")){
            inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        }


    }


    public void tick(Level level, BlockPos blockPos, BlockState blockState, PotionMixerBlockEntity be) {
        if(coolDown > 0){
            coolDown -= 1;
            return;
        }

        be.mix();
        coolDown = 100;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Silly Miner");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new PotionMixerMenu(i, inventory, this, this.inventory);
    }
}
