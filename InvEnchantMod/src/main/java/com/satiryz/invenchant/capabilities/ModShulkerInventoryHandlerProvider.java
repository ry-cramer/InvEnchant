package com.satiryz.invenchant.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ModShulkerInventoryHandlerProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
	public static Capability<ModShulkerInventoryHandler> SHULKER_INVENTORY_HANDLER = CapabilityManager
			.get(new CapabilityToken<>() { 
			});

	private ModShulkerInventoryHandler stacks = null;
	private ItemStack shulkerBox = null;
	private final LazyOptional<ModShulkerInventoryHandler> optional = LazyOptional.of(this::createStacks);

	private ModShulkerInventoryHandler createStacks() {
		if (this.stacks == null) {
			if (!(this.shulkerBox == null)) {
				this.stacks = new ModShulkerInventoryHandler(shulkerBox);
			}
		}
		return this.stacks;
	}

	public ModShulkerInventoryHandlerProvider(ItemStack shulkerBox) {
		this.shulkerBox = shulkerBox;
	}
	
	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == SHULKER_INVENTORY_HANDLER) {
			return optional.cast();
		}

		return LazyOptional.empty();
	}

	@Override
    public CompoundTag serializeNBT()
    {
        CompoundTag nbt = new CompoundTag();
        nbt = createStacks().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
    	createStacks().loadNBTData(nbt);
    }
}

