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
				System.out.println("Found available shulker box");
				ModShulkerInventoryHandler shulkerInventory = invStack.getCapability(ModShulkerInventoryHandlerProvider.SHULKER_INVENTORY_HANDLER).resolve().get();
				System.out.println("(Hopefully also) Shulker Box Inventory: " + shulkerInventory);

				break;
			}
		}
	}
}