package com.satiryz.invenchant.networking.packets;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PlayerInventorySyncS2CPacket {

	private final CompoundTag playerInventory;

	public PlayerInventorySyncS2CPacket(CompoundTag playerInventory) {
		this.playerInventory = playerInventory;
	}

	public PlayerInventorySyncS2CPacket(FriendlyByteBuf buf) {
	    this.playerInventory = buf.readAnySizeNbt();
	}

	public void toBytes(FriendlyByteBuf buf) {
		buf.writeNbt(playerInventory);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			// HERE WE ARE ON THE CLIENT!
			//playerInventory.set(this.playerInventory);
		});
		return true;
	}

}
