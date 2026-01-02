package com.flying_8lack.random.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;


public abstract class AbstractUpgradeItem extends Item {
    public AbstractUpgradeItem() {
        super(new Item.Properties().stacksTo(1));
    }

    public abstract int rangeReduction(Entity entity, Level level);
    public abstract void postOperation(Entity entity, Level level);



}
