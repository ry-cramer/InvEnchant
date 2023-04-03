package com.satiryz.invenchant.enchants;

import com.satiryz.invenchant.DevTools;
import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModItemHandler;
import com.satiryz.invenchant.capabilities.ModItemHandlerProvider;
import com.satiryz.invenchant.init.EnchantmentInit;
import com.satiryz.invenchant.tags.EnchantmentTags;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
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
		return EnchantmentTags.isShulkerLike(stack);
	}

	public static void onItemPickup(ItemPickupEvent event) {
		Player player = event.getEntity();
		ItemStack pickedUpStack = event.getStack();

		DevTools.debugMessage(("Picked up stack: " + pickedUpStack), player);

		ModItemHandler playerInventory = player.getCapability(ModItemHandlerProvider.ITEM_HANDLER).resolve().get();
		DevTools.debugMessage(("Inventory: " + playerInventory.getStacks()), player);
		
		for (ItemStack invStack : playerInventory.getStacks()) {
			if (EnchantmentTags.isShulkerLike(invStack)) {
				DevTools.debugMessage("Found available shulker box", player);
				CompoundTag shulkerBoxData = invStack.serializeNBT();
				DevTools.debugMessage("Shulker Box Data: " + shulkerBoxData, player);
				getShulkerBoxInventory(shulkerBoxData);
				break;
			}
		}
	}
	
	public static NonNullList<ItemStack> getShulkerBoxInventory(CompoundTag nbt) {
		//read nbt data and transfer it to the inventory variable
		
		ListTag nbtInventoryData = nbt.getList("BlockEntityTag", Tag.TAG_COMPOUND);
		System.out.println("nbtInvData: " + nbtInventoryData);
		NonNullList<ItemStack> inventory = NonNullList.withSize(27, ItemStack.EMPTY);
		
		return inventory;
	}
}
