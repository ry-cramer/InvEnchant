package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModPlayerInventoryHandler;
import com.satiryz.invenchant.capabilities.ModPlayerInventoryHandlerProvider;
import com.satiryz.invenchant.capabilities.ModShulkerInventoryHandler;
import com.satiryz.invenchant.capabilities.ModShulkerInventoryHandlerProvider;
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

		System.out.println("Picked up stack: " + pickedUpStack);

		ModPlayerInventoryHandler playerInventory = player
				.getCapability(ModPlayerInventoryHandlerProvider.PLAYER_INVENTORY_HANDLER).resolve().get();
		System.out.println("Inventory: " + playerInventory.getStacks());

		// for item stack in player inventory
		for (ItemStack invStack : playerInventory.getStacks()) {
			// if item stack is a Siphon shulker box
			if (invStack.getEnchantmentLevel(EnchantmentInit.SIPHON_ENCHANT) > 0) {
				ModShulkerInventoryHandler shulkerInventory = invStack
						.getCapability(ModShulkerInventoryHandlerProvider.SHULKER_INVENTORY_HANDLER).resolve()
						.get();
				System.out.println("Shulker Box Inventory: " + shulkerInventory.getStacks());
				System.out.println("Shulker Box Slots: " + shulkerInventory.getSlots());
				// for slot in shulker box
				for (int shulkerSlot = 0; shulkerSlot < shulkerInventory.getSlots(); shulkerSlot++) {
					// if there is an available slot (matching item that isn't full
					if (isAvailableSlot(pickedUpStack, shulkerInventory.getStackInSlot(shulkerSlot))) {
						System.out.println("Found available shulker box");
						// copy picked up stack
						pickedUpStack = pickedUpStack.copy();
						// insert the stack into the shulker box. Return the leftover stack
						pickedUpStack = insertIntoSlot(pickedUpStack, shulkerSlot, shulkerInventory);
						System.out.println("Stack: " + pickedUpStack);
						// if there is no more stack, leave loops
						if (pickedUpStack.isEmpty()) {
							System.out.println("Item Stack Empty");
							break;
						}
					}
				}
				System.out.println(shulkerInventory.getStacks());

				// if there is no more stack, leave loops
				System.out.println(pickedUpStack);
				if (pickedUpStack.isEmpty()) {
					break;
				}
			}
		}
		// if there is still more stack after trying shulker boxes, insert the rest of
		// the items as normal
		if (!pickedUpStack.isEmpty()) {
			pickedUpStack = ItemHandlerHelper.insertItemStacked(playerInventory, pickedUpStack, false);
			System.out.println("New Player Inventory" + playerInventory.getStacks());
			System.out.println(pickedUpStack);
		}

		int totalPickedUp = itemEntity.getItem().getCount() - pickedUpStack.getCount();

		if (totalPickedUp > 0) {
			event.setCanceled(true);
			itemEntity.getItem().setCount(pickedUpStack.getCount());
			player.getInventory().setChanged();

			((ServerPlayer) player).connection
					.send(new ClientboundTakeItemEntityPacket(event.getItem().getId(), player.getId(), totalPickedUp));
			player.containerMenu.broadcastChanges();
			System.out.println("New Player Inventory" + playerInventory.getStacks());
		}
		
	}

	public static boolean isAvailableSlot(ItemStack stack, ItemStack inventoryStack) {
		return (ItemHandlerHelper.canItemStacksStackRelaxed(stack, inventoryStack)
				&& inventoryStack.getMaxStackSize() > inventoryStack.getCount());
	}

	public static ItemStack insertIntoSlot(ItemStack stack, int slot, ModShulkerInventoryHandler shulkerInventory) {
		return shulkerInventory.insertItem(slot, stack, false);
	}
}