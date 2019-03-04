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
		Player player1 = (Player) Bukkit.getOfflinePlayer(player);
		if (item.equalsIgnoreCase("hdb")) {
			ItemStack token = this.itemApi.hdbToken(amount);
			if (player1.getInventory().firstEmpty() != -1) {
				player1.getInventory().addItem(token);
				player1.sendMessage("You got " + amount + " HDb Tokens");
			}
			else {
				player1.getWorld().dropItemNaturally(player1.getLocation().add(0.0D, 2.0D, 0.0D), token);
				player1.sendMessage("You dropped " + amount + " HDb Tokens");
			}
		}
		else {
			try {
				ItemStack head = this.heads.getItemHead(item);
				head.setAmount(amount);
				
				sender.sendMessage("§7[§8SS§7]§a Success");
				if (player1.getInventory().firstEmpty() != -1) {
					player1.getInventory().addItem(head);
					player1.sendMessage("You got " + amount + " head(s)");
				}
				else {
					player1.getWorld().dropItemNaturally(player1.getLocation().add(0.0D, 2.0D, 0.0D), head);
					player1.sendMessage("You dropped " + amount + " head(s)");
				}
			}
			
			catch (NullPointerException e) {
				sender.sendMessage("§7[§8SS§7]§c Failed");
				e.printStackTrace();
			}
		}
	}
}
