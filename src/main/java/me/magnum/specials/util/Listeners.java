package me.magnum.specials.util;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.magnum.specials.Specials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.xml.crypto.Data;

public class Listeners implements Listener {
	
	@EventHandler
	public void onDbLoad(DatabaseLoadEvent event){
		Specials.headDatabaseAPI = new HeadDatabaseAPI();
	}

}
