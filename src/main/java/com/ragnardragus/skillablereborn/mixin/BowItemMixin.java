package com.ragnardragus.skillablereborn.mixin;

import com.ragnardragus.skillablereborn.common.event.requiremets.RequirementHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Inject(method = {"use"}, at = {@At("HEAD")}, cancellable = true)
    public void test(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> ci) {
        ItemStack itemstack = player.getItemInHand(hand);
        System.out.println(player.getProjectile(itemstack).getItem().getRegistryName());

        ItemStack arrow = player.getProjectile(itemstack);

        if(arrow != null && !arrow.isEmpty()) {
            if (!player.isCreative() && !RequirementHelper.canUseItem(player, arrow)) {
                ci.setReturnValue(InteractionResultHolder.fail(itemstack));
            }
        }
    }
}
