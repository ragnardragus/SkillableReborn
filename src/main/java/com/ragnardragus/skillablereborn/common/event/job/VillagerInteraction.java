package com.ragnardragus.skillablereborn.common.event.job;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.api.Jobs;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.job.UpdateLastMerchantJob;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID)
public class VillagerInteraction {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if(event.getTarget() instanceof Villager) {
            Villager villager = (Villager) event.getTarget();

            if(event.getEntity() instanceof ServerPlayer) {
                ServerPlayer player = (ServerPlayer) event.getEntity();
                player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                    jobData.setLastMerchantJob(Jobs.parseToJobs(villager.getVillagerData().getProfession()).displayName);
                    PacketHandler.sendToPlayer(new UpdateLastMerchantJob(jobData.getLastMerchantJob()), player);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if(event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                System.out.println(Jobs.getJobByIndex(jobData.getCurrentJobIndex()));
            });
        }
    }
}
