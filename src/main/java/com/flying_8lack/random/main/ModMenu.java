package com.flying_8lack.random.main;

import com.flying_8lack.random.menu.HElevatorMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModMenu {
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(Registries.MENU,
            MODID);

    public static final Supplier<MenuType<HElevatorMenu>> H_ELEVATOR_MENU = MENU.register("h_elevator_menu",
            () -> new MenuType<>(HElevatorMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
