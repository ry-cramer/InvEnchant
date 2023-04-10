package com.satiryz.invenchant.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class ModInventoryHandlerProvider implements ICapabilityProvider {
	public static Capability<ModInventoryHandler> INVENTORY_HANDLER = CapabilityManager.get(new CapabilityToken<>() {
	});

	private ModInventoryHandler stacks = null;
	private Player player = null;
	private ItemStack shulkerBox = null;
	private final LazyOptional<ModInventoryHandler> optional = LazyOptional.of(this::createStacks);

	private ModInventoryHandler createStacks() {
		if (this.stacks == null) {
			if (!(this.shulkerBox == null)) {
				this.stacks = new ModInventoryHandler(shulkerBox);
			}
			if (!(this.player == null)) {
				this.stacks = new ModInventoryHandler(player);
			}
		}
		return this.stacks;
	}

	public ModInventoryHandlerProvider(ItemStack shulkerBox) {
		this.shulkerBox = shulkerBox;
	}
	
	public ModInventoryHandlerProvider(Player player) {
		this.player = player;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == INVENTORY_HANDLER) {
			return optional.cast();
		}

		return LazyOptional.empty();		
	}
}
