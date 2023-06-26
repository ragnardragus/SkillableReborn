package com.ragnardragus.skillablereborn.common.registries;

import com.ragnardragus.skillablereborn.client.gui.Overlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraftforge.client.gui.ForgeIngameGui.EXPERIENCE_BAR_ELEMENT;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OverlayRegistry {

    @SubscribeEvent
    public static void onRegisterOverlays(FMLClientSetupEvent eventListener) {
        net.minecraftforge.client.gui.OverlayRegistry.registerOverlayAbove(EXPERIENCE_BAR_ELEMENT, "warning_overlay", Overlay::render);
    }
}