package com.blamejared.reputed.api;

public class Tier {
    
    public String name;
    public int minKills;
    public int maxKills;
    
    public Tier(String name, int minKills, int maxKills) {
        this.name = name;
        this.minKills = minKills;
        this.maxKills = maxKills;
    }
    
    public String getName() {
        return name;
    }
    public int getMinKills() {
        return Math.min(minKills, maxKills);
    }
    
    public int getMaxKills() {
        return Math.max(maxKills, minKills);
    }
    
}
