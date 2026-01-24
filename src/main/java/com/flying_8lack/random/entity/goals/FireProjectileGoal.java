package com.flying_8lack.random.entity.goals;

import com.flying_8lack.random.entity.projectiles.FigBlobProjectile;
import com.flying_8lack.random.main.ModEntity;
import com.flying_8lack.random.main.ModSound;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;
import java.util.function.Supplier;

public class FireProjectileGoal extends Goal {

    private int timer = 0;
    private Mob mob;
    private Function<Mob, Projectile> projectile;

    public  FireProjectileGoal(Mob mob, Function<Mob, Projectile> projectile){
        this.mob = mob;
        this.projectile = projectile;
    }
    @Override
    public boolean canUse() {
        var b = this.mob.getTarget();
        return b != null && b.position().distanceToSqr(this.mob.position()) > 80;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer <= 80;
    }

    @Override
    public void start() {
        this.timer = 0;
        this.mob.level().playSound(this.mob, this.mob.blockPosition(),
                SoundEvent.createVariableRangeEvent(ModSound.WET_SOUND.value().getLocation()),
                SoundSource.HOSTILE, 1.0f, 1.2f);
    }



    @Override
    public void tick() {
        if(this.mob.level().isClientSide){
            return;
        }
       this.timer++;

       if(this.timer == 80){
           LivingEntity target = this.mob.getTarget();

           if(target == null) return;
           this.mob.getLookControl().setLookAt(target);
           this.mob.level().addFreshEntity(this.projectile.apply(this.mob));

       }
    }
}
