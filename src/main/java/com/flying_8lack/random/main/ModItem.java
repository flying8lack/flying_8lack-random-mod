package com.flying_8lack.random.main;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModItem {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> SILLY_GLASS_SHARD = ITEMS.registerSimpleItem("silly_glass_shard");

    public static final DeferredItem<Item> HARD_GUM = ITEMS.registerSimpleItem("hard_gum");

    public static final DeferredItem<Item> SOFT_GUM = ITEMS.registerSimpleItem("soft_gum");


    public static final DeferredItem<Item> FIG_SPAWN_EGG = ITEMS.register(
            "fig_spawn_egg", () -> new DeferredSpawnEggItem(ModEntity.FIG_ENTITY,
                    0x503643, 0xffd6f5,
                    new Item.Properties()
            )
    );


    public static final DeferredItem<Item> FIG_FOOD = ITEMS.register(
            "fig_food", () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(3.6f)
                            .effect(() -> new MobEffectInstance(
                                    MobEffects.HEALTH_BOOST, 660, 1
                            ), 0.99f)
                            .build()
            )
            )
    );
}
