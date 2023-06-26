package com.ragnardragus.skillablereborn.common.event.capabilities;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.AttributeCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.attributes.AttributesSync;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID)
public class StatsSync {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getPlayer();

        oldPlayer.reviveCaps();

        if(!event.isWasDeath())
            return;

        oldPlayer.getCapability(AttributeCapability.INSTANCE).ifPresent(i -> {
            newPlayer.getCapability(AttributeCapability.INSTANCE).ifPresent(j -> {
                Attribute.get(newPlayer).setAttributeLevels(Attribute.get(oldPlayer).getAttributeLevels());
            });
        });

        oldPlayer.invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent e) {
        if(e.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) e.getPlayer();
            PacketHandler.sendToPlayer(new AttributesSync(Attribute.get(player).serializeNBT()), player);
        }
    }

    @SubscribeEvent
    public static void onRespawnEvent(PlayerEvent.PlayerRespawnEvent e) {
        if(e.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) e.getPlayer();
            PacketHandler.sendToPlayer(new AttributesSync(Attribute.get(player).serializeNBT()), player);
        }
    }

    @SubscribeEvent
    public static void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent e) {

        if(e.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) e.getPlayer();
            PacketHandler.sendToPlayer(new AttributesSync(Attribute.get(player).serializeNBT()), player);
        }
    }
}
