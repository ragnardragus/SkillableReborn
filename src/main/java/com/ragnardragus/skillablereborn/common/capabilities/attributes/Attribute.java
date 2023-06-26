package com.ragnardragus.skillablereborn.common.capabilities.attributes;

import com.ragnardragus.skillablereborn.api.Stats;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.attributes.WarningMsg;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public class Attribute implements IAttribute, INBTSerializable<CompoundTag> {

    public int[] attributeLevels = new int[]{1, 1, 1, 1, 1, 1, 1, 1};

    @Override
    public int[] getAttributeLevels() {
        return attributeLevels;
    }

    @Override
    public void setAttributeLevels(int[] attributeLevels) {
        this.attributeLevels = attributeLevels;
    }

    @Override
    public int getAttributeLevel(Stats attributes) {
        return attributeLevels[attributes.index];
    }

    @Override
    public void setAttributeLevel(Stats attributes, int level) {
        attributeLevels[attributes.index] = level;
    }

    @Override
    public void increaseAttributeLevel(Stats attributes) {
        attributeLevels[attributes.index]++;
    }

    @Override
    public void decreaseAttributeLevel(Stats attributes) {
        attributeLevels[attributes.index]--;
    }

    @Override
    public void showWarning(Player player, ResourceLocation resource) {
        PacketHandler.sendToPlayer(new WarningMsg(resource), (ServerPlayer) player);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();

        for(int i = 0; i < Stats.values().length; i++) {
            compound.putInt(Stats.values()[i].displayName, attributeLevels[Stats.values()[i].index]);
        }

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for(int i = 0; i < Stats.values().length; i++) {
            attributeLevels[i] = nbt.getInt(Stats.values()[i].displayName);
        }
    }

    public static IAttribute get(Player player) {
        return player.getCapability(AttributeCapability.INSTANCE).orElseThrow(() ->
                new IllegalArgumentException("Player " + player.getName().getVisualOrderText() + " does not have a Stats Model!")
        );
    }

    public static IAttribute get() {
        return Minecraft.getInstance().player.getCapability(AttributeCapability.INSTANCE).orElseThrow(() ->
                new IllegalArgumentException("Player does not have a Stats Model!")
        );
    }
}
