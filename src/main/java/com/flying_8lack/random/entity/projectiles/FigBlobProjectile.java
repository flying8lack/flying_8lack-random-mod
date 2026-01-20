package com.flying_8lack.random.entity.projectiles;

import com.flying_8lack.random.main.ModItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class FigBlobProjectile extends ThrowableItemProjectile {

    private LivingEntity target;
    public FigBlobProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public void setTarget(LivingEntity target){
        this.target = target;
    }

    @Override
    protected void applyGravity() {

    }


    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    public void tick() {

        if(this.tickCount >= 300){
            this.discard();
            return;
        }

        if(this.target == null){
            //this.discard();
            return;
        }



        Vec3 goalDir = this.target.position().subtract(this.position()).normalize();
        Vec3 currentDir = this.getDeltaMovement();


        this.setDeltaMovement(currentDir.lerp(goalDir, 0.08));

        super.tick();


    }

    @Override
    protected void onHitEntity(EntityHitResult result) {

        if(result.getEntity() instanceof LivingEntity le){
            if(le == this.getOwner()) return;
            le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
            le.hurt(this.damageSources().mobProjectile(this,
                    (this.getOwner() instanceof LivingEntity o) ? o : null
                    ), 3);

        }


        this.discard();
    }

    @Override
    protected Item getDefaultItem() {
        return ModItem.FIG_FOOD.asItem();
    }
}
