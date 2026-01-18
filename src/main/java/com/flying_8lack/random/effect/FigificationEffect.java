package com.flying_8lack.random.effect;

import com.flying_8lack.random.entity.FigEntity;
import com.flying_8lack.random.main.ModEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;
import static com.flying_8lack.random.main.flying8lacksrandommod.lg;

public class FigificationEffect extends MobEffect {


    public FigificationEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);

        this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(MODID, "fig_speed_reduction"),
                -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(MODID, "fig_attack_speed_reduction"),
                -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }





    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        if(livingEntity.getRandom().nextFloat() < (0.008 * livingEntity.getHealth())){

            Level level = livingEntity.level();
            //FigEntity f = new FigEntity(ModEntity.FIG_ENTITY.get(), level);
            if(level instanceof ServerLevel sr && livingEntity instanceof Player p) {

                FigEntity f = ModEntity.FIG_ENTITY.get().spawn(sr, livingEntity.getOnPos().above(), MobSpawnType.MOB_SUMMONED);
                if (f != null) {

                    p.getFoodData().setFoodLevel(Math.max(0, p.getFoodData().getFoodLevel() - 2));
                    f.setTarget(livingEntity);
                }
            }



        }
        return super.applyEffectTick(livingEntity, amplifier);
    }




    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 40 == 0;
    }


}
