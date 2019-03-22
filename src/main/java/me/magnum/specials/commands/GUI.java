package me.magnum.specials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.magnum.lib.Common;
import me.magnum.specials.Specials;
import me.magnum.specials.config.Item;
import me.magnum.specials.config.SimpleConfig;
import me.magnum.specials.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI extends BaseCommand {
	
	private Items items = new Items();
	private final SimpleConfig data = new SimpleConfig("items.yml");
	
	@CommandAlias("ssgui")
	public void onGui (CommandSender sender) {
		if (!(sender instanceof Player)) {
			Common.tell(sender, "&cThis command is only for Players");
			return;
		}
		final Player player = (Player) sender;
		player.openInventory(newGui());
		
	}
	
	private Inventory newGui () {
		Inventory gui = Bukkit.createInventory(null, 9 * 5, "Server Specials");
		// int i = 0;
		for (String key : Item.specials.keySet()) {
			gui.addItem(Item.specials.get(key));
		}
		return gui;
	}
}
