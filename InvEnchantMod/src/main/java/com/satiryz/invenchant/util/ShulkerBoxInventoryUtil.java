package com.satiryz.invenchant.util;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxInventoryUtil {
	public static NonNullList<ItemStack> getShulkerBoxInventory(CompoundTag nbt) {
		//read nbt data and transfer it to the inventory variable
		ListTag nbtInventoryData = nbt.getList("BlockEntityTag", Tag.TAG_COMPOUND);
		System.out.println("nbtInvData: " + nbtInventoryData);
		NonNullList<ItemStack> inventory = NonNullList.withSize(27, null);
		
		return inventory;
	}
}
