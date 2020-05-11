package me.soldado.mineradoras;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.soldado.mineradoras.comandos.Comandos;
import me.soldado.mineradoras.config.Config;
import me.soldado.mineradoras.config.Mensagens;
import me.soldado.mineradoras.core.AntiBuild;
import me.soldado.mineradoras.core.CoreMineradora;
import me.soldado.mineradoras.core.LojaMelhorias;
import me.soldado.mineradoras.core.MenuGUI;
import me.soldado.mineradoras.core.Particulas;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public CoreMineradora core;
	public Comandos cmd;
	public Config cfg;
	public Mensagens msg;
	public Cronometro crn;
	public MenuGUI gui;
	public LojaMelhorias loja;
	public AntiBuild antbld;
	public Particulas particulas;
	public Backup backup;
	
	public Economy economy = null;
	public boolean RedProtect;
	public WorldGuardPlugin wg = null;
	
	public void onEnable(){
		core = new CoreMineradora(this);
		cmd = new Comandos(this);
		cfg = new Config(this);
		msg = new Mensagens(this);
		crn = new Cronometro(this);
		gui = new MenuGUI(this);
		loja = new LojaMelhorias(this);
		antbld = new AntiBuild(this);
		particulas = new Particulas(this);
		backup = new Backup(this);

		core.registrarEventos();
		gui.registrarEventos();
		loja.registrarEventos();
		antbld.registrarEventos();
		
		cfg.iniciarConfig();
		msg.iniciarMensagens();
		cmd.registrarComandos();
		
		crn.runTaskTimer(this, 0L, 10L);
		
		setupEconomy();
		checkRP();
		wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		
		backup.iniciarBackup();
		backup.carregar();

		this.getLogger().info("sMineradora ativado!!!");
		this.getLogger().info("Autor: Soldado_08");
	}
	
	public void onDisable(){
		this.getLogger().info("sMineradora desativado!!!");
		this.getLogger().info("Autor: Soldado_08");
		backup.salvar();
	}
	
	private boolean setupEconomy(){
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null);
	}    
	
	private void checkRP(){
		Plugin p = Bukkit.getPluginManager().getPlugin("RedProtect");
	    RedProtect = (p != null) && (p.isEnabled());
    }
}
