package com.ragnardragus.skillablereborn.common.network.attributes;

import com.ragnardragus.skillablereborn.api.Stats;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.IAttribute;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpgradeAttributeMsg {

    private final int skill;

    public UpgradeAttributeMsg(int skill) {
        this.skill = skill;
    }

    public static UpgradeAttributeMsg decode(FriendlyByteBuf buffer) {
        int skill = buffer.readInt();
        return new UpgradeAttributeMsg(skill);
    }

    public static void encode(UpgradeAttributeMsg msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.skill);
    }


    public static class Handler {
        public static void handle(UpgradeAttributeMsg msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                ServerPlayer player = context.get().getSender();
                IAttribute attribute = Attribute.get(player);
                Stats stats = Stats.values()[msg.skill];

                player.getCapability(LevelCapability.INSTANCE).ifPresent(playerLevel -> {
                    int skillPoints = playerLevel.getSkillPoints();
                    if (skillPoints > 0 && Attribute.get(player).getAttributeLevel(stats) < 32) {
                        attribute.increaseAttributeLevel(stats);
                        playerLevel.subtractSkillPoints();

                        PacketHandler.sendToPlayer(new StatsRefreshMsg(Attribute.get(player).serializeNBT()), player);
                        playerLevel.sync(player);
                    }
                });
            });

            context.get().setPacketHandled(true);
        }
    }
}
