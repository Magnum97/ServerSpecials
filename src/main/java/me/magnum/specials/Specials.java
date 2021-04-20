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
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static me.magnum.specials.config.Config.specials;

public class Specials extends JavaPlugin {

	@Getter
	public static Specials plugin;
	@Getter
	@Setter
	public String prefix;
	
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
		Common.log("Cleaning memory..");
		specials.clear();
		Common.log("disabled.");
		plugin = null;
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
	
	@SuppressWarnings("deprecation")
	private void registerCommands () {
		BukkitCommandManager commandManager;
		commandManager = new BukkitCommandManager(this);
		commandManager.enableUnstableAPI("help");
		commandManager.getCommandCompletions()
				.registerAsyncCompletion("specials", c -> ImmutableList.of(specials.keySet().toString()));
		commandManager.registerCommand(new ItemHandler());
	}
}
