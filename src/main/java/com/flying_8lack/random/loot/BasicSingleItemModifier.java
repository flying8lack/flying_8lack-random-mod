package com.flying_8lack.random.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class BasicSingleItemModifier extends LootModifier {

    public static final MapCodec<BasicSingleItemModifier> CODEC = RecordCodecBuilder.mapCodec((inst ->
            LootModifier.codecStart(inst).and(
                    inst.group(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item),
                            Codec.INT.fieldOf("count").orElse(1).forGetter(e -> e.count))

                    ).

                    apply(inst, BasicSingleItemModifier::new)
    ));
    private final Item item;
    private final int count;

    protected BasicSingleItemModifier(LootItemCondition[] conditionsIn, Item item, int Count) {
        super(conditionsIn);
        this.item = item;
        this.count = Count;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {

        generatedLoot.add(new ItemStack(item, this.count));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
