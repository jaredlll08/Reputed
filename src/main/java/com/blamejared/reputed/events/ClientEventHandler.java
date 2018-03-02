package com.blamejared.reputed.events;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(!stack.hasTagCompound() || !stack.getTagCompound().getBoolean("reputed")) {
            return;
        }
        
        NBTTagCompound tag = stack.getTagCompound();
        int kills = tag.getInteger("kills");
        event.getToolTip().add("Kills: " + kills);
    }
}
