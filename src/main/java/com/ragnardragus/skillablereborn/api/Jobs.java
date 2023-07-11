package com.ragnardragus.skillablereborn.api;

import net.minecraft.world.entity.npc.VillagerProfession;

import java.util.HashMap;

public enum Jobs {
    NONE(0, "none", 100, null),
    ARMORER(1, "armorer", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    BUTCHER(2, "butcher", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    CARTOGRAPHER (3, "cartographer", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    CLERIC(4, "cleric", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    FARMER(5,"farmer", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    FISHERMAN(6, "fisherman", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    FLETCHER(7, "fletcher", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    LEATHERWORKER(8, "leatherworker", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    LIBRARIAN(9, "librarian", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    MASON(10, "mason", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),

    TOOLSMITH(11, "toolsmith", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }}),
    WEAPONSMITH(12, "weaponsmith", 100, new HashMap<>(){{
        put("", 1);
        put("", 2);
    }});

    public final int index;

    public final String displayName;
    public final int startObjective;


    public final HashMap<String, Integer> rewards;

    Jobs(int index, String name, int startObjective, HashMap<String, Integer> rewards) {
        this.index = index;
        this.displayName = name;
        this.startObjective = startObjective;
        this.rewards = rewards;
    }

    public static Jobs getJobByDisplayName(String displayName) {
        for (Jobs job : Jobs.values()) {
            if (job.displayName.equals(displayName)) {
                return job;
            }
        }
        return NONE;
    }

    public static Jobs getJobByIndex(int index) {
        for (Jobs job : Jobs.values()) {
            if (job.index == index) {
                return job;
            }
        }
        return NONE;
    }

    public static Jobs parseToJobs(VillagerProfession villagerProfession) {
        if(villagerProfession != null) {
            String professionName = villagerProfession.getRegistryName().toString().split(":")[1];
            return getJobByDisplayName(professionName);
        }
        return NONE;
    }
}
