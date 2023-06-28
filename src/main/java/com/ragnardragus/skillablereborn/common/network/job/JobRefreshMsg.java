package com.ragnardragus.skillablereborn.common.network.job;

import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JobRefreshMsg {


    private final CompoundTag job;

    public JobRefreshMsg(CompoundTag skillModel) {
        this.job = skillModel;
    }

    public JobRefreshMsg(FriendlyByteBuf buffer) {
        this.job = buffer.readNbt();
    }

    public static JobRefreshMsg decode(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        return new JobRefreshMsg(tag);
    }

    public static void encode(JobRefreshMsg msg, FriendlyByteBuf buffer) {
        buffer.writeNbt(msg.job);
    }

    public static class Handler {
        public static void handle(final JobRefreshMsg msg, Supplier<NetworkEvent.Context> ctx) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            ctx.get().enqueueWork(() -> {
                player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                    jobData.deserializeNBT(msg.job);
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
