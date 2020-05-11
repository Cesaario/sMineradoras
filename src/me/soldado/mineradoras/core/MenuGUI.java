package me.soldado.mineradoras.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.soldado.mineradoras.Main;

public class MenuGUI implements Listener {
	
	public Main plugin;
	
	public MenuGUI(Main plugin){
		this.plugin = plugin;
	}
	
	HashMap<Player, Mineradora> menuComando = new HashMap<Player, Mineradora>();
	
	@EventHandler
	public void abrir(PlayerInteractEvent event){
		
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!event.getClickedBlock().getType().equals(Material.COMMAND)) return;
		
		Block b = event.getClickedBlock();
		
		if(!plugin.core.commandBlocks.containsKey(b)) return;
		
		event.setCancelled(true);
		Mineradora min = plugin.core.commandBlocks.get(b);
		Player p = event.getPlayer();
		abrirMenu(p, min);
		menuComando.put(p, min);
		
	}
	
	@EventHandler
	public void fecharInventario(InventoryCloseEvent event){
		if(!event.getInventory().getName().contains("Controle da Mineradora")) return;
		Player p = (Player) event.getPlayer();
		if(menuComando.containsKey(p)) menuComando.remove(p);
		
	}
	
	@EventHandler
	public void click(InventoryClickEvent event){
		if(!event.getInventory().getName().contains("Controle da Mineradora")) return;
		Player p = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		event.setCancelled(true);
		if(item == null || item.getType().equals(Material.AIR) || !item.hasItemMeta()) return;
		
		Mineradora min = menuComando.get(p);
		String nome = item.getItemMeta().getDisplayName();
		
		if(!p.getName().equals(min.getDono())){
			p.sendMessage(plugin.msg.somenteDonoPodeControlar);
			event.setCancelled(true);
			return;
		}
		
		if(nome.contains("Ligar")){
			min.setLigada(true);
			p.sendMessage(plugin.msg.ligarMineradora);
			event.setCurrentItem(onoff(true));
			p.updateInventory();
		}else if(nome.contains("Desligar")){
			min.setLigada(false);
			p.sendMessage(plugin.msg.desligarMineradora);
			event.setCurrentItem(onoff(false));
			p.updateInventory();
		}else if(nome.contains("Remover")){
			if(p.hasPermission("mineradora.remover")){
				if(plugin.economy.getBalance(p.getName()) > plugin.cfg.precoremover){
					plugin.economy.withdrawPlayer(p, plugin.cfg.precoremover);
					plugin.core.mineradoras.remove(min.getLoc());
					plugin.core.commandBlocks.remove(min.getCmdblock());
					plugin.core.schematic(min.tipo, min.loc, false);
					p.sendMessage(plugin.msg.removerMineradora);
					int tamanho = 3;
					if(min.getTipo().equals(Tipo.x5)) tamanho = 5;
					else if(min.getTipo().equals(Tipo.x7)) tamanho = 7;
					else if(min.getTipo().equals(Tipo.x9)) tamanho = 9;
					plugin.cmd.darMineradora(p, tamanho);
					p.closeInventory();
				}else p.sendMessage(plugin.msg.semDinheiro);
			}else p.sendMessage(plugin.msg.semPermissao);
		}else if(nome.contains("Loja")){
				if(min.isLigada()){
				p.sendMessage(plugin.msg.desligarParaAbrirLoja);
				event.setCancelled(true);
				return;
			}
			plugin.loja.abrirLoja(p);
			if(!menuComando.containsKey(p)) menuComando.put(p, min);
		}
	}
	
	void abrirMenu(Player p, Mineradora min){
		Inventory controleMin = Bukkit.getServer().createInventory(null, InventoryType.DISPENSER, "§aControle da Mineradora");
		
		ItemStack info = new ItemStack(Material.NAME_TAG);
		ItemMeta infom = info.getItemMeta();
		infom.setDisplayName("§9Informações da Mineradora");
		infom.setLore(loreinfo(min));
		info.setItemMeta(infom);
		
		ItemStack loja = new ItemStack(Material.EMERALD);
		ItemMeta lojam = loja.getItemMeta();
		lojam.setDisplayName("§aLoja de Melhorias");
		loja.setItemMeta(lojam);
		
		ItemStack stats = new ItemStack(Material.COMPASS);
		ItemMeta statsm = stats.getItemMeta();
		statsm.setDisplayName("§6Estatísticas da Mineradora");
		stats.setItemMeta(statsm);
		
		ItemStack remover = new ItemStack(Material.BARRIER);
		ItemMeta removerm = remover.getItemMeta();
		removerm.setDisplayName("§cRemover a mineradora");
		List<String> lore = new ArrayList<>();
		lore.add("§cCuidado, ao remover a mineradora antes de ela acabar a mineração");
		lore.add("§ctodas as suas melhorias serão perdidas.");
		lore.add("§aPreço: §f" + plugin.cfg.precoremover);
		removerm.setLore(lore);
		remover.setItemMeta(removerm);

		controleMin.setItem(0, info);
		controleMin.setItem(2, loja);
		//controleMin.setItem(2, stats);
		controleMin.setItem(6, onoff(min.isLigada()));
		controleMin.setItem(8, remover);
		
		p.openInventory(controleMin);
	}
	
	ItemStack onoff(boolean on){
		ItemStack onoff;
		if(on){
			onoff = new ItemStack(Material.INK_SACK, 1, (byte) 1);
		}else onoff = new ItemStack(Material.INK_SACK, 1, (byte) 10);
		ItemMeta onoffm = onoff.getItemMeta();
		if(on) onoffm.setDisplayName("§cDesligar a mineradora");
		else onoffm.setDisplayName("§aLigar a mineradora");
		onoff.setItemMeta(onoffm);
		return onoff;
	}
	
	List<String> loreinfo(Mineradora min){
		List<String> lore = new ArrayList<String>();
		lore.add("§aDono: §f" + min.getDono());
		String tipo = min.getTipo().toString();
		lore.add("§aTamanho: §f" + tipo.charAt(tipo.length()-1) + tipo);
		lore.add("§aLocalização: §f" + min.getLoc().getBlockX() + ", " 
		+ min.getLoc().getBlockY() + ", " + min.getLoc().getBlockZ());
		lore.add("§aBroca: §f" + min.getBroca().toString());
		lore.add("§aCombustível: §f" + min.getComb().toString());
		lore.add("§aQuantidade de Combustível: §f" + min.combustivel);
		lore.add("§aTempo restante: §f" + temporestante(min));
		return lore;
	}
	
	String temporestante(Mineradora min){
		String tempo = "";
		int minutos = 0;
		int combustivel = min.getCombustivel();
		Combustivel comb = min.getComb();
		if(comb.equals(Combustivel.COMUM)) minutos = combustivel * 1;
		if(comb.equals(Combustivel.ECONOMICO)) minutos = (int) (combustivel * 1.5);
		if(comb.equals(Combustivel.MODERNO)) minutos = combustivel * 2;
		if(comb.equals(Combustivel.INFINITUM)) minutos = combustivel * 3;
		int horas = minutos / 60;
		minutos %= 60;
		tempo = horas + " hora";
		if(horas != 1) tempo += "s";
		tempo += " e " + minutos + " minuto";
		if(minutos != 1) tempo += "s";
		tempo += ".";
		return tempo;
	}

    public void registrarEventos(){
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
