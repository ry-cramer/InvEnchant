package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.init.EnchantmentInit;
import com.satiryz.invenchant.tags.EnchantmentTags;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ReplenishEnchantment extends Enchantment {

	public ReplenishEnchantment() {
		super(Rarity.RARE, EnchantmentInit.SHULKER_LIKE, EquipmentSlot.values());
		this.descriptionId = "enchantment." + InvEnchant.MODID + ".replenish";
	}
	
	@Override
	  public boolean canEnchant(ItemStack stack) {
	    return EnchantmentTags.isShulkerLike(stack);
	  }
}
