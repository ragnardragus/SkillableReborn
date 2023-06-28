package com.ragnardragus.skillablereborn.common.event.capabilities;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobData;
import com.ragnardragus.skillablereborn.common.capabilities.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID)
public class CapabilityEvent {
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(Attribute.class);
        event.register(Level.class);
        event.register(JobData.class);
    }
}
