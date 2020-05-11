package me.soldado.mineradoras.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.soldado.mineradoras.Main;

public class AntiBuild implements Listener {
	
	Main plugin;
	
	public AntiBuild(Main plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void quebrar(BlockBreakEvent event){
		if(plugin.core.proximoDeMineradora(event.getBlock().getLocation(), false)){
			if(event.getPlayer().hasPermission("mineradoras.buildbypass")) return;
			event.setCancelled(true);
			event.getPlayer().sendMessage(plugin.msg.mineradoraProxima);
		}
	}

	@EventHandler
	public void colocar(BlockPlaceEvent event){
		if(plugin.core.proximoDeMineradora(event.getBlock().getLocation(), false)){
			if(event.getPlayer().hasPermission("mineradoras.buildbypass")) return;
			event.setCancelled(true);
			event.getPlayer().sendMessage(plugin.msg.mineradoraProxima);
		}
	}

    public void registrarEventos(){
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
