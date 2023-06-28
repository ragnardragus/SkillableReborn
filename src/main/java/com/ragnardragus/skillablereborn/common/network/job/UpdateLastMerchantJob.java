package com.ragnardragus.skillablereborn.common.network.job;

import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateLastMerchantJob {

    String merchantProfession = "none";

    public UpdateLastMerchantJob(String merchantProfession) {
        this.merchantProfession = merchantProfession;
    }

    public static void encode(UpdateLastMerchantJob msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.merchantProfession);
    }

    public static UpdateLastMerchantJob decode(FriendlyByteBuf buffer) {
        String profession = buffer.readUtf();
        return new UpdateLastMerchantJob(profession);
    }

    public static class Handler {
        public static void handle(final UpdateLastMerchantJob msg, Supplier<NetworkEvent.Context> ctx) {
            Minecraft mc = Minecraft.getInstance();
            ctx.get().enqueueWork(() -> {
                mc.player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                    jobData.setLastMerchantJob(msg.merchantProfession);
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
