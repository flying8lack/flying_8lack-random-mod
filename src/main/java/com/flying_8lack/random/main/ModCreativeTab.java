package com.flying_8lack.random.main;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying_8lack.random.main.ModBlock.H_ELEVATOR;
import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.flying8lacksrandommod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModBlock.H_ELEVATOR.asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {


                //functional blocks
                output.accept(ModBlock.WALL_DOOR);
                output.accept(ModBlock.SILLY_MINER);
                output.accept(ModBlock.POTION_MIXER_BLOCK);
                output.accept(H_ELEVATOR.get());

                //misc blocks
                output.accept(ModBlock.FIG_BLOCK);

                output.accept(ModBlock.GUM_ORE);

                //misc items
                output.accept(ModItem.SILLY_GLASS_SHARD);
                output.accept(ModBlock.GUM_ORE);
                output.accept(ModItem.FIG_FOOD);
                output.accept(ModItem.HARD_GUM);
                output.accept(ModItem.SOFT_GUM);
                output.accept(ModItem.FIG_SEED);
                output.accept(ModItem.FIG_SPAWN_EGG);


            }).build());
}
