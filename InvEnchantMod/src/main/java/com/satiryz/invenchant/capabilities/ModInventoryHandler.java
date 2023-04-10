package com.satiryz.invenchant.capabilities;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ModInventoryHandler extends ItemStackHandler implements IItemHandler {

	public ItemStack shulkerBox = null;

	public ModInventoryHandler(ItemStack shulkerBox) {
		super(getShulkerBoxInventory(shulkerBox.serializeNBT()));
		this.shulkerBox = shulkerBox;
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

	@Override
	public CompoundTag serializeNBT() {
		// we override the parent serializeNBT because we need to merge the updated
		// "Items" property with
		// other properties the itemStack has, such as Enchantments
		if (shulkerBox == null) {
			CompoundTag nbt = super.serializeNBT();
			return nbt;
		}
		CompoundTag nbt = shulkerBox.getTagElement("BlockEntityTag");
		if (nbt == null) {
			nbt = new CompoundTag();
		}

		ContainerHelper.saveAllItems(nbt, this.stacks, true);

		return nbt;
	}

	protected void onContentsChanged(int slot) {
		if (shulkerBox == null) {
			return;
		}
		CompoundTag inventoryNbt = this.serializeNBT();

		CompoundTag nbt = shulkerBox.getTag();
		if (nbt == null) {
			nbt = new CompoundTag();
		}

		nbt.put("BlockEntityTag", inventoryNbt);
		shulkerBox.setTag(nbt);
	}
}
