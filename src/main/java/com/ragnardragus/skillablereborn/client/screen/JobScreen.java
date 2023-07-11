package com.ragnardragus.skillablereborn.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Jobs;
import com.ragnardragus.skillablereborn.api.Trait;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.client.screen.widget.BadgeWidget;
import com.ragnardragus.skillablereborn.client.screen.widget.JobWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.lwjgl.glfw.GLFW;

public class JobScreen extends Screen {

    public JobScreen() {
        super(new TranslatableComponent("skillablereborn.screen.jobs"));
    }

    private int top;
    private int left;

    @Override
    protected void init() {
        this.left = (width - 176) / 2;
        this.top = (height - 166) / 2;

        addRenderableWidget(new JobWidget(left + 16, top + 28));

        Jobs[] jobs = Jobs.values();

        for(int i = 1; i < jobs.length; i++) {
            if(jobs[i] != Jobs.NONE) {
                int x = (left + 12) + (i-1) % 6 * 26; // -2px
                int y = (top + 104) + (i-1) / 6 * 32; // -2px

                addRenderableWidget(new BadgeWidget(left, top, x, y, jobs[i]));
            }
        }
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        ClientUtil.bindJobsTexture();

        renderBackground(stack);
        blit(stack, left, top, 0, 0, 176, 166);

        font.draw(stack, title, width / 2 - font.width(title.getString()) / 2, top + 6, 0x3F3F3F);
        font.draw(stack, new TextComponent("Classification"), width / 2 - font.width(new TextComponent("Classification")) / 2, top + 82, 0x3F3F3F);

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
