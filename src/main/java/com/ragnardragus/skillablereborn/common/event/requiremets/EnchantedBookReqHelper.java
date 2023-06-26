package com.ragnardragus.skillablereborn.common.event.requiremets;


import com.ragnardragus.skillablereborn.api.Requirement;
import com.ragnardragus.skillablereborn.api.Stats;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public class EnchantedBookReqHelper {

    public static Requirement getRequirementForEnchantingLeve(Map<Enchantment, Integer> map) {
        Requirement requirement = null;

        // TODO Add values to new Config or Json
        for (Map.Entry<Enchantment, Integer> element : map.entrySet()) {
            switch (element.getValue()) { // Enchantment Level
                case (2):
                    requirement = new Requirement(Stats.WISDOM, 4);
                    break;
                case (3):
                    requirement = new Requirement(Stats.WISDOM, 8);
                    break;
                case (4):
                    requirement = new Requirement(Stats.WISDOM, 16);
                    break;
                case (5):
                    requirement = new Requirement(Stats.WISDOM, 32);
                    break;
            }
            if(element.getValue() > 5) {
                requirement = new Requirement(Stats.WISDOM, 32);
            }
        }
        return requirement;
    }
}
