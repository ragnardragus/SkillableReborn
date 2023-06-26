package com.ragnardragus.skillablereborn.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Trait;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.client.screen.TraitScreen;
import com.ragnardragus.skillablereborn.common.event.requiremets.RequirementHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class TraitButton extends Button {

    private final Trait trait;
    private final TraitScreen traitScreen;

    public TraitButton(int x, int y, Trait trait, TraitScreen traitScreen) {
        super(x, y, 24, 24, TextComponent.EMPTY, button -> {});
        this.trait = trait;
        this.traitScreen = traitScreen;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        boolean haveTrait = RequirementHelper.haveTrait(player, trait);

        ClientUtil.bindTraitsTexture();
        blit(stack, x, y, 88, 166, width, height);
        blit(stack, x + 2, y + 2, haveTrait ? trait.imgXPos : trait.imgXPos + 20 , trait.imgYPos, 20, 20);

        if(isMouseOver(mouseX, mouseY)) {
            /*
            List<Component> componentList = new ArrayList<>();

            Component name = new TranslatableComponent("tooltip.trait_name." + trait.displayName);
            componentList.add(name);

            Component description =  new TranslatableComponent("tooltip.trait_desc." + trait.displayName).withStyle(ChatFormatting.DARK_GRAY);
            componentList.add(description);

            componentList.add(new TextComponent(" "));
            componentList.add(new TextComponent("Requirements: ").withStyle(ChatFormatting.GRAY));

            for (int i = 0; i < trait.requirements.length; i++) {
                boolean met = Attribute.get().getAttributeLevel(trait.requirements[i].stats) >= trait.requirements[i].level;

                Component reqName = new TranslatableComponent(trait.requirements[i].stats.displayName).append(": ");
                Component level = new TextComponent(String.valueOf(trait.requirements[i].level)).withStyle(met ? ChatFormatting.GREEN : ChatFormatting.RED);
                Component requirementMsg = new TextComponent(reqName.getString()).append(level);

                componentList.add(requirementMsg);
            }

             */

            this.traitScreen.setTraitByButton(trait);
        }
    }

    @Override
    public void onPress() {
        System.out.println(trait);
    }
}
