package com.satiryz.invenchant.init;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.enchants.ReplenishEnchantment;
import com.satiryz.invenchant.enchants.SiphonEnchantment;
import com.satiryz.invenchant.tags.ShulkerLikeTag;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {
	public static final EnchantmentCategory SHULKER_LIKE = EnchantmentCategory.create("shulker_like", item -> ShulkerLikeTag.isShulkerLike(item));
	
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, InvEnchant.MODID);
	
	public static final Enchantment SIPHON_ENCHANT = new SiphonEnchantment();
	public static final Enchantment REPLENISH_ENCHANT = new ReplenishEnchantment();
	
	public static final RegistryObject<Enchantment> SIPHON = ENCHANTMENTS.register("siphon", () -> SIPHON_ENCHANT);
	public static final RegistryObject<Enchantment> REPLENISH = ENCHANTMENTS.register("replenish", () -> REPLENISH_ENCHANT);
}
