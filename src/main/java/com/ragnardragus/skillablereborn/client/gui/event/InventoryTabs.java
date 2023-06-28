package com.ragnardragus.skillablereborn.client.gui.event;

import com.ragnardragus.skillablereborn.SkillableReborn;
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID, value = Dist.CLIENT)
public class InventoryTabs {

    @SubscribeEvent
    public static void onInitGui(ScreenEvent.InitScreenEvent.Post event) {
        Screen screen = event.getScreen();

        if (screen instanceof InventoryScreen || screen instanceof SkillScreen
                || screen instanceof TraitScreen || screen instanceof JobScreen) {
            boolean inventoryOpen = screen instanceof InventoryScreen;
            boolean skillsOpen = screen instanceof SkillScreen;
            boolean traitsOpen = screen instanceof TraitScreen;
            boolean jobsOpen = screen instanceof JobScreen;

            int x = (screen.width  / 2) - 86;
            int y = (screen.height / 2) - 110;


            event.addListener(new TabButton(x, y, TabButton.TabType.INVENTORY, inventoryOpen, button -> {}));
            event.addListener(new TabButton(x + 28, y, TabButton.TabType.SKILLS, skillsOpen, button -> {}));
            event.addListener(new TabButton(x + 56, y, TabButton.TabType.TRAITS, traitsOpen, button -> {}));
            event.addListener(new TabButton(x + 84, y, TabButton.TabType.JOBS, jobsOpen, button -> {}));
        }
    }
}
