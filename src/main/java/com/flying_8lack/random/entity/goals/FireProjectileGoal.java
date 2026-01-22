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
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.Vec3;

public class FireProjectileGoal extends Goal {

    private int timer = 0;
    private Mob mob;

    public  FireProjectileGoal(Mob mob){
        this.mob = mob;
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
           Vec3 loc = this.mob.getForward().scale(2);

           if(target == null) return;
           this.mob.getLookControl().setLookAt(target);
           FigBlobProjectile b = ModEntity.FIG_BLOB_ENTITY.get().spawn((ServerLevel) this.mob.level(),
                   this.mob.blockPosition().offset((int) loc.x, (int) (loc.y+this.mob.getBbHeight()), (int) loc.z),
                   MobSpawnType.MOB_SUMMONED);

           b.setOwner(this.mob);

           b.setTarget(target);

       }
    }
}
