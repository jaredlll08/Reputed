package com.blamejared.reputed.proxy;

import com.blamejared.reputed.events.*;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    
    
    @Override
    public void registerEvents() {
        super.registerEvents();
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}
