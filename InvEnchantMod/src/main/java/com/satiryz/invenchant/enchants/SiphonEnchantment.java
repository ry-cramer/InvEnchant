package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModInventoryHandler;
import com.satiryz.invenchant.capabilities.ModInventoryHandlerProvider;
import com.satiryz.invenchant.init.EnchantmentInit;
import com.satiryz.invenchant.tags.ShulkerLikeTag;

import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
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

	public static void onItemPickup(EntityItemPickupEvent event) {
		if (event.isCanceled() || event.getResult() == Result.ALLOW) {
			return;
		}

		ItemEntity itemEntity = event.getItem();
		Player player = event.getEntity();
		ItemStack pickedUpStack = event.getItem().getItem();

		if (!pickedUpStack.isStackable()) {
			return;
		}

		ModInventoryHandler playerInventory = player
				.getCapability(ModInventoryHandlerProvider.INVENTORY_HANDLER).resolve().get();

		// for item stack in player inventory
		for (ItemStack invStack : playerInventory.getStacks()) {
			// if item stack is a Siphon shulker box
			if (invStack.getEnchantmentLevel(EnchantmentInit.SIPHON_ENCHANT) > 0) {
				ModInventoryHandler shulkerInventory = invStack
						.getCapability(ModInventoryHandlerProvider.INVENTORY_HANDLER).resolve().get();
				// for slot in shulker box
				for (int shulkerSlot = 0; shulkerSlot < shulkerInventory.getSlots(); shulkerSlot++) {
					// if there is an available slot (matching item that isn't full
					if (isAvailableSlot(pickedUpStack, shulkerInventory.getStackInSlot(shulkerSlot))) {
						// copy picked up stack
						pickedUpStack = pickedUpStack.copy();
						// insert the stack into the shulker box. Return the leftover stack
						pickedUpStack = insertIntoSlot(pickedUpStack, shulkerSlot, shulkerInventory);
						// if there is no more stack, leave loops
						if (pickedUpStack.isEmpty()) {
							break;
						}
					}
				}
				// if there is no more stack, leave loops
				if (pickedUpStack.isEmpty()) {
					break;
				}
				System.out.println("New Shulker Inventory: " + shulkerInventory.getStacks());
			}
		}
		// if there is still more stack after trying shulker boxes, insert the rest of
		// the items as normal
		if (!pickedUpStack.isEmpty()) {
			pickedUpStack = ItemHandlerHelper.insertItemStacked(playerInventory, pickedUpStack, false);
			System.out.println("PickedUpStack: " + pickedUpStack.getCount());
			System.out.println("PickedUpStack Empty: " + pickedUpStack.isEmpty());
			System.out.println("Player Inventory: " + playerInventory.getStacks());
		}

		int totalPickedUp = itemEntity.getItem().getCount() - pickedUpStack.getCount();
		System.out.println("Line 83: " + playerInventory.getStacks());

		if (totalPickedUp > 0) {
			event.setCanceled(true);
			System.out.println("Line 87: " + playerInventory.getStacks());
			itemEntity.getItem().setCount(pickedUpStack.getCount());
			System.out.println("Line 89: " + playerInventory.getStacks());
			player.getInventory().setChanged();
			System.out.println("Line 91: " + playerInventory.getStacks());
			
			((ServerPlayer) player).connection
					.send(new ClientboundTakeItemEntityPacket(event.getItem().getId(), player.getId(), totalPickedUp));
			System.out.println("Line 95: " + playerInventory.getStacks());
			player.containerMenu.broadcastChanges();
			System.out.println("Line 97: " + playerInventory.getStacks());
		}
	}

	public static boolean isAvailableSlot(ItemStack stack, ItemStack inventoryStack) {
		return (ItemHandlerHelper.canItemStacksStackRelaxed(stack, inventoryStack)
				&& inventoryStack.getMaxStackSize() > inventoryStack.getCount());
	}

	public static ItemStack insertIntoSlot(ItemStack stack, int slot, ModInventoryHandler shulkerInventory) {
		return shulkerInventory.insertItem(slot, stack, false);
	}
}