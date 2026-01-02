package com.flying_8lack.random.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class PostProtectionUpgradeItem extends AbstractUpgradeItem{
    public PostProtectionUpgradeItem() {

    }

    @Override
    public int rangeReduction(Entity entity, Level level) {
        return 0;
    }


    @Override
    public void postOperation(Entity entity, Level level) {
        if(entity instanceof LivingEntity le){
            le.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(
                    Component.literal("Gives limited protection when using the elevator.")
        );


    }
}
