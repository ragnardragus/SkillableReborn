package com.ragnardragus.skillablereborn;

import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.AttributeProvider;
import com.ragnardragus.skillablereborn.common.capabilities.level.Level;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelProvider;
import com.ragnardragus.skillablereborn.common.commands.ModCommands;
import com.ragnardragus.skillablereborn.common.compat.CuriosCompat;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.registries.OverlayRegistry;
import com.ragnardragus.skillablereborn.serialization.RequirementsJsonListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.BowItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod("skillablereborn")
public class SkillableReborn {

    @Nonnull
    public static final String MOD_ID = "skillablereborn";
    public static final String RESOURCE_PREFIX = MOD_ID + ":";
    public static RequirementsJsonListener jsonListener;


    public SkillableReborn() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);

        eventBus.addListener(OverlayRegistry::onRegisterOverlays);

        if (CuriosCompat.isLoaded())
            MinecraftForge.EVENT_BUS.register(new CuriosCompat());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.register();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {

            event.addCapability(AttributeProvider.IDENTIFIER, new AttributeProvider());
            event.addCapability(LevelProvider.IDENTIFIER, new LevelProvider());
        }
    }
}
