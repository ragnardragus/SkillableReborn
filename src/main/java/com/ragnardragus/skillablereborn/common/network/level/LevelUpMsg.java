package com.ragnardragus.skillablereborn.common.network.level;

import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LevelUpMsg {

    private boolean aux = false;

    public LevelUpMsg(boolean aux) {
        this.aux = aux;
    }

    public static LevelUpMsg decode(FriendlyByteBuf buffer) {
        return new LevelUpMsg(buffer.readBoolean());
    }

    public static void encode(LevelUpMsg msg, FriendlyByteBuf buffer) {
        buffer.writeBoolean(msg.aux);
    }

    public static class Handler {
        public static void handle(final LevelUpMsg msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {

                    if (player.experienceLevel >= skillPoint.getMcLevelsNeed()) {
                        int oldLevelsNeed = skillPoint.getMcLevelsNeed();
                        player.giveExperienceLevels(-skillPoint.getMcLevelsNeed());
                        skillPoint.setMcLevelsNeed(oldLevelsNeed + 1);

                        skillPoint.addPlayerModLevel();
                        skillPoint.addSkillPoints();

                        if(player instanceof ServerPlayer) {
                            skillPoint.sync(player);
                        }
                    }
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
