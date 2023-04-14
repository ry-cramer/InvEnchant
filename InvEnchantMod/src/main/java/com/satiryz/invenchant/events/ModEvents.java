package com.satiryz.invenchant.events;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModInventoryHandler;
import com.satiryz.invenchant.capabilities.ModInventoryHandlerProvider;
import com.satiryz.invenchant.enchants.ReplenishEnchantment;
import com.satiryz.invenchant.init.EnchantmentInit;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = InvEnchant.MODID)
public class ModEvents {
	@SubscribeEvent
	public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			event.addCapability(new ResourceLocation(InvEnchant.MODID, "properties.player"),
					new ModInventoryHandlerProvider((Player) event.getObject()));
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesContainer(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (stack.getEnchantmentLevel(EnchantmentInit.SIPHON_ENCHANT) > 0 || stack.getEnchantmentLevel(EnchantmentInit.REPLENISH_ENCHANT) > 0) {
			event.addCapability(new ResourceLocation(InvEnchant.MODID, "properties.shulker_box"),
					new ModInventoryHandlerProvider(stack));
		}
	}

	@SubscribeEvent
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			event.getOriginal().getCapability(ModInventoryHandlerProvider.INVENTORY_HANDLER)
					.ifPresent(oldStore -> {
						event.getOriginal().getCapability(ModInventoryHandlerProvider.INVENTORY_HANDLER)
								.ifPresent(newStore -> {
									newStore.copyFrom(oldStore);
								});
					});
		}
	}

	@SubscribeEvent
	public static void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(ModInventoryHandler.class);
	}
	
	private static final PreviousInventory prevInv = new PreviousInventory();

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.side != LogicalSide.CLIENT || event.player.isCreative()) {
			return;
		}
		
		Player player = event.player;
		ItemStack itemStackMainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
		ItemStack itemStackOffHand = player.getItemInHand(InteractionHand.OFF_HAND);
		int selectedSlot = player.getInventory().selected;
		
		if (selectedSlot == prevInv.selectedSlot){
			// if the  stack size is less than the previous stack size and the main/off hand haven't been switched
			if (itemStackMainHand.getCount() < prevInv.itemStackMainHand.getCount()
					&& (!ItemStack.matches(itemStackMainHand, prevInv.itemStackOffHand) || ItemStack.matches(itemStackMainHand, itemStackOffHand))) {
				System.out.println("Item dropped/placed from main hand");
				ReplenishEnchantment.attemptRefill(player, prevInv.itemStackMainHand, prevInv.itemStackMainHand.getCount() - itemStackMainHand.getCount(), selectedSlot);
			} else if (itemStackOffHand.getCount() < prevInv.itemStackOffHand.getCount()
					&& (!ItemStack.matches(itemStackOffHand, prevInv.itemStackMainHand) || ItemStack.matches(itemStackMainHand, itemStackOffHand))) {
				System.out.println("Item dropped/placed from offhand");
			}
		}
		prevInv.itemStackMainHand = itemStackMainHand.copy();
		prevInv.itemStackOffHand = itemStackOffHand.copy();
		prevInv.selectedSlot = selectedSlot;
	}
	
	private static class PreviousInventory {
		ItemStack itemStackMainHand = ItemStack.EMPTY;
		ItemStack itemStackOffHand = ItemStack.EMPTY;
		int selectedSlot = -1;
	}
}
