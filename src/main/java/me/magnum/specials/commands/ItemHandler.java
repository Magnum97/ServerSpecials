package me.magnum.specials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.lib.CheckSender;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import me.magnum.specials.Specials;
import me.magnum.specials.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.magnum.specials.Specials.cfg;
import static me.magnum.specials.Specials.data;

@CommandAlias("specials|ss")
public class ItemHandler extends BaseCommand {
	
	private HeadDatabaseAPI heads = new HeadDatabaseAPI();
	private final String pre = Config.getPREFIX();
	
	@HelpCommand
	public void doHelp (CommandSender sender, CommandHelp help) {
		Common.tell(sender, ("Server Specials"));
		help.showHelp();
	}
	
	@Subcommand("version|ver|info")
	public void onVersion (CommandSender sender) {
		Common.tell(sender, Specials.getPlugin().getDescription().toString());
	}
	
	@Subcommand("give")
	@CommandCompletion("@players|@specials")
	@Description("Save the item in your hand to the config file")
	@CommandPermission("specials.give")
	public void onGive (CommandSender sender, String player, String item, @Default("1") int amount) {
		Player p = (Player) Bukkit.getOfflinePlayer(player);
		ItemStack token = null;
		
		for (String key : Config.specials.keySet()) {
			if (key.equalsIgnoreCase(item)) {
				token = Config.specials.get(key);
				give(p, token, amount);
			}
		}
		//
		// Set keys = Config.specials.keySet();
		// Config.specials.keySet().forEach(key->{
		// key.compareToIgnoreCase(item)
		// });
		// if (Config.specials.keySet. (item)) {
		// 	token = Config.specials.get(item);
		// 	give(p, token, amount);
	}
	
	
	public void give (Player player, ItemStack token, int amount) {
		if (player.getInventory().firstEmpty() != -1) {
			player.getInventory().addItem(token);
			player.sendMessage("You were given " + amount + " " + token.getItemMeta().getDisplayName());
		}
		else {
			player.getWorld().dropItemNaturally(player.getLocation().add(0.0D, 2.0D, 0.0D), token);
			player.sendMessage("You dropped " + amount + " " + token.getItemMeta().getDisplayName());
		}
	}
	
	@Subcommand("reload|r")
	public void onReload (CommandSender sender) {
		cfg.reloadConfig();
		data.reloadConfig();
		Common.tell(sender, pre + "§eConfig reloaded.");
	}
	
	@Subcommand("save")
	@Description("Hold and item and use this command to save it to items.yml. " +
			"Recommended way to make new items. You can then edit the items.yml")
	@CommandPermission("specials.save")
	public void onSave (CommandSender sender, String item) {
		if (!CheckSender.isPlayer(sender)) {
			return;
		}
		SimpleConfig data = new SimpleConfig("items.yml", Specials.getPlugin(), false);
		Player p = (Player) sender;
		Config.specials.put(item, p.getInventory().getItemInMainHand());
		data.set(item, p.getInventory().getItemInMainHand());
		data.saveConfig();
	}
	
	@Subcommand("removeitem|remove")
	@CommandPermission("@specials")
	@CommandCompletion("@specials")
	public void onRemove (CommandSender sender, String key, @Optional String confirm) {
		if (confirm.equalsIgnoreCase("yesimsure")) {
			Common.tell(sender, pre + Config.specials.get(key).getItemMeta().getDisplayName() + " has been removed.");
			Config.specials.remove(key);
			data.set(key, null);
			data.saveConfig();
		}
		else {
			Common.tell(sender, pre + "&eIf you are sure you want to remove " + key +
					" from the database append &6YesImSure &eto your command.");
		}
	}
	
	@Subcommand("show")
	public void onShow (CommandSender sender) {
		for (String key : Config.specials.keySet()) {
			String item = Config.specials.get(key).getItemMeta().getDisplayName();
			sender.sendMessage(pre + key + ": " + item);
		}
	}
}
