package com.ragnardragus.skillablereborn.common.capabilities.attributes;

import com.ragnardragus.skillablereborn.api.Stats;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface IAttribute {

    int[] getAttributeLevels();
    void setAttributeLevels(int[] attributes);
    int getAttributeLevel(Stats attributes);
    void setAttributeLevel(Stats attributes, int level);
    void increaseAttributeLevel(Stats attributes);
    void decreaseAttributeLevel(Stats attributes);
    void showWarning(Player player, ResourceLocation resource);
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag nbt);
}
