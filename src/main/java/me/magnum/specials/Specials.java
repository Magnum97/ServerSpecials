package me.magnum.specials;

import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.specials.commands.GUI;
import me.magnum.specials.commands.Give;
import me.magnum.specials.config.Item;
import me.magnum.specials.config.SimpleConfig;
import me.magnum.specials.util.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class Specials extends JavaPlugin {
	
	@Getter
	public static Specials plugin;
	public static HeadDatabaseAPI headDatabaseAPI;
	public static SimpleConfig data;
	public static Logger logger;
	
	@Override
	public void onEnable () {
		plugin = this;
		boolean useDefaults = checkFile();
		data = new SimpleConfig("items.yml", useDefaults);
		logger = Bukkit.getLogger();
		getServer().getPluginManager().registerEvents(new Listeners(), plugin);
		Item.init();
		registerCommands();
	}
	
	@Override
	public void onDisable () {
		super.onDisable();
	}
	
	private boolean checkFile () {
		final File file = new File(plugin.getDataFolder(), "items.yml");
		return !file.exists();
	}
	
	@SuppressWarnings("deprecated")
	private void registerCommands () {
		BukkitCommandManager commandManager;
		commandManager = new BukkitCommandManager(this);
		commandManager.enableUnstableAPI("help");
		commandManager.getCommandCompletions()
				.registerAsyncCompletion("foo", c -> {
					return ImmutableList.of("hdb", "450", "14469");
				});
		commandManager.registerCommand(new Give());
		commandManager.registerCommand(new GUI());
	}
}
