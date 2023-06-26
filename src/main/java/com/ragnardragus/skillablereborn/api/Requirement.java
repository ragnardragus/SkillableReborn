package com.ragnardragus.skillablereborn.api;

public class Requirement
{
    public Stats stats;
    public int level;
    
    public Requirement(Stats stats, int level)
    {
        this.stats = stats;
        this.level = level;
    }

    @Override
    public String toString() {
        return stats + ":" + level;
    }
}