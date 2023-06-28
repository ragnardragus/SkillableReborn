package com.ragnardragus.skillablereborn.common.network.attributes;

import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StatsRefreshMsg {


    private final CompoundTag skillModel;

    public StatsRefreshMsg(CompoundTag skillModel) {
        this.skillModel = skillModel;
    }

    public StatsRefreshMsg(FriendlyByteBuf buffer) {
        this.skillModel = buffer.readNbt();
    }

    public static StatsRefreshMsg decode(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        return new StatsRefreshMsg(tag);
    }

    public static void encode(StatsRefreshMsg msg, FriendlyByteBuf buffer) {
        buffer.writeNbt(msg.skillModel);
    }

    public static class Handler {
        public static void handle(final StatsRefreshMsg msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> Attribute.get().deserializeNBT(msg.skillModel));
            ctx.get().setPacketHandled(true);
        }
    }
}
