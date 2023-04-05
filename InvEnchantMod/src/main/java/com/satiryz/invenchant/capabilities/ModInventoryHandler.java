package com.satiryz.invenchant.capabilities;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ModInventoryHandler extends ItemStackHandler implements IItemHandler {

	public ModInventoryHandler(ItemStack shulkerBox) {
		super(getShulkerBoxInventory(shulkerBox.serializeNBT()));
	}
	
	public ModInventoryHandler(Player player) {
		super(player.getInventory().items);
	}

	public NonNullList<ItemStack> getStacks() {
		return this.stacks;
	}

	public void copyFrom(ModInventoryHandler source) {
		this.stacks = source.stacks;
	}

	public static NonNullList<ItemStack> getShulkerBoxInventory(CompoundTag nbt) {
		// read nbt data and transfer it to the inventory variable
		ListTag nbtInventoryData = nbt.getCompound("tag").getCompound("BlockEntityTag").getList("Items",
				Tag.TAG_COMPOUND);

		NonNullList<ItemStack> inventory = NonNullList.withSize(27, ItemStack.EMPTY);

		for (int i = 0; i < nbtInventoryData.size(); i++) {
			CompoundTag itemTags = nbtInventoryData.getCompound(i);
			int slot = itemTags.getInt("Slot");

			if (slot >= 0 && slot < inventory.size()) {
				inventory.set(slot, ItemStack.of(itemTags));
			}
		}
		return inventory;
	}
}
