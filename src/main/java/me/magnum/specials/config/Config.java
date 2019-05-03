package me.magnum.specials.config;

import lombok.Getter;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import me.magnum.specials.Specials;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Config extends SimpleConfig {
	
	public static HashMap <String, ItemStack> specials = new HashMap <>();
	@Getter
	public static String PREFIX;
	@Getter
	public static ItemStack NEXT;
	@Getter
	public static ItemStack PREV;
	private SimpleConfig cfg = Specials.cfg;
	private SimpleConfig data = Specials.data;
	
	
	private Config (String filename) {
		super(filename, Specials.getPlugin());
	}
	
	private void onLoad () {
		PREFIX = cfg.getString("prefix").replace("&", "§");
		NEXT = cfg.getItemStack("next");
		PREV = cfg.getItemStack("prev");
		
		ItemStack item;
		specials.clear();
		int i = 0;
		for (String key : data.getKeys(false)) {
			i++;
			Common.setInstance(Specials.getPlugin());
			Common.log("&d ItemStack " + i + " loading..");
			if (isItemStack(key)) {
				item = new ItemStack(data.getItemStack(key));
				specials.put(key, item);
				Common.log("&a ItemStack " + key + " initialized.");
			}
			else {
				Common.log("&e" + key + " is not an item stack.",
				           "Hold an item and do /specials save to commit to config.",
				           "Items can be easily edited in " + Specials.plugin.getDataFolder() + data.toString());
			}
		}
	}
	
	public static void init () {
		new Config("items.yml").onLoad();
	}
	
}
