package com.ragnardragus.skillablereborn.common.capabilities.attributes;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.api.Stats;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AttributeProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(SkillableReborn.RESOURCE_PREFIX + "cap_skills");

    private final Attribute backend = new Attribute();
    private final LazyOptional<IAttribute> optionalData = LazyOptional.of(() -> backend);

    public void invalidate()
    {
        optionalData.invalidate();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        return AttributeCapability.INSTANCE.orEmpty(capability, this.optionalData);
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
