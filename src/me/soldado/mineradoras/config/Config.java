package me.soldado.mineradoras.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.soldado.mineradoras.Main;

public class Config{
	
	public Main plugin;
	
	public Config(Main plugin)
	{
		this.plugin = plugin;
	}
	
	File configFile;
	FileConfiguration config;

	public int precobroca1;
	public int precobroca2;
	public int precobroca3;
	public int precocombustivel1;
	public int precocombustivel2;
	public int precocombustivel3;
	public int precomelhoria1;
	public int precomelhoria2;
	public int precomelhoria3;
	public int precoremover;
	
	public int porcentagemdescontovip;
	
	public boolean descontovip;
	
	private void iniciarValores(){
		precobroca1 = getInt("precobroca1");
		precobroca2 = getInt("precobroca2");
		precobroca3 = getInt("precobroca3");
		precocombustivel1 = getInt("precocombustivel1");
		precocombustivel2 = getInt("precocombustivel2");
		precocombustivel3 = getInt("precocombustivel3");
		precomelhoria1 = getInt("precomelhoria1");
		precomelhoria2 = getInt("precomelhoria2");
		precomelhoria3 = getInt("precomelhoria3");
		porcentagemdescontovip = getInt("porcentagemdescontovip");
		descontovip = getBoolean("descontovip");
		precoremover = getInt("precoremover");
	}
	
	public void iniciarConfig(){

		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), "config.yml");
		}
		if (!configFile.exists()) {
			plugin.saveResource("config.yml", false);
		}
		config = YamlConfiguration.loadConfiguration(configFile);
		iniciarValores();
	}
	
	private String getString(String s){
		return config.getString(s).replace("&", "§");
	}
	
	private int getInt(String s){
		return config.getInt(s);
	}
	
	private boolean getBoolean(String s){
		return config.getBoolean(s);
	}
}
