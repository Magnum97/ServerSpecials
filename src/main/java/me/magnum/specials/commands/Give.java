package me.magnum.specials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.lib.Common;
import me.magnum.specials.config.Item;
import me.magnum.specials.config.SimpleConfig;
import me.magnum.specials.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("specials|ss")
public class Give extends BaseCommand {
	
	private Items itemApi = new Items();
	private HeadDatabaseAPI heads = new HeadDatabaseAPI();
	private final String PRE = "§7[§8SS§7]§c ";
	
	@HelpCommand
	public void doHelp (CommandSender sender, CommandHelp help) {
		Common.tell(sender, ("Server Specials"));
		help.showHelp();
	}
	
	@Subcommand("give")
	@CommandCompletion("@players|@foo")
	@Description("Give player a special item")
	@CommandPermission("specials.give")
	public void onGive (CommandSender sender, String player, String item, @Default("1") int amount) {
		Player player1 = (Player) Bukkit.getOfflinePlayer(player);
		ItemStack token = null;
		if (item.equalsIgnoreCase("horsemover")) {
			token = this.itemApi.horsemover(amount);
			give(player1, token, amount, token.getItemMeta().getDisplayName());
			return;
		}
		if (item.equalsIgnoreCase("hdb")) {
			token = this.itemApi.hdbToken(amount);
			give(player1, token, amount, token.getItemMeta().getDisplayName());
			return;
		}
		if (Item.specials.containsKey(item)) {
			token = Item.specials.get(item);
			give(player1, token, amount, token.getItemMeta().getDisplayName());
		}
		else {
			try {
				token = this.heads.getItemHead(item);
				token.setAmount(amount);
				give(player1, token, amount, token.getType().toString().toLowerCase());
				sender.sendMessage("§7[§8SS§7]§a Success");
			}
			catch (NullPointerException e) {
				sender.sendMessage("§7[§8SS§7]§c Failed");
				e.printStackTrace();
			}
		}
		if (token != null) {
			give(player1, token, amount, item);
		}
		else {
			sender.sendMessage(PRE + "§cSomething went wrong.");
		}
	}
	
	private void give (Player player, ItemStack token, int amount, String item) {
		if (player.getInventory().firstEmpty() != -1) {
			player.getInventory().addItem(token);
			player.sendMessage("You got " + amount + " " + token.getItemMeta().getDisplayName());
		}
		else {
			player.getWorld().dropItemNaturally(player.getLocation().add(0.0D, 2.0D, 0.0D), token);
			player.sendMessage("You dropped " + amount + " " + token.getItemMeta().getDisplayName());
		}
	}
	
	@Subcommand("reload|r")
	public void onReload (CommandSender sender) {
		
		
		Common.tell(sender, PRE +"§eConfig reloaded.");
	}
	
	@Subcommand("save")
	public void onSave (CommandSender sender, String item) {
		SimpleConfig data = new SimpleConfig("items.yml");
		data.setPathPrefix("items.");
		if (item.equalsIgnoreCase("hdb")) {
			data.set("hdb", itemApi.hdbToken(1));
			data.saveConfig();
		}
		if (item.equalsIgnoreCase("horsemover")) {
			data.set("horsemover", itemApi.horsemover(1));
			data.saveConfig();
		}
		else {
			sender.sendMessage(PRE + "§cItem not found");
		}
	}
	
	@Subcommand("show")
	public void onShow (CommandSender sender) {
		
		for (String key : Item.specials.keySet()) {
			String item = Item.specials.get(key).getItemMeta().getDisplayName();
		sender.sendMessage(PRE + item);
		}
	}
}
