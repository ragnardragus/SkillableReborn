package com.ragnardragus.skillablereborn.common.event.requiremets;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.common.compat.GameStageCompat;
import com.ragnardragus.skillablereborn.serialization.RequirementsJsonListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID)
public class RequirementEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if(GameStageCompat.isLoaded()) {
            if (!player.isCreative() && (!GameStageCompat.canUseItem(player, item) || !GameStageCompat.canUseBlock(player, block))) {
                event.setCanceled(true);
            }
        }

        if(!player.isCreative() && (!RequirementHelper.canUseItem(player, item) || !RequirementHelper.canUseBlock(player, block))) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if(GameStageCompat.isLoaded()) {
            if (!player.isCreative() && (!GameStageCompat.canUseItem(player, item) || !GameStageCompat.canUseBlock(player, block))) {
                event.setCanceled(true);
            }
        }

        if (!player.isCreative() && (!RequirementHelper.canUseItem(player, item) || !RequirementHelper.canUseBlock(player, block))) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();

        if(GameStageCompat.isLoaded()) {
            if (!player.isCreative() && !GameStageCompat.canUseItem(player, item)) {
                event.setCanceled(true);
            }
        }

        if (!player.isCreative() && !RequirementHelper.canUseItem(player, item)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        Entity entity = event.getTarget();
        ItemStack item = event.getItemStack();

        if (!player.isCreative()) {

            if(GameStageCompat.isLoaded()) {
                if (!GameStageCompat.canUseEntity(player, entity) || !GameStageCompat.canUseItem(player, item)) {
                    event.setCanceled(true);
                }
            }

            if (!RequirementHelper.canUseEntity(player, entity) || !RequirementHelper.canUseItem(player, item)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getPlayer();

        if (player != null) {
            ItemStack item = player.getMainHandItem();

            if(GameStageCompat.isLoaded()) {
                if (!player.isCreative() && !GameStageCompat.canUseItem(player, item)) {
                    event.setCanceled(true);
                }
            }

            if (!player.isCreative() && !RequirementHelper.canUseItem(player, item)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onChangeEquipment(LivingEquipmentChangeEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();

            if (!player.isCreative() && event.getSlot().getType() == EquipmentSlot.Type.ARMOR)
            {
                ItemStack item = event.getTo();

                if(GameStageCompat.isLoaded()) {
                    if (!GameStageCompat.canUseItem(player, item)) {
                        player.drop(item.copy(), false);
                        item.setCount(0);
                    }
                }

                if (!RequirementHelper.canUseItem(player, item)) {
                    player.drop(item.copy(), false);
                    item.setCount(0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        SkillableReborn.jsonListener = new RequirementsJsonListener();
        event.addListener(SkillableReborn.jsonListener);
    }
}
