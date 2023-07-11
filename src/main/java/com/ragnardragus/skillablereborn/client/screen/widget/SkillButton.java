package com.ragnardragus.skillablereborn.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Stats;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.attributes.UpgradeAttributeMsg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class SkillButton extends Button {

    private final Stats stats;

    public SkillButton(int x, int y, Stats stats) {
        super(x, y, 79, 32, TextComponent.EMPTY, button -> {});

        this.stats = stats;
    }


    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        Minecraft minecraft = Minecraft.getInstance();
        ClientUtil.bindSkillsTexture();

        Player player = minecraft.player;

        int level = Attribute.get().getAttributeLevel(stats);
        int maxLevel = 32;

        int u = ((int) Math.ceil((double) level * 4 / maxLevel) - 1) * 16 + 176;
        int v = stats.index * 16 + 128;

        blit(stack, x, y, 176, (level == maxLevel ? 64 : 0) + (isMouseOver(mouseX, mouseY) ? 32 : 0), width, height);
        blit(stack, x + 6, y + 8, u, v, 16, 16);

        player.getCapability(LevelCapability.INSTANCE).ifPresent(playerLevel -> {
            if(playerLevel.getSkillPoints() > 0 && level < maxLevel) {
                blit(stack, x + 62, y + 18, 0, 168, 11, 12);
            }
        });

        minecraft.font.draw(stack, new TranslatableComponent(stats.displayName), x + 25, y + 7, 0xFFFFFF);
        minecraft.font.draw(stack, level + "/" + maxLevel, x + 25, y + 18, 0xBEBEBE);

    }

    @Override
    public void onPress() {
        PacketHandler.sendToServer(new UpgradeAttributeMsg(stats.index));
    }

}
