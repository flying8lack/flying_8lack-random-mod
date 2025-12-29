package com.flying_8lack.random.effect;

import com.flying_8lack.random.entity.FigEntity;
import com.flying_8lack.random.main.ModEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.flying_8lack.random.main.flying8lacksrandommod.lg;

public class FigificationEffect extends MobEffect {

    public int amount = 0;
    public FigificationEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);
    }



    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(livingEntity.getRandom().nextInt(0, 100) < 20){

            Level level = livingEntity.level();
            Creeper f = new Creeper(EntityType.CREEPER, level);

            f.moveTo(livingEntity.getOnPos().above().getCenter());

            lg().debug("Sent a creeper to player {}", f.blockPosition());

            level.addFreshEntity(f);
            this.amount += 1;


        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return this.amount < 3*amplifier && duration % 16 == 0;
    }
}
