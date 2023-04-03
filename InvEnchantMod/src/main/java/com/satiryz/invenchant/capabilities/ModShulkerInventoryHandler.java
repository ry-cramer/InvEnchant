package com.satiryz.invenchant.capabilities;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@AutoRegisterCapability
public class ModShulkerInventoryHandler extends ItemStackHandler implements IItemHandler {

	private NonNullList<ItemStack> stacks;

	public ModShulkerInventoryHandler() {
		this(27);
	}

	public ModShulkerInventoryHandler(int size) {
		this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}
	
	public ModShulkerInventoryHandler(ItemStack shulkerBox) {
		NonNullList<ItemStack> shulkerInventoryData = getShulkerBoxInventory(shulkerBox.serializeNBT());
		this.stacks = shulkerInventoryData;
	}

	public NonNullList<ItemStack> getStacks() {
		return this.stacks;
	}

	public void copyFrom(ModShulkerInventoryHandler source) {
		this.stacks = source.stacks;
	}

	public CompoundTag saveNBTData(CompoundTag nbt) {
		ListTag nbtTagList = new ListTag();
		for (int i = 0; i < stacks.size(); i++) {
			if (!stacks.get(i).isEmpty()) {
				CompoundTag itemTag = new CompoundTag();
				itemTag.putInt("Slot", i);
				stacks.get(i).save(itemTag);
				nbtTagList.add(itemTag);
			}
		}
		nbt.put("Items", nbtTagList);
		nbt.putInt("Size", stacks.size());
		return nbt;
	}

	public void loadNBTData(CompoundTag nbt) {
		setSize(nbt.contains("Size", Tag.TAG_INT) ? nbt.getInt("Size") : stacks.size());
		ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
		for (int i = 0; i < tagList.size(); i++) {
			CompoundTag itemTags = tagList.getCompound(i);
			int slot = itemTags.getInt("Slot");

			if (slot >= 0 && slot < stacks.size()) {
				stacks.set(slot, ItemStack.of(itemTags));
			}
		}
		onLoad();
	}
	
	public static NonNullList<ItemStack> getShulkerBoxInventory(CompoundTag nbt) {
        //read nbt data and transfer it to the inventory variable
		System.out.println(nbt);
		ListTag nbtInventoryData = nbt.getCompound("tag").getCompound("BlockEntityTag").getList("Items", Tag.TAG_COMPOUND);
        System.out.println("nbtInvData: " + nbtInventoryData);
        
        NonNullList<ItemStack> inventory = NonNullList.withSize(27, ItemStack.EMPTY);
        
        for(int i = 0; i < nbtInventoryData.size(); i++) {
        	CompoundTag itemTags = nbtInventoryData.getCompound(i);
        	int slot = itemTags.getInt("Slot");
        	
        	if (slot >= 0 && slot < inventory.size()) {
        		inventory.set(slot, ItemStack.of(itemTags));
        	}
        }
        
        return inventory;
    }
}

