package com.blamejared.reputed;

import com.blamejared.reputed.config.Config;
import com.blamejared.reputed.proxy.CommonProxy;
import com.blamejared.reputed.reference.Reference;
import net.minecraft.block.BlockAnvil;
import net.minecraft.inventory.ContainerRepair;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Reputed {
    
    @Mod.Instance(Reference.MODID)
    public static Reputed INSTANCE;
    
    @SidedProxy(clientSide = "com.blamejared.reputed.proxy.ClientProxy", serverSide = "com.blamejared.reputed.proxy.CommonProxy")
    public static CommonProxy PROXY;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        PROXY.registerEvents();
        Config.readConfig(new File(e.getModConfigurationDirectory(), Reference.NAME + ".json"));
    }
    
    
}
