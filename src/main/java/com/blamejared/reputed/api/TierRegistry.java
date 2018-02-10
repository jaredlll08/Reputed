package com.blamejared.reputed.api;

import java.util.*;

public class TierRegistry {
    
    
    private static final List<Tier> tiers = new ArrayList<>();
    
    
    public static void registerTier(Tier tier) {
        tiers.add(tier);
    }
    
    public static void removeTier(Tier tier) {
        tiers.remove(tier);
    }
    
    public static Tier getTierByName(String name) {
        for(Tier tier : getTiers()) {
            if(tier.name.equalsIgnoreCase(name)) {
                return tier;
            }
        }
        return null;
    }
    
    public static Tier getTierByKills(int kills) {
        for(Tier tier : getTiers()) {
            if(kills >= tier.getMinKills()) {
                if(kills <= tier.getMaxKills()) {
                    return tier;
                }
            }
        }
        return null;
    }
    
    public static List<Tier> getTiers() {
        return tiers;
    }
}
