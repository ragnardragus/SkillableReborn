package com.ragnardragus.skillablereborn.common.network.level;

import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class McLevelsNeedMsg {

    private final int value;

    public McLevelsNeedMsg(int value) {
        this.value = value;
    }


    public static void encode(McLevelsNeedMsg msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.value);
    }

    public static McLevelsNeedMsg decode(FriendlyByteBuf buffer) {
        int value = buffer.readInt();
        return new McLevelsNeedMsg(value);
    }

    public static class Handler {
        public static void handle(final McLevelsNeedMsg msg, Supplier<NetworkEvent.Context> ctx) {

            Minecraft mc = Minecraft.getInstance();

            ctx.get().enqueueWork(() -> {
                mc.player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {
                    skillPoint.setMcLevelsNeed(msg.value);
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
