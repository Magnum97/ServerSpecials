package me.magnum.specials.util;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Actions {
	
	private Items api = new Items();
	
	public void actions () {
	}
	
	private void give (Player giveTo, ItemStack item) {
		Player player = giveTo;
		ItemStack toGive;
		toGive = api.hdbToken();
		if (player.getInventory().firstEmpty() != -1) {
			player.getInventory().addItem(toGive);
			player.sendMessage("§aYou have been given §6"+toGive.toString());
		}
		else {
			player.sendMessage("§aYou found §6" + toGive.toString() + "§a but yor inventory is full.");
			Location where = player.getLocation();
			player.getWorld().dropItemNaturally(where.add(0, 2, 0), toGive);
		}
	}
	
}
