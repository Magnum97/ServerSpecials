package me.magnum.specials.config;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.specials.Specials;
import me.magnum.specials.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Item extends SimpleConfig {
	
	public static HashMap <String, ItemStack> specials = new HashMap <>();
	private SimpleConfig data = Specials.data;
	
	public Item (String filename) {
		super(filename);
	}

	private void onLoad () {
		reloadConfig();
		// specials.clear();
		// SimpleConfig data = new SimpleConfig("items.yml");
		String shortName, displayName;
		Material material = null;
		List <String> lore = null;
		ItemStack item;
		int slot;
		specials.clear();
		HeadDatabaseAPI hdb = new HeadDatabaseAPI();
		int i = 0;
		for (String key : data.getKeys(false)) {
			i++;
			Specials.logger.info("[SS] ItemStack " + i + " loading..");
			
			boolean isHead = data.getBoolean(key + ".hdb");
			
			if (isHead) {
				item = hdb.getItemHead(data.getString(key + ".index"));
				data.setPathPrefix(key + ".meta");
				displayName = data.getString("display-name").replace("&", "§");
				List<String> rawLore;
				rawLore = data.getStringList("lore");
				lore = rawLore.stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
				item = new ItemBuilder(item).setName(displayName).setLore(lore).toItemStack();
				specials.put(key, item);
				Specials.logger.info("[SS] ItemStack " + key + " initialized.");
			}
			else {
				item = data.getItemStack(key);
				specials.put(key, item);
				Specials.logger.info("[SS] ItemStack " + key + " initialized.");
			}
		}
	}
	
/*
	public HashMap <String, String> getItems () {
		HashMap <String, String> stringItems = new HashMap <>();
		for (String key : specials.keySet()) {
			String item = specials.get(key).getItemMeta().getDisplayName();
			stringItems.put(key, item);
		}
		return stringItems;
	}
*/
	
	public static void init () {
		new Item("items.yml").onLoad();
	}
	
}
