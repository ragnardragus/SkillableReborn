package com.ragnardragus.skillablereborn.common.capabilities.attributes;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class AttributeCapability {
    public static Capability<IAttribute> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    public AttributeCapability() {}

}
