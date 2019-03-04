package me.magnum.specials;

import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.ImmutableList;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.specials.util.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

public class Specials extends JavaPlugin {
	
	Specials plugin;
	BukkitCommandManager commandManager;
	public static HeadDatabaseAPI headDatabaseAPI;
	
	@Override
	public void onEnable () {
		plugin = this;
		getServer().getPluginManager().registerEvents(new Listeners(), plugin);
		registerCommands();
	}
	
	@Override
	public void onDisable () {
		super.onDisable();
	}
	
	@SuppressWarnings("deprecated")
	public void registerCommands () {
		commandManager = new BukkitCommandManager(this);
		commandManager.enableUnstableAPI("help");
		commandManager.getCommandCompletions()
				.registerAsyncCompletion("foo", c->{return ImmutableList.of("hdb","450","14469");
				});
		commandManager.registerCommand(new Commands());
	}
}
