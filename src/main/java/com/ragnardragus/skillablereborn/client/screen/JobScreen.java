package com.ragnardragus.skillablereborn.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Trait;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.client.screen.widget.TraitButton;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.lwjgl.glfw.GLFW;

public class JobScreen extends Screen {

    public JobScreen() {
        super(new TranslatableComponent("skillablereborn.screen.jobs"));
    }

    @Override
    protected void init() {

        int left = (width - 176) / 2;
        int top = (height - 166) / 2;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        ClientUtil.bindTraitsTexture();

        int left = (width - 176) / 2;
        int top = (height - 166) / 2;

        renderBackground(stack);
        blit(stack, left, top, 0, 0, 176, 166);

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
    public boolean isPauseScreen() {
        return false;
    }
}
