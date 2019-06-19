package me.magnum.specials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import fr.minuskube.inv.SmartInventory;
import me.magnum.lib.CheckSender;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import me.magnum.specials.Specials;
import me.magnum.specials.config.Config;
import me.magnum.specials.util.InventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.magnum.specials.Specials.cfg;
import static me.magnum.specials.Specials.data;
import static me.magnum.specials.config.Config.specials;

@CommandAlias("specials|ss")
public class ItemHandler extends BaseCommand {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
			.id("Main")
			.manager(Specials.INVENTORY_MANAGER)
			.provider(new InventoryBuilder())
			.size(6, 9)
			.title("Main").build();
	
	private final String pre = Config.getPREFIX();
	
	@Default
	@Subcommand("gui")
	@Description("Open GUI and view or get items")
	@CommandPermission("specials.command.gui")
	public void onGui (CommandSender sender) {
		if (!(sender instanceof Player)) {
			Common.tell(sender, "&cThis command is only for Players");
			return;
		}
		final Player player = (Player) sender;
		INVENTORY.open(player);
		// player.openInventory(newGui());
		
	}
	
	@HelpCommand
	public void doHelp (CommandSender sender, CommandHelp help) {
		Common.tell(sender, ("Server Specials"));
		help.showHelp();
	}
	
	private Inventory newGui () {
		Inventory gui = Bukkit.createInventory(null, 9 * 5, "Server Specials");
		// int i = 0;
		for (String key : Config.specials.keySet()) {
			gui.addItem(Config.specials.get(key));
		}
		return gui;
	}
	
	@Subcommand("version|ver|info")
	public void onVersion (CommandSender sender) {
		Common.tell(sender, Specials.getPlugin().getDescription().toString());
	}
	
	@Subcommand("give")
	@CommandCompletion("@players|@specials")
	@Description("Give special item to a player")
	@CommandPermission("specials.command.give")
	@SuppressWarnings("deprecation")
	public void onGive (CommandSender sender, String player, String item, @Default("1") int amount) {
		Player p = (Player) Bukkit.getOfflinePlayer(player);
		ItemStack token;
		
		for (String key : specials.keySet()) {
			if (key.equalsIgnoreCase(item)) {
				token = specials.get(key);
				give(p, token, amount);
			}
		}
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
	@CommandPermission("specials.command.reload")
	public void onReload (CommandSender sender) {
		cfg.reloadConfig();
		data.reloadConfig();
		Common.tell(sender, pre + "§eConfig reloaded.");
	}
	
	@Subcommand("save")
	@Description("Hold and item and use this command to save it to items.yml. " +
			"Recommended way to make new items. You can then edit the items.yml")
	@CommandPermission("specials.command.save")
	public void onSave (CommandSender sender, String item) { //todo Add confirmation message
		if (!CheckSender.isPlayer(sender)) {
			return;
		}
		SimpleConfig data = new SimpleConfig("items.yml", Specials.getPlugin(), false);
		Player p = (Player) sender;
		specials.put(item, p.getInventory().getItemInMainHand());
		data.set(item, p.getInventory().getItemInMainHand());
		data.saveConfig();
	}
	
	@Subcommand("removeitem|remove") //todo Add confirmation / fail message
	@CommandCompletion("@specials")
	@CommandPermission("specials.command.remove")
	public void onRemove (CommandSender sender, String key, @Default(" ") String confirm) {
		if (confirm.equalsIgnoreCase("yesimsure")) {
			Common.tell(sender, pre + specials.get(key).getItemMeta().getDisplayName() + " has been removed.");
			specials.remove(key);
			data.set(key, null);
			data.saveConfig();
		}
		else {
			Common.tell(sender, pre + "&eIf you are sure you want to remove " + key +
					" from the database append &6YesImSure &eto your command.");
		}
	}
	
	@Subcommand("show")
	@CommandPermission("specials.command.show")
	@Description("View a list of saved specials")
	public void onShow (CommandSender sender) {
		for (String key : specials.keySet()) {
			String item = specials.get(key).getItemMeta().getDisplayName();
			sender.sendMessage(pre + key + ": " + item);
		}
	}
}
