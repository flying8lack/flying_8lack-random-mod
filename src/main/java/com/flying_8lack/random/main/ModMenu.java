package com.flying_8lack.random.main;

import com.flying_8lack.random.menu.HElevatorMenu;
import com.flying_8lack.random.menu.PotionMixerMenu;
import com.flying_8lack.random.menu.SillyMinerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModMenu {
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(Registries.MENU,
            MODID);

    public static final Supplier<MenuType<HElevatorMenu>> H_ELEVATOR_MENU = MENU.register("h_elevator_menu",
            () -> IMenuTypeExtension.create(HElevatorMenu::new));

    public static final Supplier<MenuType<PotionMixerMenu>> POTION_MIXER_MENU = MENU.register("potion_mixer_menu",
            () -> IMenuTypeExtension.create(PotionMixerMenu::new));

    public static final Supplier<MenuType<SillyMinerMenu>> SILLY_MINER_MENU = MENU.register("silly_miner_menu",
            () -> IMenuTypeExtension.create(SillyMinerMenu::new));
}
