package com.ragnardragus.skillablereborn.common.event.capabilities;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID)
public class LevelSync {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getPlayer();

        oldPlayer.reviveCaps();

        if(!event.isWasDeath())
            return;

        oldPlayer.getCapability(LevelCapability.INSTANCE).ifPresent(oldLevels -> {
            newPlayer.getCapability(LevelCapability.INSTANCE).ifPresent(newLevels -> {
                newLevels.setSkillPoints(oldLevels.getSkillPoints());
                newLevels.setMcLevelsNeed(oldLevels.getMcLevelsNeed());
                newLevels.setPlayerModLevel(oldLevels.getPlayerModLevel());
            });
        });

        oldPlayer.invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        syncClientSideSkills(player);
    }

    @SubscribeEvent
    public static void onRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        syncClientSideSkills(player);
    }

    @SubscribeEvent
    public static void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        syncClientSideSkills(player);
    }

    private static void syncClientSideSkills(ServerPlayer player) {
        if(player != null && player.level != null && !player.level.isClientSide) {
            player.getCapability(LevelCapability.INSTANCE).ifPresent(skill -> {
                skill.sync(player);
            });
        }
    }
}
