package com.ragnardragus.skillablereborn.client.screen.widget;


import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.level.LevelUpMsg;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class LevelUpButton extends Button {

    private boolean canLevelUp = false;
    public LevelUpButton(int x, int y) {
        super(x, y, 14, 14, TextComponent.EMPTY, button -> {}, (btn, stack, mouseX, mouseY) -> {

        });
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientUtil.bindSkillsTexture();

        blit(poseStack, x, y, 145, canLevelUp ? 240 : 226, 14, 14);

        Player player = minecraft.player;

        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {
            canLevelUp = player.experienceLevel >= skillPoint.getMcLevelsNeed();

            if(isMouseOver(mouseX, mouseY)) {

                Component levelMsg = new TextComponent("Lv.: " + skillPoint.getPlayerModLevel());
                Component expLevelMsg = new TextComponent(String.valueOf(player.experienceLevel))
                        .withStyle(canLevelUp ? ChatFormatting.GREEN : ChatFormatting.RED);
                Component needLevelMsg = new TextComponent("/" + skillPoint.getMcLevelsNeed())
                        .withStyle(ChatFormatting.WHITE);
                Component requireMsg = new TextComponent("Req.: ")
                        .append(expLevelMsg)
                        .append(needLevelMsg);

                List<Component> componentsList = new ArrayList<>();
                componentsList.add(levelMsg);
                componentsList.add(requireMsg);

                ClientUtil.drawHoveringText(poseStack, componentsList, mouseX, mouseY);
            }
        });
    }

    @Override
    public void onPress() {
        if(canLevelUp) {
            PacketHandler.sendToServer(new LevelUpMsg(true));
        }
    }
}
