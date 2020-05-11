package me.soldado.mineradoras.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.soldado.mineradoras.Main;

public class Comandos implements CommandExecutor{

	public Main plugin;
	
	public Comandos(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("mineradora")){
			if(!sender.hasPermission("mineradora.adm")) return true;
			if(args.length == 0){
				sender.sendMessage("=-=-=-=-=-=-=-=-=-=");
				sender.sendMessage("§6Plugin de Mineradora versão 1.0");
				sender.sendMessage("§6Autor: Soldado_08");
				sender.sendMessage("=-=-=-=-=-=-=-=-=-=");
			}else if(args.length > 0){
				if(args[0].equalsIgnoreCase("combustivel")){
					if(args.length != 3) sender.sendMessage("§cSintaxe incorreta!");
					OfflinePlayer p;
					int amt;
					try{
						p = Bukkit.getServer().getOfflinePlayer(args[1]);
						amt = Integer.parseInt(args[2]);
						if(p.isOnline()){
							if(amt > 64){
								amt = 64;
								sender.sendMessage("§cValor máximo 64");
							}
							darCombustivel(p.getPlayer(), amt);
							sender.sendMessage("§aDado " + amt + " combustiveis para " + p.getName());
						}
					}catch(Exception e){
						sender.sendMessage("§cSintaxe incorreta!");
					}
				}else if(args[0].equalsIgnoreCase("min")){
					if(args.length != 3) sender.sendMessage("§cSintaxe incorreta!");
					OfflinePlayer p;
					int tipo;
					try{
						p = Bukkit.getServer().getOfflinePlayer(args[1]);
						tipo = Integer.parseInt(args[2]);
						if(tipo == 3 || tipo == 5 || tipo == 7 || tipo == 9){
							if(p.isOnline()){
								darMineradora(p.getPlayer(), tipo);
								sender.sendMessage("§aDada mineradora " + tipo + " para " + p.getName());
							}
						}else sender.sendMessage("§cTipo inexistente!");
					}catch(Exception e){
						sender.sendMessage("§cSintaxe incorreta!");
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public void darCombustivel(Player p, int amt){
		ItemStack item = new ItemStack(Material.SULPHUR);
		ItemMeta itemm = item.getItemMeta();
		itemm.setDisplayName("§eCombustivel");
		item.setItemMeta(itemm);
		item.setAmount(amt);
		if(plugin.core.temEspaco(p.getInventory())) p.getInventory().addItem(item);
		else p.getWorld().dropItem(p.getLocation(), item);
	}
	
	public void darMineradora(Player p, int tam){
		ItemStack item = new ItemStack(Material.DISPENSER);
		ItemMeta itemm = item.getItemMeta();
		String tamanho = "";
		switch(tam){
			case 3:
				tamanho = "3x3";
				break;
			case 5:
				tamanho = "5x5";
				break;
			case 7:
				tamanho = "7x7";
				break;
			case 9:
				tamanho = "9x9";
				break;
		}
		itemm.setDisplayName("§eMineradora " + tamanho);
		item.setItemMeta(itemm);
		if(plugin.core.temEspaco(p.getInventory())) p.getInventory().addItem(item);
		else p.getWorld().dropItem(p.getLocation(), item);
		
	}

	public void registrarComandos(){
		plugin.getCommand("mineradora").setExecutor(this);
	}
}
