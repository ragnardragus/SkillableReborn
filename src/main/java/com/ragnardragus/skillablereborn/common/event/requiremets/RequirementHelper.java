package com.ragnardragus.skillablereborn.common.event.requiremets;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.api.Requirement;
import com.ragnardragus.skillablereborn.api.Trait;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class RequirementHelper {

    public static boolean canUseItem(Player player, ItemStack item) {
        return canUse(player, item.getItem().getRegistryName());
    }

    public static boolean canUseBlock(Player player, Block block) {
        return canUse(player, block.getRegistryName());
    }

    public static boolean canUseEntity(Player player, Entity entity) {
        return canUse(player, entity.getType().getRegistryName());
    }

    public static boolean haveTrait(Player player, Trait trait) {
        Requirement[] requirements = trait.requirements;
        return haveRequirements(player, requirements);
    }

    public static boolean haveRequirements(Player player, Requirement[] requirements) {
        if (requirements != null) {
            for (Requirement requirement : requirements) {
                if (Attribute.get(player).getAttributeLevel(requirement.stats) < requirement.level) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean canUse(Player player, ResourceLocation resource) {

        Requirement[] requirements = SkillableReborn.jsonListener.getRequirements(resource);

        if (requirements != null) {
            for (Requirement requirement : requirements) {
                if (Attribute.get(player).getAttributeLevel(requirement.stats) < requirement.level) {
                    if (player instanceof ServerPlayer) {
                        Attribute.get(player).showWarning(player, resource);
                    }

                    return false;
                }
            }
        }

        return true;
    }
}
