package com.satiryz.invenchant.init;

import com.satiryz.invenchant.InvEnchant;
import com.satiryz.invenchant.Tags;
import com.satiryz.invenchant.enchants.ReplenishEnchantment;
import com.satiryz.invenchant.enchants.SiphonEnchantment;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {
	public static final Item[] SHULKER_BOX_ITEMS =
		      new Item[] {Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX,
		          Items.LIGHT_BLUE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.PINK_SHULKER_BOX,
		          Items.GRAY_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.CYAN_SHULKER_BOX, Items.PURPLE_SHULKER_BOX,
		          Items.BLUE_SHULKER_BOX, Items.BROWN_SHULKER_BOX, Items.GREEN_SHULKER_BOX, Items.RED_SHULKER_BOX,
		          Items.BLACK_SHULKER_BOX};
	
	public static final EnchantmentCategory SHULKER_LIKE = EnchantmentCategory.create("shulker_like", item -> Tags.isShulkerLike(item));
	
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, InvEnchant.MODID);
	
	public static final RegistryObject<Enchantment> SIPHON = ENCHANTMENTS.register("siphon", () -> new SiphonEnchantment());
	public static final RegistryObject<Enchantment> REPLENISH = ENCHANTMENTS.register("replenish", () -> new ReplenishEnchantment());
}
