package com.ragnardragus.skillablereborn.client.gui;

import com.ragnardragus.skillablereborn.SkillableReborn;
import com.ragnardragus.skillablereborn.api.Requirement;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.compat.GameStageCompat;
import com.ragnardragus.skillablereborn.common.event.requiremets.EnchantedBookReqHelper;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SkillableReborn.MOD_ID, value = Dist.CLIENT)
public class Tooltip {

    @SubscribeEvent
    public static void onTooltipDisplay(ItemTooltipEvent event) {
        if (Minecraft.getInstance().player != null) {
            ItemStack itemStack = event.getItemStack();
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);

            if(!map.isEmpty() && (itemStack.getItem() instanceof EnchantedBookItem)) {
                Requirement requirement = EnchantedBookReqHelper.getRequirementForEnchantingLeve(map);

                List<Component> tooltips = event.getToolTip();
                tooltips.add(TextComponent.EMPTY);

                if(requirement != null) {
                    tooltips.add(new TranslatableComponent("tooltip.requirements").append(":").withStyle(ChatFormatting.GRAY));
                    ChatFormatting colour = Attribute.get().getAttributeLevel(requirement.stats) >= requirement.level ? ChatFormatting.GREEN : ChatFormatting.RED;
                    tooltips.add(new TranslatableComponent(requirement.stats.displayName).append(": " + requirement.level).withStyle(colour));
                }
            } else {
                Requirement[] requirements = SkillableReborn.jsonListener.getRequirements(event.getItemStack().getItem().getRegistryName());
                String stage = SkillableReborn.jsonListener.getRequirementStageName(event.getItemStack().getItem().getRegistryName());

                List<Component> tooltips = event.getToolTip();

                if ((requirements != null && requirements.length > 0) || stage != null) {
                    tooltips.add(TextComponent.EMPTY);
                    tooltips.add(new TranslatableComponent("tooltip.requirements").append(":").withStyle(ChatFormatting.GRAY));
                }

                if (requirements != null && requirements.length > 0) {
                    for (Requirement requirement : requirements) {
                        ChatFormatting colour = Attribute.get().getAttributeLevel(requirement.stats) >= requirement.level ? ChatFormatting.GREEN : ChatFormatting.RED;
                        tooltips.add(new TranslatableComponent(requirement.stats.displayName).append(": " + requirement.level).withStyle(colour));
                    }
                }

                if (stage != null && GameStageCompat.isLoaded()) {
                    boolean hasStage = GameStageHelper.hasStage(Minecraft.getInstance().player, stage);
                    ChatFormatting colour = hasStage ? ChatFormatting.GREEN : ChatFormatting.RED;
                    tooltips.add(new TranslatableComponent("[ " + stage + " ]").withStyle(colour));
                }
            }
        }
    }
}
