package com.ragnardragus.skillablereborn.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.client.screen.SkillScreen;
import com.ragnardragus.skillablereborn.client.screen.TraitScreen;
import com.ragnardragus.skillablereborn.client.screen.widget.LevelUpButton;
import com.ragnardragus.skillablereborn.client.screen.widget.TraitButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID, value = Dist.CLIENT)
public class ClientUtil {

    public static final ResourceLocation SKILLS_RESOURCE = new ResourceLocation(SkillableReborn.MOD_ID, "textures/gui/skills.png");
    public static final ResourceLocation TRAITS_RESOURCE = new ResourceLocation(SkillableReborn.MOD_ID, "textures/gui/traits.png");

    public static void bind(ResourceLocation texture) {
         bind(texture, true);
    }

    public static void bindSkillsTexture() {
        bind(SKILLS_RESOURCE);
    }

    public static void bindTraitsTexture() {
        bind(TRAITS_RESOURCE);
    }

    public static void bind(ResourceLocation texture, boolean setShader) {
        if (setShader) {
           RenderSystem.setShader(GameRenderer::getPositionTexShader);
        }

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void bindTranslucent(ResourceLocation texture) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getRendertypeTranslucentShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, texture);
    }

    public static void drawHoveringText(PoseStack poseStack, List<Component> textLines, int x, int y) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        Screen screen = minecraft.screen;
        if (screen == null) {
            return;
        }

        screen.renderComponentTooltip(poseStack, textLines, x, y, font);
    }

    @SubscribeEvent
    public static void renderButtons(ScreenEvent.DrawScreenEvent.Post event) {
        if (event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof SkillScreen || event.getScreen() instanceof TraitScreen) {
            event.getScreen().renderables.forEach(widget -> {
                if (widget instanceof TraitButton || widget instanceof LevelUpButton) {
                    widget.render(event.getPoseStack(), event.getMouseX(), event.getMouseY(), event.getPartialTicks());
                }
            });
        }
    }
}
