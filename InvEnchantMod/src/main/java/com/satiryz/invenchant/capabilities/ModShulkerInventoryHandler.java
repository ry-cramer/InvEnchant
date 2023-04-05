package com.satiryz.invenchant.capabilities;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ModShulkerInventoryHandler extends ItemStackHandler implements IItemHandler {

	public ModShulkerInventoryHandler(ItemStack shulkerBox) {
		super(getShulkerBoxInventory(shulkerBox.serializeNBT()));
	}

	public NonNullList<ItemStack> getStacks() {
		return this.stacks;
	}

	public void copyFrom(ModShulkerInventoryHandler source) {
		this.stacks = source.stacks;
	}

	public static NonNullList<ItemStack> getShulkerBoxInventory(CompoundTag nbt) {
		// read nbt data and transfer it to the inventory variable
		System.out.println(nbt);
		ListTag nbtInventoryData = nbt.getCompound("tag").getCompound("BlockEntityTag").getList("Items",
				Tag.TAG_COMPOUND);
		System.out.println("nbtInvData: " + nbtInventoryData);

		NonNullList<ItemStack> inventory = NonNullList.withSize(27, ItemStack.EMPTY);

		for (int i = 0; i < nbtInventoryData.size(); i++) {
			CompoundTag itemTags = nbtInventoryData.getCompound(i);
			int slot = itemTags.getInt("Slot");

			if (slot >= 0 && slot < inventory.size()) {
				inventory.set(slot, ItemStack.of(itemTags));
			}
		}
		System.out.println("Before Initialize: " + inventory);
		return inventory;
	}
}
