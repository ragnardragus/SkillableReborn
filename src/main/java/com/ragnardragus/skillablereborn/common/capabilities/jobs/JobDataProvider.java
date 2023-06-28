package com.ragnardragus.skillablereborn.common.capabilities.jobs;

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

public class JobDataProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(SkillableReborn.RESOURCE_PREFIX + "cap_job_data");

    private final IJobData backend = new JobData();
    private final LazyOptional<IJobData> optionalData = LazyOptional.of(() -> backend);

    public void invalidate() {
        this.optionalData.invalidate();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return JobDataCapability.INSTANCE.orEmpty(cap, this.optionalData);
    }

    @Override
    public CompoundTag serializeNBT() {
        return backend.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.deserializeNBT(nbt);
    }
}
