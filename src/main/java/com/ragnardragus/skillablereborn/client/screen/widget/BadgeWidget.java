package com.ragnardragus.skillablereborn.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Jobs;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class BadgeWidget extends Button {

    private int left;
    private int top;
    private Jobs jobs;

    public BadgeWidget(int left, int top, int x, int y, Jobs jobs) {
        super(x, y, 20, 20, TextComponent.EMPTY, button -> {});
        this.jobs = jobs;
        this.left = left;
        this.top = top;
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        ClientUtil.bindJobsTexture();
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
            int progress = jobData.getJobLevel(this.jobs);
            ClassificationJob classification = ClassificationJob.getClassificationByIndex(progress);
            Component classificationText = new TextComponent(classification.getNote());

            blit(stack, x, y, 176, 83, 20, 20);

            if(isMouseOver(mouseX, mouseY)) {
                blit(stack, left - 80, top, 176, 126, 80, 40);

                Component jobTitle = new TranslatableComponent("skillablereborn.job."+jobs.displayName);
                Component rankText = new TextComponent("Rank: ").append(classificationText);

                minecraft.font.drawShadow(stack, jobTitle, left - (80 / 2) - minecraft.font.width(jobTitle) / 2, top + 8, ChatFormatting.GOLD.getColor());
                minecraft.font.draw(stack, rankText, left - (80 / 2) - minecraft.font.width(rankText) / 2, top + 5 + 18, ChatFormatting.WHITE.getColor());
            }

            minecraft.font.drawShadow(stack, classificationText, x + 8, y + 5, classification.getColor());
        });
    }

    private enum ClassificationJob {
        D(1, "D", ChatFormatting.LIGHT_PURPLE.getColor()),
        C(2, "C", ChatFormatting.GREEN.getColor()),
        B(3, "B", ChatFormatting.BLUE.getColor()),
        A(4, "A", ChatFormatting.YELLOW.getColor()),
        S(5, "S", ChatFormatting.GOLD.getColor());

        private int index;
        private String note;

        private int color;

        ClassificationJob(int index, String note, int color) {
            this.index = index;
            this.note = note;
            this.color = color;
        }

        public String getNote() {
            return note;
        }

        public int getColor() {
            return color;
        }

        public static ClassificationJob getClassificationByIndex(int index) {
            for (ClassificationJob classificationJob : ClassificationJob.values()) {
                if (classificationJob.index == index) {
                    return classificationJob;
                }
                if(index > 5) {
                    return S;
                }
            }
            return D;
        }
    }
}
