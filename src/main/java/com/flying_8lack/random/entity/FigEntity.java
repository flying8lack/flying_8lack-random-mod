package com.flying_8lack.random.entity;


import com.flying_8lack.random.entity.goals.FireProjectileGoal;
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
import net.minecraft.world.phys.Vec3;


public class FigEntity extends PathfinderMob {

    private int cooldown = 50;
    public FigEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide() && this.isAlive()) {
            LivingEntity target = this.getTarget();

            if(target != null){
                this.cooldown -= 1;
                double distSqr = this.distanceToSqr(target);
                if(distSqr > 25 && distSqr < 81 && this.onGround() && this.cooldown <= 0){
                    Vec3 look = target.position().subtract(this.position());

                    this.setDeltaMovement(look.x*0.2, 0.6 , look.z*0.2);
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 50, 100));
                    this.cooldown = 50;
                }
            }
        }

    }



    @Override
    public boolean doHurtTarget(Entity entity) {
        if(entity instanceof LivingEntity le){
            if(le.getActiveEffects().stream().noneMatch(p -> p.is(ModEffect.FIGIFICATION))){
                le.addEffect(new MobEffectInstance(ModEffect.FIGIFICATION, 300));
            }
        }
        return super.doHurtTarget(entity);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 1.2f, 1.0f));
        this.goalSelector.addGoal(1, new FireProjectileGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.addGoal(2, new BreakDoorGoal(this, (p) -> true));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.1f));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Villager.class,
                false));
    }




}
