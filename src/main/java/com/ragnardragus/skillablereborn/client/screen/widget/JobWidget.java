package com.ragnardragus.skillablereborn.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Jobs;
import com.ragnardragus.skillablereborn.api.Quest;
import com.ragnardragus.skillablereborn.api.Stats;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.client.screen.JobScreen;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.CactusBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JobWidget extends Button {

    public JobWidget(int x, int y) {
        super(x, y, 143, 48, TextComponent.EMPTY, button -> {});
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        ClientUtil.bindJobsTexture();
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {

            // Get Job data //
            Jobs currentJob = Jobs.getJobByIndex(jobData.getCurrentJobIndex());
            int currentJobProgress = jobData.getJobProgressAmount(currentJob);
            int jobLevel = jobData.getJobLevel(currentJob) <= 5 ? jobData.getJobLevel(currentJob) : 6;

            // Background //
            blit(stack, x, y, 113, 208, 143, 48);

            if(jobLevel != 6) {

                // Get Objective //
                if (!Quest.QUESTS.containsKey(currentJob)) return;
                Quest.Objective objective = Quest.QUESTS.get(currentJob).get(jobLevel);
                double progressPercent = currentJob == Jobs.NONE ? 0 : ((double) currentJobProgress) / objective.getQuantity();
                int percentProgressBar = (int) Math.ceil(progressPercent * 111);
                String description = objective.getDescriptionKey();
                int quantity = objective.getQuantity();

                if (currentJob != Jobs.NONE) {

                    // Progress Bar //
                    blit(stack, x + 18, y + 34, 145, 171, 111, 5);
                    blit(stack, x + 18, y + 34, 145, 166, percentProgressBar, 5);

                    if (isMouseOver(mouseX, mouseY)) {
                        blit(stack, x - 96, y - 28, 176, 0, 80, 83);
                        blit(stack, x - 96, y + 55, 176, 0, 80, 83);

                        List<Component> descriptionLines = ClientUtil.getComponentListWithBreakLines(description, 70, minecraft);
                        Component textObjectives = new TextComponent(currentJobProgress + " / " + quantity);

                        minecraft.font.drawShadow(stack, new TextComponent("Objective"), x - 56 - (minecraft.font.width("Objective") / 2), y - 20, ChatFormatting.WHITE.getColor());
                        ClientUtil.drawTextLines(descriptionLines, minecraft.font, stack, x - 90, y, ChatFormatting.WHITE.getColor());
                        minecraft.font.drawShadow(stack, textObjectives, x - 56 - (minecraft.font.width(textObjectives) / 2), y + 40, ChatFormatting.WHITE.getColor());

                        minecraft.font.drawShadow(stack, new TextComponent("Rewards"), x - 56 - (minecraft.font.width("Rewards") / 2), y + 64, ChatFormatting.WHITE.getColor());

                        for (int i = 0; i < objective.getRewards().size(); i++) {
                            Stats stats = objective.getRewards().get(i).getStats();
                            int value = objective.getRewards().get(i).getValue();

                            Component rewardStat = new TranslatableComponent(stats.displayName);
                            Component rewardText = new TextComponent("+" + value + " ").append(rewardStat);

                            minecraft.font.draw(stack, rewardText, x - 90, y + 104 + (i * 11), ChatFormatting.GREEN.getColor());
                        }
                    }
                }
            }

            // Get Text Components //
            Component jobName = new TranslatableComponent("skillablereborn.job." + currentJob.displayName).withStyle(ChatFormatting.BOLD);
            Component jobLevelLabel = new TranslatableComponent("skillablereborn.job.level_"+ jobLevel);

            // Draw Texts //
            minecraft.font.drawShadow(stack, jobName, x + (143 / 2) - minecraft.font.width(jobName) / 2, y + 6, ChatFormatting.GOLD.getColor());
            minecraft.font.drawShadow(stack, jobLevelLabel,x + (143 / 2) - minecraft.font.width(jobLevelLabel) / 2, y + 18, ChatFormatting.WHITE.getColor());
        });

    }

    @Override
    public void playDownSound(SoundManager p_93665_) {}
}
