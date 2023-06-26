package com.ragnardragus.skillablereborn.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.api.Trait;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.client.screen.widget.TraitButton;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class TraitScreen extends Screen {

    private Trait trait = null;
    public TraitScreen() {
        super(new TranslatableComponent("skillablereborn.screen.traits"));
    }

    @Override
    protected void init() {

        int left = (width - 176) / 2;
        int top = (height - 166) / 2;

        Trait[] traits = Trait.values();

        for(int i = 0; i < traits.length; i++) {

            int x = (left + 8) + i % 5 * 34; // -2px
            int y = (top + 28) + i / 5 * 32; // -2px

            addRenderableWidget(new TraitButton(x, y, traits[i], this));
        }
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        ClientUtil.bindTraitsTexture();

        int left = (width - 176) / 2;
        int top = (height - 166) / 2;

        renderBackground(stack);
        blit(stack, left, top, 0, 0, 176, 166);

        if(trait != null) {
            blit(stack, left - 88, top, 0, 166, 88, 90);
            font.drawShadow(stack, new TranslatableComponent("tooltip.trait_name." + trait.displayName), left - 80, top + 10, ChatFormatting.WHITE.getColor());
            font.draw(stack, new TranslatableComponent("tooltip.requirements"), left - 80 , top + 40, ChatFormatting.GRAY.getColor());

            for (int i = 0; i < trait.requirements.length; i++) {
                boolean met = Attribute.get().getAttributeLevel(trait.requirements[i].stats) >= trait.requirements[i].level;

                Component reqName = new TranslatableComponent(trait.requirements[i].stats.displayName).append(": ");
                Component level = new TextComponent(String.valueOf(trait.requirements[i].level));
                Component requirementMsg = new TextComponent(reqName.getString()).append(level);

                font.draw(stack, requirementMsg, left - 80 , top + 52 + (i * 12), met ? ChatFormatting.GREEN.getColor() : ChatFormatting.RED.getColor());
            }
        }

        font.draw(stack, title, width / 2 - font.width(title.getString()) / 2, top + 6, 0x3F3F3F);

        this.trait = null;
        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int key, int p_231046_2_, int p_231046_3_) {
        if(key == GLFW.GLFW_KEY_E) {
            this.onClose();
            return true;
        }
        return super.keyPressed(key, p_231046_2_, p_231046_3_);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    public void setTraitByButton(Trait trait) {
        this.trait = trait;
    }
}
