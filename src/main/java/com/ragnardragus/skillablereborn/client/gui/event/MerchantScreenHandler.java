package com.ragnardragus.skillablereborn.client.gui.event;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.api.Quest;
import com.ragnardragus.skillablereborn.client.screen.JobScreen;
import com.ragnardragus.skillablereborn.client.screen.SkillScreen;
import com.ragnardragus.skillablereborn.client.screen.TraitScreen;
import com.ragnardragus.skillablereborn.client.screen.widget.RequestJobButton;
import com.ragnardragus.skillablereborn.client.screen.widget.TabButton;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID, value = Dist.CLIENT)
public class MerchantScreenHandler {

    @SubscribeEvent
    public static void onInitGui(ScreenEvent.InitScreenEvent.Post event) {
        if(event.getScreen() instanceof MerchantScreen) {
            MerchantScreen screen = (MerchantScreen) event.getScreen();

            int x = (screen.width  / 2) + 105;
            int y = (screen.height / 2) - 18;

            event.addListener(new RequestJobButton(x, y));
        }
    }
}
