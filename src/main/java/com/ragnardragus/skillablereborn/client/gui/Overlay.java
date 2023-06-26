package com.ragnardragus.skillablereborn.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.api.Requirement;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.AttributeCapability;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID, value = Dist.CLIENT)
public class Overlay extends GuiComponent {

    private static List<Requirement> requirements = null;

    private static String stagesRequirement = null;

    private static boolean isArrowProjectile = false;

    private static int showTicks = 0;

    public static void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {

        if(showTicks > 0) {
            Minecraft mc = Minecraft.getInstance();

            int cx = screenWidth / 2;
            int cy = screenHeight / 4;

            ClientUtil.bindTranslucent(ClientUtil.SKILLS_RESOURCE);
            gui.blit(poseStack, cx - 71, cy - 4, 0, 196, 142, 52);

            Component message = new TranslatableComponent(isArrowProjectile ? "overlay.message.arrow" : "overlay.message");

            mc.font.drawShadow(poseStack, message, cx - mc.font.width(message.getString()) / 2f, cy, 0xFF5555);

            if (mc.player.getCapability(AttributeCapability.INSTANCE).isPresent()) {
                if(!requirements.isEmpty()) {
                    for (int i = 0; i < requirements.size(); i++) {
                        Requirement requirement = requirements.get(i);
                        int maxLevel = 32;

                        int x = cx + i * 20 - requirements.size() * 10 + 2;
                        int y = cy + 15;
                        int u = Math.min(requirement.level, maxLevel - 1) / (maxLevel / 4) * 16 + 176;
                        int v = requirement.stats.index * 16 + 128;

                        ClientUtil.bind(ClientUtil.SKILLS_RESOURCE);
                        gui.blit(poseStack, x, y, u, v, 16, 16);

                        String level = Integer.toString(requirement.level);
                        boolean met = Attribute.get().getAttributeLevel(requirement.stats) >= requirement.level;
                        mc.font.drawShadow(poseStack, new TextComponent(level), x + 17 - mc.font.width(level), y + 9, met ? 0x55FF55 : 0xFF5555);
                    }
                }
            }


            if(stagesRequirement != null) {
                boolean hasStage = GameStageHelper.hasStage(Minecraft.getInstance().player, stagesRequirement);
                String jobMessage = I18n.get("overlay.job_requirement", stagesRequirement);
                mc.font.drawShadow(poseStack, new TextComponent(jobMessage), cx - mc.font.width(jobMessage) / 2f, cy + (requirements.isEmpty() ? 24 : 34), hasStage ? 0x55FF55 : 0xFF5555);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (showTicks > 0) showTicks--;
    }

    public static void showWarning(ResourceLocation resource) {
        Requirement[] requirementsArray = SkillableReborn.jsonListener.getRequirements(resource);
        requirements = requirementsArray != null ? Arrays.asList(requirementsArray) : new ArrayList<>();
        stagesRequirement = SkillableReborn.jsonListener.getRequirementStageName(resource);
        isArrowProjectile = SkillableReborn.jsonListener.isBowProjectile(resource);
        showTicks = 60;
    }
}
