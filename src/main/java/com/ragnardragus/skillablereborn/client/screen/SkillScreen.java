package com.ragnardragus.skillablereborn.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Stats;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.client.screen.widget.LevelUpButton;
import com.ragnardragus.skillablereborn.client.screen.widget.SkillButton;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class SkillScreen extends Screen {
    public SkillScreen() {
        super(new TranslatableComponent("skillablereborn.screen.skills"));
    }

    @Override
    protected void init() {

        int left = (width - 162) / 2;
        int top = (height - 112) / 2; // - 8px

        for (int i = 0; i < 8; i++) {
            int x = left + i % 2 * 81; // -2px
            int y = top + i / 2 * 34; // -2px

            addRenderableWidget(new SkillButton(x, y, Stats.values()[i]));
        }

        addRenderableWidget(new LevelUpButton(left, top - 18));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        ClientUtil.bind(ClientUtil.SKILLS_RESOURCE);

        int left = (width - 176) / 2;
        int top = (height - 166) / 2;

        renderBackground(stack);

        blit(stack, left, top, 0, 0, 176, 166);

        blit(stack, left + 147, top + 10, 108, 167, 20, 12);
        blit(stack, left + 21, top + 10, 108, 167, 20, 12);

        Player player = minecraft.player;

        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {
            String skillPointString = String.valueOf(skillPoint.getSkillPoints());
            font.draw(stack, new TextComponent(skillPointString), width /2 - font.width(skillPointString) / 2 + 70, top + 12, 0x00C800);

            String levelString = String.valueOf(skillPoint.getPlayerModLevel());
            font.draw(stack, new TextComponent(levelString), width /2 - font.width(levelString) / 2 - 57, top + 12, 0xFFFFFF);
        });

        font.draw(stack, title, width / 2 - font.width(title.getString()) / 2, top + 6, 0x3F3F3F);

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int key, int p_231046_2_, int p_231046_3_) {
        if(key == GLFW.GLFW_KEY_E) {
            this.onClose();
            return true;
        }
        return super.keyPressed(key, p_231046_2_, p_231046_3_);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
