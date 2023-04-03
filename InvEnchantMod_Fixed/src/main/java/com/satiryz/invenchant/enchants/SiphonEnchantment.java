package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.DevTools;
import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModItemHandler;
import com.satiryz.invenchant.capabilities.ModItemHandlerProvider;
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
		
		if(!pickedUpStack.isStackable()) {
			return;
		}

		DevTools.debugMessage(("Picked up stack: " + pickedUpStack), player);

		ModItemHandler playerInventory = player.getCapability(ModItemHandlerProvider.ITEM_HANDLER).resolve().get();
		DevTools.debugMessage(("Inventory: " + playerInventory.getStacks()), player);
		
		for (ItemStack invStack : playerInventory.getStacks()) {
			if (invStack.getEnchantmentLevel(EnchantmentInit.SIPHON_ENCHANT) > 0) {
				DevTools.debugMessage("Found available shulker box", player);
				break;
			}
		}
	}
}