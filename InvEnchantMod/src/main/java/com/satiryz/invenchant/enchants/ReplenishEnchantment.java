package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModInventoryHandler;
import com.satiryz.invenchant.capabilities.ModInventoryHandlerProvider;
import com.satiryz.invenchant.init.EnchantmentInit;
import com.satiryz.invenchant.tags.ShulkerLikeTag;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ReplenishEnchantment extends Enchantment {

	public ReplenishEnchantment() {
		super(Rarity.RARE, EnchantmentInit.SHULKER_LIKE, EquipmentSlot.values());
		this.descriptionId = "enchantment." + InvEnchant.MODID + ".replenish";
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return ShulkerLikeTag.isShulkerLike(stack);
	}

	public static void attemptRefill(Player player, ItemStack placedStack, int amountToFill, int slotPlacedFrom) {
		ModInventoryHandler playerInventory = player.getCapability(ModInventoryHandlerProvider.INVENTORY_HANDLER)
				.resolve().get();
		for (ItemStack invStack : playerInventory.getStacks()) {
			if (invStack.isEmpty()) {
				continue;
			}
			// if item stack is a Replenish shulker box
			if (invStack.getEnchantmentLevel(EnchantmentInit.REPLENISH_ENCHANT) > 0) {
				ModInventoryHandler shulkerInventory = invStack
						.getCapability(ModInventoryHandlerProvider.INVENTORY_HANDLER).resolve().get();
				// for slot in shulker box
				for (int shulkerSlot = 0; shulkerSlot < shulkerInventory.getSlots(); shulkerSlot++) {
					// If placedStack matches shulker stack
					if (isMatchingItem(placedStack, shulkerInventory.getStackInSlot(shulkerSlot))) {
						// refill stack and return
						refillStack(playerInventory, shulkerInventory, shulkerSlot, amountToFill, slotPlacedFrom);
						return;
					}
				}
			}
		}
	}
	
	public static boolean isMatchingItem(ItemStack stackToRefill, ItemStack shulkerStack) {
		return stackToRefill.getItem() == shulkerStack.getItem();
	}
	
	public static void refillStack(ModInventoryHandler playerInventory, ModInventoryHandler shulkerInventory, int slotToTakeFrom, int amountToFill, int slotToFill) {
		 System.out.println("Refill function reached");
		 ItemStack refillStack = shulkerInventory.extractItem(slotToTakeFrom, amountToFill, false);
		 
		 playerInventory.insertItem(slotToFill, refillStack, false);
		 System.out.println(playerInventory.serializeNBT());
	}
}
