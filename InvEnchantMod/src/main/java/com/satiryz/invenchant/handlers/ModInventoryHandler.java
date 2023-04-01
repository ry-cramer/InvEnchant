package com.satiryz.invenchant.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ModInventoryHandler extends ItemStackHandler implements IItemHandler{
	
	private NonNullList<ItemStack> stacks;
	
	public ModInventoryHandler() {
		this(36);
	}
	
	public ModInventoryHandler(int size) {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }
	
	public ModInventoryHandler(ServerPlayer player) {
		this.stacks = player.getInventory().items;
	}
		
	public NonNullList<ItemStack> getStacks(){
		return this.stacks;
	}
	
	public void copyFrom(ModInventoryHandler source) {
		this.stacks = source.stacks;
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt("inventory", getSlots());
	}
	
	public void loadNBTData(CompoundTag nbt) {
		this.setSize(nbt.getInt("inventory"));
	}
}
