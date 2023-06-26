package com.ragnardragus.skillablereborn.common.capabilities.level;

import com.ragnardragus.skillablereborn.SkillableReborn;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LevelProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(SkillableReborn.RESOURCE_PREFIX + "cap_levels");

    private final ILevel backend = new Level();
    private final LazyOptional<ILevel> optionalData = LazyOptional.of(() -> backend);

    public void invalidate() {
        this.optionalData.invalidate();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return LevelCapability.INSTANCE.orEmpty(cap, this.optionalData);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putInt("skill_points", backend.getSkillPoints());
        compound.putInt("mc_levels_need", backend.getMcLevelsNeed());
        compound.putInt("player_mod_level", backend.getPlayerModLevel());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.setSkillPoints(nbt.getInt("skill_points"));
        backend.setMcLevelsNeed(nbt.getInt("mc_levels_need"));
        backend.setPlayerModLevel(nbt.getInt("player_mod_level"));
    }
}
