package com.ragnardragus.skillablereborn.common.capabilities.level;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class LevelCapability {

    public static Capability<ILevel> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    public LevelCapability() {}
}
