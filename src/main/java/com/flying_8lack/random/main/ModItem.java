package com.flying_8lack.random.main;

import com.flying_8lack.random.items.PostProtectionUpgradeItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import com.flying_8lack.random.items.potions.ModPotions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModItem {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> SILLY_GLASS_SHARD = ITEMS.registerSimpleItem("silly_glass_shard");

    public static final DeferredItem<PostProtectionUpgradeItem> POST_PROTECTION_UPGRADE_ITEM = ITEMS.register(
            "post_protection_upgrade_item", PostProtectionUpgradeItem::new
    );


    public static final DeferredItem<Item> FIG_FOOD = ITEMS.register(
            "fig_food", () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(3.6f)
                            .effect(() -> new MobEffectInstance(
                                    MobEffects.LUCK, 100
                            ), 0.1f)
                            .build()
            )
            )
    );
}
