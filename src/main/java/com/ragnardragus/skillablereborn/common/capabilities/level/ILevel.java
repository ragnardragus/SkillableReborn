package com.ragnardragus.skillablereborn.common.capabilities.level;

import net.minecraft.server.level.ServerPlayer;

public interface ILevel {


    int getSkillPoints();
    void setSkillPoints(int skillPoint);

    void addSkillPoints();

    void subtractSkillPoints();

    int getMcLevelsNeed();
    void setMcLevelsNeed(int level);

    int getPlayerModLevel();
    void setPlayerModLevel(int level);
    void addPlayerModLevel();

    void sync(ServerPlayer player);
}
