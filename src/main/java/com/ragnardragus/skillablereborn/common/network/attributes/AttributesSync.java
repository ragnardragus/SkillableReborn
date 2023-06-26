package com.ragnardragus.skillablereborn.common.network.attributes;

import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AttributesSync {


    private final CompoundTag skillModel;

    public AttributesSync(CompoundTag skillModel) {
        this.skillModel = skillModel;
    }

    public AttributesSync(FriendlyByteBuf buffer) {
        this.skillModel = buffer.readNbt();
    }

    public static AttributesSync decode(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        return new AttributesSync(tag);
    }

    public static void encode(AttributesSync msg, FriendlyByteBuf buffer) {
        buffer.writeNbt(msg.skillModel);
    }

    public static class Handler {
        public static void handle(final AttributesSync msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> Attribute.get().deserializeNBT(msg.skillModel));
            ctx.get().setPacketHandled(true);
        }
    }
}
