package com.ragnardragus.skillablereborn.api;

import net.minecraft.world.entity.npc.VillagerProfession;

public enum Jobs {
    NONE(0, "none", 100),
    ARMORER(1, "armorer", 100),
    BUTCHER(2, "butcher", 100),
    CARTOGRAPHER (3, "cartographer", 100),
    CLERIC(4, "cleric", 100),
    FARMER(5,"farmer", 100),
    FISHERMAN(6, "fisherman", 100),
    FLETCHER(7, "fletcher", 100),
    LEATHERWORKER(8, "leatherworker", 100),
    LIBRARIAN(9, "librarian", 100),
    MASON(10, "mason", 100),
    SHEPHERD(11, "shepherd", 100),
    TOOLSMITH(12, "toolsmith", 100),
    WEAPONSMITH(13, "weaponsmith", 100);

    public final int index;

    public final String displayName;
    public final int startObjective;

    Jobs(int index, String name, int startObjective) {
        this.index = index;
        this.displayName = name;
        this.startObjective = startObjective;
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
