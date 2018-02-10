package com.blamejared.reputed.config;

import com.blamejared.reputed.Reputed;
import com.blamejared.reputed.api.*;
import com.blamejared.reputed.reference.Reference;
import com.google.gson.Gson;

import java.io.File;
import java.net.URL;
import java.util.List;

public class Config {
    
    public static void readConfig(File file) {
        
        System.out.println(">>>" + new Gson().toJson(new Tier("test", 20, 50)));
        if(!file.exists()) {
            copyFromJar(Reputed.class, Reference.MODID + "/jsons/template.json", file);
        }
        JSONParser<Tier> parser = new JSONParser<>(file, Tier.class);
        
        
        List<Tier> list = parser.getElements("tiers");
        for(Tier tier : list) {
            TierRegistry.registerTier(tier);
        }
    }
    
    public static void copyFromJar(Class baseClass, String fileName, File to) {
        URL baseUrl = baseClass.getResource("/assets/" + fileName);
        try {
            org.apache.commons.io.FileUtils.copyURLToFile(baseUrl, to);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
