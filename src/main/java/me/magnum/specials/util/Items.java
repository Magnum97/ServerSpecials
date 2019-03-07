package me.magnum.specials.util;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.specials.Specials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Items {
	
	HeadDatabaseAPI headDatabaseAPI = Specials.headDatabaseAPI;
	
	public ItemStack hdbToken (int amount) {
		ItemStack hdbToken = new ItemStack(Material.PAPER);
		ItemMeta im = hdbToken.getItemMeta();
		hdbToken.setAmount(amount);
		im.setDisplayName("§6HDb Token");
		im.setLore(Arrays.asList("Use this to get decorative heads", "To use type: §e/hdb"));
		hdbToken.setItemMeta(im);
		return hdbToken;
	}
	
	
	// public ItemStack hdbToken () {
	// 	return hdbToken(1);
	// }
	
	public ItemStack butter () {
		try {
			ItemStack butter = headDatabaseAPI.getItemHead("2192");
			return butter;
		}
		catch (NullPointerException npe) {
			Bukkit.getLogger().warning("Could not find head");
		}
		return null;
	}
	
	public ItemStack horsemover (int amount){
		HeadDatabaseAPI headsdatabase = new HeadDatabaseAPI();
		ItemStack head = headsdatabase.getItemHead("25352");
		head.setAmount(amount);
		String title ="§dHorse Move Token";
		ItemStack token = new ItemBuilder(head).setName(title).setLore(Arrays.asList("§aGive this token to Magnum","§aand he will transport your horse.")).toItemStack();
		return head;
	}
	
}
