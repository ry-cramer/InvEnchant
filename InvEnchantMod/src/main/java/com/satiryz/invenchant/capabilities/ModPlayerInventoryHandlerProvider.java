package com.satiryz.invenchant.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ModPlayerInventoryHandlerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static Capability<ModPlayerInventoryHandler> PLAYER_INVENTORY_HANDLER = CapabilityManager
			.get(new CapabilityToken<ModPlayerInventoryHandler>() {
			});

	private ModPlayerInventoryHandler stacks = null;
	private Player player = null;
	private final LazyOptional<ModPlayerInventoryHandler> optional = LazyOptional.of(this::createStacks);

	private ModPlayerInventoryHandler createStacks() {
		if (this.stacks == null) {
			if (!(this.player == null)) {
				this.stacks = new ModPlayerInventoryHandler(player);
			} 
		}
		return this.stacks;
	}

	public ModPlayerInventoryHandlerProvider(Player player) {
		this.player = player;
	}
	
	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == PLAYER_INVENTORY_HANDLER) {
			return optional.cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		createStacks().saveNBTData(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		createStacks().loadNBTData(nbt);
	}
}

