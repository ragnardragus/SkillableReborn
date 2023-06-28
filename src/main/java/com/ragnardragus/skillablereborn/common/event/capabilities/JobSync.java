package com.ragnardragus.skillablereborn.common.event.capabilities;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.job.JobRefreshMsg;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID)
public class JobSync {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getPlayer();

        oldPlayer.reviveCaps();

        if(!event.isWasDeath())
            return;

        oldPlayer.getCapability(JobDataCapability.INSTANCE).ifPresent(oldJobData -> {
            newPlayer.getCapability(JobDataCapability.INSTANCE).ifPresent(newJobData -> {
                newJobData.setLastMerchantJob(oldJobData.getLastMerchantJob());
                newJobData.setCurrentJobIndex(oldJobData.getCurrentJobIndex());

                if(newPlayer instanceof ServerPlayer)
                    PacketHandler.sendToPlayer(new JobRefreshMsg(newJobData.serializeNBT()), (ServerPlayer) newPlayer);
            });
        });

        oldPlayer.invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent e) {
        if(e.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) e.getPlayer();

            player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                PacketHandler.sendToPlayer(new JobRefreshMsg(jobData.serializeNBT()), player);
            });
        }
    }

    @SubscribeEvent
    public static void onRespawnEvent(PlayerEvent.PlayerRespawnEvent e) {
        if(e.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) e.getPlayer();

            player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                PacketHandler.sendToPlayer(new JobRefreshMsg(jobData.serializeNBT()), player);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent e) {

        if(e.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) e.getPlayer();

            player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                PacketHandler.sendToPlayer(new JobRefreshMsg(jobData.serializeNBT()), player);
            });
        }
    }
}
