package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.DevTools;
import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.Tags;
import com.satiryz.invenchant.helpers.ModInventoryHelper;
import com.satiryz.invenchant.init.EnchantmentInit;

import net.minecraft.network.chat.Component;
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

		if (DevTools.debug = true) {
			player.sendSystemMessage(Component.literal("Inventory: " + player.getInventory().items));
			for(ItemStack invItem: ModInventoryHelper.getInventoryIterator(player))
			player.sendSystemMessage(Component.literal("Also (possibly) inventory?: " + invItem));
			player.sendSystemMessage(Component.literal("Picked up stack: " + pickedUpStack));
		}
	}
}
