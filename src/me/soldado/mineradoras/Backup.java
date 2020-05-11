package me.soldado.mineradoras;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.soldado.mineradoras.core.Broca;
import me.soldado.mineradoras.core.Combustivel;
import me.soldado.mineradoras.core.Mineradora;
import me.soldado.mineradoras.core.Tipo;

public class Backup {

	Main plugin;
	public Backup(Main plugin){
		this.plugin = plugin;
	}
	
	File bdFile;
	FileConfiguration bd;
	
	public void recarregarMineradora(String str){
		Mineradora min = desserializarMineradora(str);
		plugin.core.mineradoras.put(min.getLoc(), min);
		Block cmd = min.getCmdBlock();
		plugin.core.commandBlocks.put(cmd, min);
	}
	
	public void carregar(){
		try{
			if(bd.getStringList("mineradoras") != null){
				List<String> s = bd.getStringList("mineradoras");
				for(String str : s){
					recarregarMineradora(str);
				}
			}
		}catch(Exception e){
			plugin.getLogger().info("Erro ao carregar mineradoras.");
			e.printStackTrace();
		}
	}
	
	public void salvar(){
		if(bd.getStringList("mineradoras") != null){
			List<String> s = bd.getStringList("mineradoras");
			s.clear();
			for(Mineradora min : plugin.core.mineradoras.values()){
				s.add(serializarMineradora(min));
			}
			bd.set("mineradoras", s);
			try{
				bd.save(bdFile);
			}catch(IOException e){
				plugin.getLogger().info("Erro ao salvar mineradoras");
				e.printStackTrace();
			}
		}
	}
	
	public void iniciarBackup(){

		if (bdFile == null) {
			bdFile = new File(plugin.getDataFolder(), "mineradoras.dat");
		}
		if (!bdFile.exists()) {
			plugin.saveResource("mineradoras.dat", false);
		}
		bd = YamlConfiguration.loadConfiguration(bdFile);
	}
	
	//mundo;x;y;z;tipo;dono;intcomb;posx;posy;broca;comb;seda;quente;recic;ligada
	
	String serializarMineradora(Mineradora min){
		String str = "";
		Location loc = min.getLoc();
		str += loc.getWorld().getName() + ";" + loc.getBlockX() + ";"+loc.getBlockY() + ";" + loc.getBlockZ() + ";";
		str += min.getTipo().toString()+";"+min.getDono()+";"+min.getCombustivel()+";"+min.getPosx()+";"+min.getPosy()+";";
		str += min.getBroca().toString() + ";" + min.getComb().toString() + ";" + min.isBrocaDeSeda()+";";
		str += min.isBrocaPontaQuente() + ";" + min.isReciclador() + ";" + min.isLigada();
		return str;
	}
	
	Mineradora desserializarMineradora(String s){
		try{
			String args[] = s.split(";");
			World mundo = Bukkit.getServer().getWorld(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			Location loc = new Location(mundo, x, y, z);
			Tipo tipo = Tipo.valueOf(args[4]);
			String dono = args[5];
			int nivelcombustivel = Integer.parseInt(args[6]);
			int posx = Integer.parseInt(args[7]);
			int posy = Integer.parseInt(args[8]);
			Broca broca = Broca.valueOf(args[9]);
			Combustivel comb = Combustivel.valueOf(args[10]);
			boolean seda = Boolean.valueOf(args[11]);
			boolean quente = Boolean.valueOf(args[12]);
			boolean recic = Boolean.valueOf(args[13]);
			boolean ligada = Boolean.valueOf(args[14]);
			Mineradora min = new Mineradora(loc, tipo, dono, nivelcombustivel,
					posx, posy, broca, comb, seda, quente, recic, ligada);
			plugin.getLogger().info("Mineradora carregada com sucesso!");
			return min;
		}catch(Exception e){
			plugin.getLogger().info("Erro ao carregar Mineradora!");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
