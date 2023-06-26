package com.ragnardragus.skillablereborn.mixin;

import com.ragnardragus.skillablereborn.api.Requirement;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.event.requiremets.EnchantedBookReqHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Map;

@Mixin(AnvilMenu.class)
public class RepairContainerMixin {

    @Inject(method = {"createResult()V"}, at = {@At("HEAD")}, cancellable = true)
    public void createResult(CallbackInfo ci) {
        AnvilMenu anvil = (AnvilMenu) (Object) this;
        ItemStack itemStack = anvil.getSlot(1).getItem();
        ItemStack copy = itemStack.copy();

        Field field = ObfuscationReflectionHelper.findField(ItemCombinerMenu.class, "player");
        field.setAccessible(true);

        Player player;

        try {
            player = (Player) field.get(anvil);
            if(!player.isCreative()) {
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);

                if(!map.isEmpty() && (itemStack.getItem() instanceof EnchantedBookItem)){
                    Requirement requirement = EnchantedBookReqHelper.getRequirementForEnchantingLeve(map);

                    if(requirement != null) {
                        if (Attribute.get(player).getAttributeLevel(requirement.stats) < requirement.level) {
                            if (player instanceof ServerPlayer) {
                                player.drop(copy, false);
                                itemStack.setCount(0);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
