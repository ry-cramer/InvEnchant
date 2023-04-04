package com.satiryz.invenchant.capabilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class ModShulkerInventoryHandler implements IItemHandler {

	private NonNullList<ItemStack> stacks;

	public ModShulkerInventoryHandler() {
		this(27);
	}

	public ModShulkerInventoryHandler(int size) {
		this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	public ModShulkerInventoryHandler(ItemStack shulkerBox) {
		this.stacks = getShulkerBoxInventory(shulkerBox.serializeNBT());
		System.out.println(stacks.size());
	}

	public void setSize(int size)
    {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }
	
	@Override
	public int getSlots() {
		return stacks.size();
	}

	public NonNullList<ItemStack> getStacks() {
		return this.stacks;
	}

	@Override
	@NotNull
	public ItemStack getStackInSlot(int slot) {
		validateSlotIndex(slot);
		return this.stacks.get(slot);
	}

	@Override
	@NotNull
	public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		if (!isItemValid(slot, stack))
			return stack;

		validateSlotIndex(slot);

		ItemStack existing = this.stacks.get(slot);

		int limit = getStackLimit(slot, stack);

		if (!existing.isEmpty()) {
			if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
				return stack;

			limit -= existing.getCount();
		}

		if (limit <= 0)
			return stack;

		boolean reachedLimit = stack.getCount() > limit;

		if (!simulate) {
			if (existing.isEmpty()) {
				this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
			} else {
				existing.grow(reachedLimit ? limit : stack.getCount());
			}
			onContentsChanged(slot);
		}

		return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
	}

	@Override
    @NotNull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                this.stacks.set(slot, ItemStack.EMPTY);
                onContentsChanged(slot);
                return existing;
            }
            else
            {
                return existing.copy();
            }
        }
        else
        {
            if (!simulate)
            {
                this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		return true;
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
		return inventory;
	}

	protected void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= stacks.size())
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
	}

	protected int getStackLimit(int slot, @NotNull ItemStack stack) {
		return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
	}

	protected void onContentsChanged(int slot) {

	}
	
	public CompoundTag saveNBTData(CompoundTag nbt) {
		ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (!this.stacks.get(i).isEmpty())
            {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                this.stacks.get(i).save(itemTag);
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
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, ItemStack.of(itemTags));
            }
        }
        onLoad();
    }
    
	protected void onLoad() {

	}
}
