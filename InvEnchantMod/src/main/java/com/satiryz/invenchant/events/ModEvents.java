package com.satiryz.invenchant.events;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.capabilities.ModItemHandler;
import com.satiryz.invenchant.capabilities.ModItemHandlerProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InvEnchant.MODID)
public class ModEvents {
	@SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(ModItemHandlerProvider.ITEM_HANDLER).isPresent()) {
                event.addCapability(new ResourceLocation(InvEnchant.MODID, "properties"), new ModItemHandlerProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(ModItemHandlerProvider.ITEM_HANDLER).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ModItemHandlerProvider.ITEM_HANDLER).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ModItemHandler.class);
    }
}
