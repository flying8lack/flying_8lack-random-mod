package com.flying_8lack.random.data;


import com.flying_8lack.random.main.ModBlock;
import com.flying_8lack.random.main.ModEntity;
import com.flying_8lack.random.main.ModItem;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.*;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class ModEntityLootTableProvider extends EntityLootSubProvider {
    public ModEntityLootTableProvider(HolderLookup.Provider registries) {
        super(FeatureFlags.DEFAULT_FLAGS, registries);
    }


    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntity.ET.getEntries()
                .stream()
                .map(DeferredHolder::value);
    }

    @Override
    public void generate() {
        add(ModEntity.FIG_ENTITY.get(), LootTable.lootTable()
                .withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItem.FIG_FOOD)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,
                                        ConstantValue.exactly(2.0f)))
                        )
                )
        );

        add(ModEntity.FIG_BLOB_ENTITY.get(), LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0f))
                                .add(LootItem.lootTableItem(ModItem.FIG_FOOD)
                                )
                )
        );

        add(ModEntity.LOLIPOP_ENTITY.get(), LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0f))
                                .add(LootItem.lootTableItem(ModItem.HARD_GUM)
                                )
                )
        );




    }
}
