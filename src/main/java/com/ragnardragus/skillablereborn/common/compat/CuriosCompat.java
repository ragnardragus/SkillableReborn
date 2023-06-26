package com.ragnardragus.skillablereborn.common.compat;

import com.ragnardragus.skillablereborn.common.event.requiremets.RequirementHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class CuriosCompat {

    public static boolean isLoaded() {
        return ModList.get().isLoaded("curios");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChangeCurio(CurioChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            
            if (!player.isCreative()) {
                ItemStack item = event.getTo();
                
                if (!RequirementHelper.canUseItem(player, item)) {
                    player.drop(item.copy(), false);
                    item.setCount(0);
                }
            }
        }
    }
}