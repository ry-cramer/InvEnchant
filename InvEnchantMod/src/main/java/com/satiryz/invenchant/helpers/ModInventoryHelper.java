package com.satiryz.invenchant.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import com.satiryz.invenchant.capabilities.ModItemHandler;
import com.satiryz.invenchant.capabilities.ModItemHandlerProvider;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// Keeping this because it may be useful later, but currently unused class
public class ModInventoryHelper {
	
	public static class EmptyIterator<T> implements Iterator<T> {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public T next() {
			throw new RuntimeException("Iterator is empty");
		}
	}

	public static class ModItemHandlerIterator implements Iterator<ItemStack> {
		private final ModItemHandler itemHandler;
		private int nextIndex = 0;

		public ModItemHandlerIterator(ModItemHandler itemHandler) {
			this.itemHandler = itemHandler;
		}

		@Override
		public boolean hasNext() {
			return this.nextIndex < this.itemHandler.getSlots();
		}

		@Override
		public ItemStack next() {
			return this.itemHandler.getStackInSlot(nextIndex++);
		}
	}

	public static class ModItemHandlerIterable implements Iterable<ItemStack> {
		private final ModItemHandler itemHandler;

		public ModItemHandlerIterable(ModItemHandler itemHandler) {
			this.itemHandler = itemHandler;
		}

		@Override
		public Iterator<ItemStack> iterator() {
			return new ModItemHandlerIterator(this.itemHandler);
		}
	}

	public static Iterable<ItemStack> getInventoryIterator(LivingEntity entity) {
		Iterable<ItemStack> inventoryIterator = null;

		Optional<ModItemHandler> itemHandlerCapability = entity.getCapability(ModItemHandlerProvider.ITEM_HANDLER)
				.resolve();
		if (itemHandlerCapability.isPresent()) {
			inventoryIterator = new ModItemHandlerIterable(itemHandlerCapability.get());
		}

		if (inventoryIterator == null) {
			return new ArrayList<>();
		}

		return inventoryIterator;
	}
}
