package com.ragnardragus.skillablereborn.api;

public enum Stats {
    STRENGTH (0, "strength"),
    SURVIVAL (1, "survival"),
    COMBAT (2, "combat"),
    TENACITY(3, "tenacity"),
    CRAFTING (4, "crafting"),
    CULTIVATION (5, "cultivation"),
    DEXTERITY (6, "dexterity"),
    WISDOM(7, "wisdom");

    public final int index;
    public final String displayName;

    Stats(int index, String name) {
        this.index = index;
        this.displayName = name;
    }
}
