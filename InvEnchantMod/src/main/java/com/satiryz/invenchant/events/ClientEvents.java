package com.satiryz.invenchant.events;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.enchants.SiphonEnchantment;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
	@Mod.EventBusSubscriber(modid = InvEnchant.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onPickupEvent(EntityItemPickupEvent event) {
        	SiphonEnchantment.onItemPickup(event);
        }
    }

    @Mod.EventBusSubscriber(modid = InvEnchant.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        //@SubscribeEvent}
    	
    }
}