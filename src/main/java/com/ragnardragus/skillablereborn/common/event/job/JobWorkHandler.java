package com.ragnardragus.skillablereborn.common.event.job;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.api.Jobs;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.job.JobRefreshMsg;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID)
public class JobWorkHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWoodcut(BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        ItemStack stack = new ItemStack(block.asItem());
        Stream<TagKey<Item>> tags = stack.getTags();
        boolean isLog = tags.anyMatch(tag -> tag == ItemTags.LOGS);

        if(isLog) {
            if (event.getPlayer() instanceof ServerPlayer) {
                ServerPlayer player = (ServerPlayer) event.getPlayer();

                player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {
                    Jobs currentJob = Jobs.getJobByIndex(jobData.getCurrentJobIndex());
                    if (currentJob == Jobs.MASON) {
                        jobData.increaseJobProgressAmount(currentJob);

                        if (jobData.getJobProgressAmount(currentJob) >= (Jobs.MASON.startObjective + (8 * jobData.getJobLevel(currentJob)))) {
                            jobData.increaseJobLevel(currentJob);
                            jobData.setJobProgressAmount(currentJob, 0);
                        }

                        PacketHandler.sendToPlayer(new JobRefreshMsg(jobData.serializeNBT()), player);
                    }
                });
            }
        }
    }
}
