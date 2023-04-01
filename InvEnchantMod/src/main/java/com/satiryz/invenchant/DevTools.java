package com.satiryz.invenchant;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class DevTools {
	// Set to true while in development. Set to false for play-testing or production
	public static boolean debug = true;
	
	public static void debugMessage(String message, Player player) {
		if(debug = true) {
			player.sendSystemMessage(Component.literal(message));
		}
	}
}
