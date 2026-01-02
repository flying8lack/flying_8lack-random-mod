package com.flying_8lack.random.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class PostProtectionUpgradeItem extends AbstractUpgradeItem{
    public PostProtectionUpgradeItem() {

    }

    @Override
    public void preOperation(Entity entity, Level level) {

    }

    @Override
    public void postOperation(Entity entity, Level level) {
        if(entity instanceof LivingEntity le){
            le.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1));
        }
    }
}
