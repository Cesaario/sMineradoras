package me.soldado.mineradoras;

import org.bukkit.scheduler.BukkitRunnable;

import me.soldado.mineradoras.core.Mineradora;

public class Cronometro extends BukkitRunnable{
	public Main plugin;
	
	public Cronometro(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		for(Mineradora min : plugin.core.mineradoras.values()){
			if(min.isLigada()) plugin.core.tick(min);
		}
	}
}
