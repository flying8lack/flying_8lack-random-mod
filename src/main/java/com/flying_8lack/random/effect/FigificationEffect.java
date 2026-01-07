package com.flying_8lack.random.effect;

import com.flying_8lack.random.entity.FigEntity;
import com.flying_8lack.random.main.ModEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.flying_8lack.random.main.flying8lacksrandommod.lg;

public class FigificationEffect extends MobEffect {


    public FigificationEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);
    }





    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(livingEntity.getRandom().nextInt(0, 100) < 10){

            Level level = livingEntity.level();
            //FigEntity f = new FigEntity(ModEntity.FIG_ENTITY.get(), level);
            if(level instanceof ServerLevel sr) {
                FigEntity f = ModEntity.FIG_ENTITY.get().spawn(sr, livingEntity.getOnPos().above(), MobSpawnType.MOB_SUMMONED);
                if (f != null) {
                    f.setTarget(livingEntity);
                }
            }



        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }


}
