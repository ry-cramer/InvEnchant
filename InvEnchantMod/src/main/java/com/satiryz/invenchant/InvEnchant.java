package com.satiryz.invenchant;

import com.satiryz.invenchant.init.EnchantmentInit;
import com.satiryz.invenchant.networking.ModPackets;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(InvEnchant.MODID)
public class InvEnchant
{
    public static final String MODID = "invenchant";

    public InvEnchant()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        
        EnchantmentInit.ENCHANTMENTS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void commonSetup(final FMLCommonSetupEvent event) {
    	ModPackets.register();
    }
}
