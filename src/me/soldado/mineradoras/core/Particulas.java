package me.soldado.mineradoras.core;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import me.soldado.mineradoras.Main;
import me.soldado.mineradoras.ParticleEffect;

public class Particulas {

	Main plugin;
	public Particulas(Main plugin){
		this.plugin = plugin;
	}
	
	void laser(Location loc1, Location loc2){
		loc1.add(0.5, -1, 0.5);
		loc2.add(0.5, 1, 0.5);
		Location loc = loc1.clone();
		Vector dir = loc2.toVector().subtract(loc1.toVector()).normalize();
		double razao = 0.1;
		for(double i = 0; i < loc1.distance(loc2); i += razao){
			loc.add(dir.clone().multiply(razao));
            ParticleEffect.CRIT_MAGIC.display(0, 0, 0, 0, 1, loc, 20);
		}
	}
}