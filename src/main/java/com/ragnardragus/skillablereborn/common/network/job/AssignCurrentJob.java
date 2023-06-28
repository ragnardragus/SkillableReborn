package com.ragnardragus.skillablereborn.common.network.job;

import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AssignCurrentJob {

    private final int value;

    public AssignCurrentJob(int value) {
        this.value = value;
    }

    public static void encode(AssignCurrentJob msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.value);
    }

    public static AssignCurrentJob decode(FriendlyByteBuf buffer) {
        int value = buffer.readInt();
        return new AssignCurrentJob(value);
    }

    public static class Handler {
        public static void handle(final AssignCurrentJob msg, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                    jobData.setCurrentJobIndex(msg.value);
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
