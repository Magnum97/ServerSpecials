package me.magnum.specials.util;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Listeners implements Listener {
	public static HeadDatabaseAPI headsAPI;
	
	@EventHandler
	public void onDbLoad(DatabaseLoadEvent event){
		HeadDatabaseAPI headDatabaseAPI = new HeadDatabaseAPI();
	}

}
