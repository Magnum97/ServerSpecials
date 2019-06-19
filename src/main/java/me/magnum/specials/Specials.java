package me.magnum.specials;

import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.ImmutableList;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.Setter;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import me.magnum.specials.commands.ItemHandler;
import me.magnum.specials.config.Config;
import me.magnum.specials.util.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Specials extends JavaPlugin {
	
	@Getter
	public static Specials plugin;
	@Getter @Setter public String prefix;
	
	public static SimpleConfig cfg;
	public static SimpleConfig data;
	
	public static InventoryManager INVENTORY_MANAGER;
	
	@Override
	public void onEnable () {
		plugin = this;
		
		Common.setInstance(plugin);
		cfg = new SimpleConfig("config.yml", plugin);
		Common.log("Checking for existing config file...");
		boolean useDefaults = checkFile();
		data = new SimpleConfig("items.yml", plugin, useDefaults);
		INVENTORY_MANAGER = new InventoryManager(plugin);
		INVENTORY_MANAGER.init();
		Config.init();
		registerCommands();
	}
	
	@Override
	public void onDisable () {
		super.onDisable();
	}
	
	private boolean checkHDb () {
		boolean hdb;
		if (getServer().getPluginManager().getPlugin("HeadDatabase") == null) {
			Common.log("HeadDatabase hook disabled");
			return false;
		}
		Common.log("HeadDatabase detected. HDB Features active.");
		getServer().getPluginManager().registerEvents(new Listeners(), plugin);
		return true;
	}
	
	private boolean checkFile () {
		final File file = new File(plugin.getDataFolder(), "items.yml");
		if (file.exists()) {
			Common.log("Loading existing config...");
		}
		else {
			Common.log("Extracting config from JAR...");
		}
		return !file.exists();
	}
	
	@SuppressWarnings("deprecated")
	private void registerCommands () {
		BukkitCommandManager commandManager;
		commandManager = new BukkitCommandManager(this);
		commandManager.enableUnstableAPI("help");
		commandManager.getCommandCompletions()
				.registerAsyncCompletion("specials", c -> {
					return ImmutableList.of(Config.specials.keySet().toString());
				});
		commandManager.registerCommand(new ItemHandler());
	}
}
