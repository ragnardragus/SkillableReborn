package com.ragnardragus.skillablereborn.common.capabilities.level;

import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.level.LevelsMsg;
import com.ragnardragus.skillablereborn.common.network.level.McLevelsNeedMsg;
import com.ragnardragus.skillablereborn.common.network.level.SkillPointMsg;
import net.minecraft.server.level.ServerPlayer;

public class Level implements ILevel {

    private int skillPoints = 0;
    private int mcLevelsNeed = 1;
    private int playerModLevel = 0;

    @Override
    public int getSkillPoints() {
        return this.skillPoints;
    }

    @Override
    public void setSkillPoints(int skillPoint) {
        if(skillPoint < 0) {
            this.skillPoints = 0;
        } else {
            this.skillPoints = skillPoint;
        }
    }

    @Override
    public void addSkillPoints() {
        setSkillPoints(this.skillPoints + 1);
    }

    @Override
    public void subtractSkillPoints() {
        setSkillPoints(this.skillPoints - 1);
    }

    @Override
    public int getMcLevelsNeed() {
        return this.mcLevelsNeed;
    }

    @Override
    public void setMcLevelsNeed(int level) {
        if(level < 0) {
            this.mcLevelsNeed = 0;
        } else {
            this.mcLevelsNeed = level;
        }
    }

    @Override
    public int getPlayerModLevel() {
        return this.playerModLevel;
    }

    @Override
    public void setPlayerModLevel(int level) {
        if(level < 0) {
            this.playerModLevel = 0;
        } else if(level > 240) {
            this.playerModLevel = 240;
        } else {
            this.playerModLevel = level;
        }
    }

    @Override
    public void addPlayerModLevel() {
        setPlayerModLevel(this.playerModLevel + 1);
    }

    @Override
    public void sync(ServerPlayer player) {
        PacketHandler.sendToPlayer(new SkillPointMsg(this.getSkillPoints()), player);
        PacketHandler.sendToPlayer(new McLevelsNeedMsg(this.getMcLevelsNeed()), player);
        PacketHandler.sendToPlayer(new LevelsMsg(this.getPlayerModLevel()), player);
    }
}
