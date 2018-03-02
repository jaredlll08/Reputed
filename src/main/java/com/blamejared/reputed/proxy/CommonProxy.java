package com.blamejared.reputed.proxy;

import com.blamejared.reputed.events.CommonEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }
}
