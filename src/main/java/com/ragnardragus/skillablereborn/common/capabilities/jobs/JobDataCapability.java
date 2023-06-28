package com.ragnardragus.skillablereborn.common.capabilities.jobs;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class JobDataCapability {

    public static Capability<IJobData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    public JobDataCapability(){}
}
