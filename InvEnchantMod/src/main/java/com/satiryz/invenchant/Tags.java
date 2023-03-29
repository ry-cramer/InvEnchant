package com.satiryz.invenchant;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Tags {
	public static final TagKey<Item> SHULKER_LIKE = 
			ItemTags.create(new ResourceLocation(InvEnchant.MODID, "shulker_like"));
	
	public static boolean isShulkerLike(Item item) {
		ItemStack itemStack = new ItemStack(item);
		return isShulkerLike(itemStack);
	}
	
	public static boolean isShulkerLike(ItemStack item) {
	    return item.is(SHULKER_LIKE);
	  }
}
