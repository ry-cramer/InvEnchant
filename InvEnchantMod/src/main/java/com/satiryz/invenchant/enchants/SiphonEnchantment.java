package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModPlayerInventoryHandler;
import com.satiryz.invenchant.capabilities.ModPlayerInventoryHandlerProvider;
import com.satiryz.invenchant.capabilities.ModShulkerInventoryHandler;
import com.satiryz.invenchant.capabilities.ModShulkerInventoryHandlerProvider;
import com.satiryz.invenchant.init.EnchantmentInit;
import com.satiryz.invenchant.tags.ShulkerLikeTag;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.items.ItemHandlerHelper;

public class SiphonEnchantment extends Enchantment {

	public SiphonEnchantment() {
		super(Rarity.RARE, EnchantmentInit.SHULKER_LIKE, EquipmentSlot.values());
		this.descriptionId = "enchantment." + InvEnchant.MODID + ".siphon";
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return ShulkerLikeTag.isShulkerLike(stack);
	}

	public static void onItemPickup(ItemPickupEvent event) {
		if (event.isCanceled() || event.getResult() == Result.ALLOW) {
		      return;
		    }
		
		Player player = event.getEntity();
		ItemStack pickedUpStack = event.getStack();

		if (!pickedUpStack.isStackable()) {
			return;
		}

		System.out.println("Picked up stack: " + pickedUpStack);

		ModPlayerInventoryHandler playerInventory = player.getCapability(ModPlayerInventoryHandlerProvider.PLAYER_INVENTORY_HANDLER).resolve().get();
		System.out.println("Inventory: " + playerInventory.getStacks());

		for (ItemStack invStack : playerInventory.getStacks()) {
			if (invStack.getEnchantmentLevel(EnchantmentInit.SIPHON_ENCHANT) > 0) {
				
				ModShulkerInventoryHandler shulkerInventory = invStack.getCapability(ModShulkerInventoryHandlerProvider.SHULKER_INVENTORY_HANDLER).resolve().get();
				System.out.println("Shulker Box Slots: " + shulkerInventory.getSlots());
				System.out.println("Shulker Box Inventory: " + shulkerInventory.getStacks());
				//if has valid slot, insert stack into slot
				for (int shulkerSlot = 0; shulkerSlot < shulkerInventory.getSlots(); shulkerSlot++) {
					if (isAvailableSlot(pickedUpStack, shulkerInventory.getStackInSlot(shulkerSlot))) {
						System.out.println("Found available shulker box");
						pickedUpStack = insertIntoSlot(pickedUpStack, shulkerSlot, shulkerInventory);
						System.out.println("Stack: " + pickedUpStack);
					}
					if (invStack.isEmpty()) {
						System.out.println("Item Stack Empty");
						break;
					}
				}
				break;
			}
		}
	}
	
	public static boolean isAvailableSlot(ItemStack stack, ItemStack inventoryStack) {
		if (ItemHandlerHelper.canItemStacksStackRelaxed(inventoryStack, stack)) {
			return true;
		}
		return false;
	}
	
	public static ItemStack insertIntoSlot(ItemStack stack, int slot, ModShulkerInventoryHandler shulkerInventory) {
		return shulkerInventory.insertItem(slot, stack, false);
	}
}