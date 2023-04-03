package com.satiryz.invenchant.entities;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.entities.custom.FinnEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, InvEnchant.MODID);
	
	public static final RegistryObject<EntityType<FinnEntity>> FINN = 
            ENTITY_TYPES.register("finn",
                    () -> EntityType.Builder.of(FinnEntity::new, MobCategory.CREATURE)
                            .sized(1.0f, 1.5f)
                            .build(new ResourceLocation(InvEnchant.MODID, "finn").toString()));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
