package com.ragnardragus.skillablereborn.client.gui.event;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.client.screen.SkillScreen;
import com.ragnardragus.skillablereborn.client.screen.TraitScreen;
import com.ragnardragus.skillablereborn.client.screen.widget.TabButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID, value = Dist.CLIENT)
public class InventoryTabsScreenHandler {

    @SubscribeEvent
    public static void onInitGui(ScreenEvent.InitScreenEvent.Post event) {
        Screen screen = event.getScreen();

        if (screen instanceof InventoryScreen || screen instanceof SkillScreen || screen instanceof TraitScreen) {
            boolean inventoryOpen = screen instanceof InventoryScreen;
            boolean skillsOpen = screen instanceof SkillScreen;
            boolean traitsOpen = screen instanceof TraitScreen;

            int x = (screen.width  / 2) - 86;
            int y = (screen.height / 2) - 110;


            event.addListener(new TabButton(x, y, TabButton.TabType.INVENTORY, inventoryOpen, button -> {}));
            event.addListener(new TabButton(x + 28, y, TabButton.TabType.SKILLS, skillsOpen, button -> {}));
            event.addListener(new TabButton(x + 56, y, TabButton.TabType.TRAITS, traitsOpen, button -> {}));
        }
    }
}
