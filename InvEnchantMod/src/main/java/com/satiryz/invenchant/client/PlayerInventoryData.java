package com.satiryz.invenchant.client;

import net.minecraft.nbt.CompoundTag;

public class PlayerInventoryData {
	private static CompoundTag playerInventory;
	
	public static void set(CompoundTag playerInventory) {
        PlayerInventoryData.playerInventory = playerInventory;
    }

    public static CompoundTag get() {
        return playerInventory;
    }
}
