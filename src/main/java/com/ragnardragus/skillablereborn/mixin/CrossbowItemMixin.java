package com.ragnardragus.skillablereborn.mixin;

import com.ragnardragus.skillablereborn.common.event.requiremets.RequirementHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(method = {"use"}, at = {@At("HEAD")}, cancellable = true)
    public void onUseCrossbow(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> ci) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack arrow = player.getProjectile(itemstack);

        if(arrow != null && !arrow.isEmpty()) {
            if (!player.isCreative() && !RequirementHelper.canUseItem(player, arrow)) {
                ci.setReturnValue(InteractionResultHolder.fail(itemstack));
            }
        }
    }
}
