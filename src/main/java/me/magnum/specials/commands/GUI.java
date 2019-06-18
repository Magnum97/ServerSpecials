package me.magnum.specials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import fr.minuskube.inv.SmartInventory;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import me.magnum.specials.Specials;
import me.magnum.specials.config.Config;
import me.magnum.specials.util.InventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUI extends BaseCommand {
	
	private final SimpleConfig data = Specials.data;
	public static final SmartInventory INVENTORY = SmartInventory.builder()
			.id("Main")
			.manager(Specials.INVENTORY_MANAGER)
			.provider(new InventoryBuilder())
			.size(6, 9)
			.title("Main").build();
	
	@CommandAlias("ssgui")
	public void onGui (CommandSender sender) {
		if (!(sender instanceof Player)) {
			Common.tell(sender, "&cThis command is only for Players");
			return;
		}
		final Player player = (Player) sender;
		INVENTORY.open(player);
		// player.openInventory(newGui());
		
	}
	
	private Inventory newGui () {
		Inventory gui = Bukkit.createInventory(null, 9 * 5, "Server Specials");
		// int i = 0;
		for (String key : Config.specials.keySet()) {
			gui.addItem(Config.specials.get(key));
		}
		return gui;
	}
}
