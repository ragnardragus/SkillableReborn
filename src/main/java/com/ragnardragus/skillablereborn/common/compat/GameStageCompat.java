package com.ragnardragus.skillablereborn.common.compat;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;

public class GameStageCompat {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("gamestages");
    }


    public static boolean canUseItem(Player player, ItemStack item) {
        return canUse(player, item.getItem().getRegistryName());
    }

    public static boolean canUseBlock(Player player, Block block) {
        return canUse(player, block.getRegistryName());
    }

    public static boolean canUseEntity(Player player, Entity entity) {
        return canUse(player, entity.getType().getRegistryName());
    }

    private static boolean canUse(Player player, ResourceLocation resource) {

        String stageName = SkillableReborn.jsonListener.getRequirementStageName(resource);

        if(stageName != null) {
            boolean hasStage = GameStageHelper.hasStage(player, stageName);

            if(!hasStage) {

                if (player instanceof ServerPlayer) {
                    Attribute.get(player).showWarning(player, resource);
                }

                return false;
            }
        }

        return true;
    }
}
