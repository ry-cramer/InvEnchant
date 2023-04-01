package com.satiryz.invenchant.networking.packets;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class InventoryC2SPacket {
	public InventoryC2SPacket() {}

    public InventoryC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
    	NetworkEvent.Context context = supplier.get();
    	context.enqueueWork(() -> {
    		//ServerPlayer player = context.getSender();
    		
    	});
    	return true;
    }
}
