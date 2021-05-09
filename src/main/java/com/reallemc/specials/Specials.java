package com.reallemc.specials;

import com.google.common.collect.ImmutableList;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.Setter;
import com.reallemc.specials.commands.ItemHandler;
import com.reallemc.specials.config.Config;
import org.bukkit.plugin.java.JavaPlugin;
import com.reallemc.*;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

import static com.reallemc.specials.config.Config.specials;

public class Specials extends JavaPlugin {

	@Getter
	public static Specials plugin;
	@Getter
	@Setter
	public String prefix;
	
	public static Yaml cfg;
	public static Yaml data;

	public static InventoryManager INVENTORY_MANAGER;

	@Override
	public void onEnable () {
		plugin = this;

		cfg = new Yaml("config.yml", plugin);
		Common.log("Checking for existing config file...");
		boolean useDefaults = checkFile();
		data = new Yaml("items.yml", plugin, useDefaults);
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
		PaperCommandManager commandManager;
		commandManager = new PaperCommandManager(this);
		commandManager.enableUnstableAPI("help");
		commandManager.getCommandCompletions()
				.registerAsyncCompletion("specials", c -> ImmutableList.of(specials.keySet().toString()));
		commandManager.registerCommand(new ItemHandler());
	}
}
