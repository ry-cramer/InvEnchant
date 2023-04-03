package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.DevTools;
import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.Tags;
import com.satiryz.invenchant.capabilities.ModItemHandlerProvider;
import com.satiryz.invenchant.init.EnchantmentInit;

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
		return Tags.isShulkerLike(stack);
	}

	public static void onItemPickup(ItemPickupEvent event) {
		Player player = event.getEntity();
		ItemStack pickedUpStack = event.getStack();

		DevTools.debugMessage(("Picked up stack: " + pickedUpStack), player);
		DevTools.debugMessage(("Inventory: " + player.getInventory()), player);
		
		player.getCapability(ModItemHandlerProvider.ITEM_HANDLER).ifPresent(stack -> {
			DevTools.debugMessage(("Also (possibly) inventory?: " + stack.getStacks()), player);
			for (ItemStack invStack : stack.getStacks()) {
				if (Tags.isShulkerLike(invStack)) {
					DevTools.debugMessage("Found available shulker box", player);
					break;
				}
			}
		});
	}
}
