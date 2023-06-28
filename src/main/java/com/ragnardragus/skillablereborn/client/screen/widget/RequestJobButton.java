package com.ragnardragus.skillablereborn.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Jobs;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.job.AssignCurrentJob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class RequestJobButton extends Button {

    private boolean inProgress = false;
    private boolean completed = false;

    private Jobs currentJob = Jobs.NONE;
    private Jobs lastMerchantJob = Jobs.NONE;
    private Player clientPlayer;

    public RequestJobButton(int x, int y) {
        super(x, y, 26, 16, TextComponent.EMPTY, button -> {});

        Minecraft mc = Minecraft.getInstance();
        clientPlayer = mc.player;

        clientPlayer.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
            this.currentJob = Jobs.getJobByIndex(jobData.getCurrentJobIndex());
            this.lastMerchantJob = Jobs.getJobByDisplayName(jobData.getLastMerchantJob());
        });
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        ClientUtil.bind(ClientUtil.JOBS_RESOURCE);

        if(!(lastMerchantJob == currentJob)) {
            blit(stack, x, y, 0 + (isMouseOver(mouseX, mouseY) ? 28 : 0), 184, width, height);
        } else {
            blit(stack, x, y, 0 + (isMouseOver(mouseX, mouseY) ? 28 : 0), 166, width, height);
        }
    }

    @Override
    public void onPress() {
        clientPlayer.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
            if(this.currentJob != this.lastMerchantJob) {
                this.currentJob = this.lastMerchantJob;
                jobData.setCurrentJobIndex(this.currentJob.index);
                PacketHandler.sendToServer(new AssignCurrentJob(this.currentJob.index));
            } else {
                this.currentJob = Jobs.NONE;
                jobData.setCurrentJobIndex(Jobs.NONE.index);
                PacketHandler.sendToServer(new AssignCurrentJob(Jobs.NONE.index));
            }
        });
    }
}
