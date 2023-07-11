package com.ragnardragus.skillablereborn.client.screen.widget;


import com.mojang.blaze3d.vertex.PoseStack;
import com.ragnardragus.skillablereborn.client.ClientUtil;
import com.ragnardragus.skillablereborn.client.screen.SkillScreen;
import com.ragnardragus.skillablereborn.client.screen.TraitScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class TabButton extends Button {

    private final boolean selected;
    private final TabType type;

    public TabButton(int x, int y, TabType type, boolean selected, Button.OnPress onPress) {
        super(x, y, 28, 30, TextComponent.EMPTY, onPress);

        this.type = type;
        this.selected = selected;
    }

    @Override
    public void render(@NotNull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {

        Minecraft minecraft = Minecraft.getInstance();
        active = !(minecraft.screen instanceof InventoryScreen) || !((InventoryScreen) minecraft.screen).getRecipeBookComponent().isVisible();

        if (active) {
            ClientUtil.bindSkillsTexture();

            blit(matrix, x, y, selected ? 11 : 39, 166, width, height);
            blit(matrix, x + 6 , y + (selected ? 6 : 8), 240, 128 + type.iconIndex * 16, 16, 16);
        }
    }

    @Override
    public void onPress() {
        Minecraft minecraft = Minecraft.getInstance();

        switch (type) {
            case INVENTORY -> minecraft.setScreen(new InventoryScreen(minecraft.player));
            case SKILLS -> minecraft.setScreen(new SkillScreen());
            case TRAITS -> minecraft.setScreen(new TraitScreen());
        }
    }

    public enum TabType {
        INVENTORY (0),
        SKILLS (1),
        TRAITS(2);

        public final int iconIndex;

        TabType(int index)
        {
            iconIndex = index;
        }
    }
}
