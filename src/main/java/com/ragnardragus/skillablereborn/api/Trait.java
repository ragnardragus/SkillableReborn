package com.ragnardragus.skillablereborn.api;

public enum Trait {

    VEIN_MINING(0, "vein_mine", new Requirement[] {
            new Requirement(Stats.STRENGTH, 10),
            new Requirement(Stats.SURVIVAL, 8)
    }, 176, 0), // 10 str, 8 sur
    DASH(1, "dash", new Requirement[] {
            new Requirement(Stats.DEXTERITY, 16)
    }, 176, 20), // 16 dex
    HI_JUMP(2, "hi_jump", new Requirement[] {
            new Requirement(Stats.STRENGTH, 12),
            new Requirement(Stats.DEXTERITY, 4)
    }, 176, 20), // 12 str, 4 dex
    STEP(3, "step", new Requirement[] {
            new Requirement(Stats.DEXTERITY, 12)
    }, 176, 20), // 12 dex
    REINFORCE(4, "reinforce",new Requirement[] {
            new Requirement(Stats.TENACITY, 10),
            new Requirement(Stats.STRENGTH, 6),
            new Requirement(Stats.COMBAT, 5),
    }, 176, 20), // every 4 damage taken 1 damage less  - 13 ten, 6 str, 5 com
    BRUTAL(5, "brutal",new Requirement[] {
            new Requirement(Stats.COMBAT, 8),
            new Requirement(Stats.STRENGTH, 15)
    }, 176, 20), // more damage dealt by % - 8 com, 15 str
    FIRE_FRIENDLY(6, "fire_friendly", new Requirement[] {
            new Requirement(Stats.TENACITY, 18)
    }, 176, 20), // 75% of thunder and burn damage is ignored if player has 3 hearts or fewer - 18 ten
    STEEL_LEGS(7, "steel_legs", new Requirement[] {
            new Requirement(Stats.DEXTERITY, 8),
            new Requirement(Stats.TENACITY, 8)
    }, 176, 20), // immune to fall damage to up 6 blocks - 8 dex, 8 ten
    PLAGUE_RESISTANCE(8, "plague_resistance", new Requirement[] {
            new Requirement(Stats.CULTIVATION, 6),
            new Requirement(Stats.TENACITY, 18)
    }, 176, 20), // wither and poison resistance - 18 ten, 6 cul
    RANGER(9, "ranger", new Requirement[] {
            new Requirement(Stats.DEXTERITY, 10),
            new Requirement(Stats.SURVIVAL, 8)
    }, 176, 20), // more bow damage - 10 dex, 8 sur
    RESONANCE(10, "resonance", new Requirement[] {
            new Requirement(Stats.WISDOM, 8)
    }, 176, 20), // attract items to you by hold crouch key - wid 12
    EMERGENCY_SUPPLIES(11, "emergency_supplies", new Requirement[] {
            new Requirement(Stats.SURVIVAL, 18)
    }, 176, 20), // potion effect saturation by 5 seconds if hearts is below 4 - 18 sur
    SPIKE_SHIELD(12, "spike_shield", new Requirement[] {
            new Requirement(Stats.TENACITY, 8),
            new Requirement(Stats.COMBAT, 10),
            new Requirement(Stats.STRENGTH, 8)
    }, 176, 20), // blocking with shield deal damage to attackers - 8 ten, 10 com, 8 str
    FLORA_AFFINITY(13, "flora_affinity", new Requirement[] {
            new Requirement(Stats.CULTIVATION, 13)
    }, 176, 20), // crouch nearby plants can make grow them faster - 13 cul
    DIVINE_HANDS(14, "divine_hands", new Requirement[] {
            new Requirement(Stats.CRAFTING, 10),
            new Requirement(Stats.WISDOM, 8)
    }, 176, 20) // on craft a weapon or equipment, has a small chance to get a random enchantment - 10 cra, 8 wid
    ;

    public final int index;
    public final String displayName;

    public final Requirement[] requirements;

    public final int imgXPos;
    public final int imgYPos;

    Trait(int index, String name, Requirement[] requirements, int x, int y) {
        this.index = index;
        this.displayName = name;
        this.requirements = requirements;
        this.imgXPos = x;
        this.imgYPos = y;
    }
}
