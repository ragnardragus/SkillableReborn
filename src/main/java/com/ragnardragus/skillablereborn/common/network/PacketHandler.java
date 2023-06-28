package com.ragnardragus.skillablereborn.common.network;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.common.network.attributes.StatsRefreshMsg;
import com.ragnardragus.skillablereborn.common.network.attributes.UpgradeAttributeMsg;
import com.ragnardragus.skillablereborn.common.network.attributes.WarningMsg;
import com.ragnardragus.skillablereborn.common.network.job.AssignCurrentJob;
import com.ragnardragus.skillablereborn.common.network.job.JobRefreshMsg;
import com.ragnardragus.skillablereborn.common.network.job.UpdateLastMerchantJob;
import com.ragnardragus.skillablereborn.common.network.level.LevelUpMsg;
import com.ragnardragus.skillablereborn.common.network.level.LevelsMsg;
import com.ragnardragus.skillablereborn.common.network.level.McLevelsNeedMsg;
import com.ragnardragus.skillablereborn.common.network.level.SkillPointMsg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {


    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SkillableReborn.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(StatsRefreshMsg.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StatsRefreshMsg::decode)
                .encoder(StatsRefreshMsg::encode)
                .consumer(StatsRefreshMsg.Handler::handle)
                .add();

        net.messageBuilder(UpgradeAttributeMsg.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpgradeAttributeMsg::decode)
                .encoder(UpgradeAttributeMsg::encode)
                .consumer(UpgradeAttributeMsg.Handler::handle)
                .add();

        net.messageBuilder(WarningMsg.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(WarningMsg::decode)
                .encoder(WarningMsg::encode)
                .consumer(WarningMsg.Handler::handle)
                .add();

        net.messageBuilder(LevelUpMsg.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LevelUpMsg::decode)
                .encoder(LevelUpMsg::encode)
                .consumer(LevelUpMsg.Handler::handle)
                .add();

        net.messageBuilder(SkillPointMsg.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillPointMsg::decode)
                .encoder(SkillPointMsg::encode)
                .consumer(SkillPointMsg.Handler::handle)
                .add();

        net.messageBuilder(LevelsMsg.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LevelsMsg::decode)
                .encoder(LevelsMsg::encode)
                .consumer(LevelsMsg.Handler::handle)
                .add();


        net.messageBuilder(McLevelsNeedMsg.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(McLevelsNeedMsg::decode)
                .encoder(McLevelsNeedMsg::encode)
                .consumer(McLevelsNeedMsg.Handler::handle)
                .add();

        net.messageBuilder(UpdateLastMerchantJob.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UpdateLastMerchantJob::decode)
                .encoder(UpdateLastMerchantJob::encode)
                .consumer(UpdateLastMerchantJob.Handler::handle)
                .add();

        net.messageBuilder(AssignCurrentJob.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AssignCurrentJob::decode)
                .encoder(AssignCurrentJob::encode)
                .consumer(AssignCurrentJob.Handler::handle)
                .add();

        net.messageBuilder(JobRefreshMsg.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(JobRefreshMsg::decode)
                .encoder(JobRefreshMsg::encode)
                .consumer(JobRefreshMsg.Handler::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToPlayersTrackingEntity(MSG message, Entity entity) {
        sendToPlayersTrackingEntity(message, entity, false);
    }

    public static <MSG> void sendToPlayersTrackingEntity(MSG message, Entity entity, boolean sendToSource) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
        if (sendToSource && entity instanceof ServerPlayer serverPlayer)
            sendToPlayer(message, serverPlayer);
    }
}
