package me.magnum.specials;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.lib.Common;
import me.magnum.specials.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("secrets|specials|ss")
public class Commands extends BaseCommand {
	
	private Items itemApi = new Items();
	private HeadDatabaseAPI heads = new HeadDatabaseAPI();
	
	@HelpCommand
	public void doHelp (CommandSender sender, CommandHelp help) {
		Common.tell(sender, ("Secret Server Specials"));
		help.showHelp();
	}
	
	
	@Subcommand("give")
	@CommandCompletion("@players|@foo")
	@Description("Give player a special item")
	@CommandPermission("specials.give")
	public void onGive (CommandSender sender, String player, String item, @Default("1") int amount) {
		String pre = "§7[§8SS§7]§c ";
		Player player1 = (Player) Bukkit.getOfflinePlayer(player);
		ItemStack token = null;
		if (item.equalsIgnoreCase("horsemover")) {
			token = this.itemApi.horsemover(amount);
			give(player1, token, amount, token.getType().toString().toLowerCase());
			return;
		}
		if (item.equalsIgnoreCase("hdb")) {
			token = this.itemApi.hdbToken(amount);
			give(player1, token, amount, token.getType().toString().toLowerCase());
		return;
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
			sender.sendMessage(pre + "§cSomething went wrong.");
		}
	}
	
	private void give (Player player, ItemStack token, int amount, String item) {
		if (player.getInventory().firstEmpty() != -1) {
			player.getInventory().addItem(token);
			player.sendMessage("You got " + amount + " " + item);
		}
		else {
			player.getWorld().dropItemNaturally(player.getLocation().add(0.0D, 2.0D, 0.0D), token);
			player.sendMessage("You dropped " + amount + " " + item);
		}
		
		
	}
}
