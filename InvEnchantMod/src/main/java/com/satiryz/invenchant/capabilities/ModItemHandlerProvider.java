package com.satiryz.invenchant.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ModItemHandlerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ModItemHandler> ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<ModItemHandler>() { });

    private ModItemHandler stacks = null;
    private final LazyOptional<ModItemHandler> optional = LazyOptional.of(this::createStacks);

    private ModItemHandler createStacks() {
        if(this.stacks == null) {
            this.stacks = new ModItemHandler();
        }

        return this.stacks;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ITEM_HANDLER) {
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
