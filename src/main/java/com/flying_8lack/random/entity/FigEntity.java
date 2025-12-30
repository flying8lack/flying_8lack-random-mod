package com.flying_8lack.random.entity;


import com.flying_8lack.random.main.ModEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.animal.Animal;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class FigEntity extends PathfinderMob {
    public FigEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void aiStep() {
        super.aiStep();
//        if(this.getTarget() instanceof LivingEntity le){
//            if(le.blockPosition().closerToCenterThan(this.blockPosition().getBottomCenter(),2)){
//                le.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60));
//            }
//
//        }

    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(entity instanceof LivingEntity le){
            le.addEffect(new MobEffectInstance(ModEffect.FIGIFICATION, 300));
        }
        return super.doHurtTarget(entity);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 1.2f, 1.0f));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.addGoal(2, new BreakDoorGoal(this, (p) -> true));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.1f));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Villager.class,
                false));
    }


}
