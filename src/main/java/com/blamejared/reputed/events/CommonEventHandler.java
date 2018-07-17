package com.blamejared.reputed.events;

import com.blamejared.reputed.api.TierRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

public class CommonEventHandler {
    
    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        DamageSource source1 = event.getSource();
        Entity source = source1.getTrueSource();
        String type = source1.getDamageType();
        if(source instanceof EntityLivingBase && type.equalsIgnoreCase("player") || type.equalsIgnoreCase("arrow") && source instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase) source;
            ItemStack stack = ent.getHeldItemMainhand();
            if(event.getEntityLiving().getHealth() - event.getAmount() > 0) {
                return;
            }
            if(!stack.isEmpty()) {
                NBTTagCompound tag = stack.getTagCompound();
                if(tag != null && tag.hasKey("reputed")) {
                    int kills = tag.getInteger("kills") + 1;
                    tag.setInteger("kills", kills);
                    String prevName = tag.getString("prevName");
                    String name = TierRegistry.getTierByKills(kills).getName();
                    if(!prevName.equalsIgnoreCase(name)) {
                        stack.setStackDisplayName(TextFormatting.RESET + name + " " + stack.getItem().getItemStackDisplayName(stack));
                        tag.setString("prevName", name);
                    }
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
            String name1 = TierRegistry.getTierByKills(0).getName();
            nbt.setString("prevName", name1);
            output.setTagCompound(nbt);
            output.setStackDisplayName(TextFormatting.RESET + name1 + " " + output.getItem().getItemStackDisplayName(output));
            event.setOutput(output);
            event.setCost(1);
        }
    }
    
}
