package com.blamejared.reputed.events;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;

import java.util.Collection;

public class CommonEventHandler {
    
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        Entity source = event.getSource().getTrueSource();
        if(source instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase) source;
            ItemStack stack = ent.getHeldItemMainhand();
            if(!stack.isEmpty()) {
                NBTTagCompound tag = stack.getTagCompound();
                if(tag != null && tag.hasKey("reputed")) {
                    tag.setInteger("kills", tag.getInteger("kills") + 1);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if(event.getRight().getItem() != Items.WRITABLE_BOOK) {
            return;
        }
        String name = SharedMonsterAttributes.ATTACK_DAMAGE.getName();
        ItemStack left = event.getLeft();
        Collection<AttributeModifier> attributeModifiers = left.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, left).get(name);
        int attack = 0;
        for(AttributeModifier modifier : attributeModifiers) {
            if(modifier.getOperation() == 0)
                attack += modifier.getAmount();
        }
        if(attack > SharedMonsterAttributes.ATTACK_DAMAGE.getDefaultValue() || left.getItem() instanceof ItemBow) {
            ItemStack output = left.copy();
            NBTTagCompound nbt = new NBTTagCompound();
            if(output.hasTagCompound()) {
                nbt = output.getTagCompound();
            }
            nbt.setBoolean("reputed", true);
            nbt.setInteger("kills", 0);
            output.setTagCompound(nbt);
            output.setStackDisplayName("Reputed " + output.getDisplayName());
            event.setOutput(output);
            event.setCost(1);
        }
    }
    
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
