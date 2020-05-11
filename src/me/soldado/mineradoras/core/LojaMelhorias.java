package me.soldado.mineradoras.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.soldado.mineradoras.Main;
import me.soldado.mineradoras.ParticleEffect;

public class LojaMelhorias implements Listener {

	Main plugin;
	public LojaMelhorias(Main plugin){
		this.plugin = plugin;
	}
	
	public void abrirLoja(Player p){
		Inventory loja = Bukkit.getServer().createInventory(null, 27, "§aLoja de Melhorias");
		List<String> lore = new ArrayList<String>();
		int aux;
		
		ItemStack broca1 = new ItemStack(Material.DIAMOND);
		ItemMeta broca1m = broca1.getItemMeta();
		lore.add("§9Quebra §oum §r§9bloco a cada §odois segundos");
		aux = (int) (plugin.cfg.precobroca1 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precobroca1 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precobroca1);
		broca1m.setDisplayName("§6Comprar Broca de Diamante");
		broca1m.setLore(lore);
		broca1.setItemMeta(broca1m);
		
		ItemStack broca2 = new ItemStack(Material.QUARTZ);
		ItemMeta broca2m = broca2.getItemMeta();
		lore.clear();
		lore.add("§9Quebra §oum §r§9bloco a cada §odois segundos");
		aux = (int) (plugin.cfg.precobroca2 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precobroca2 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precobroca2);
		broca2m.setDisplayName("§6Comprar Broca de Platina");
		broca2m.setLore(lore);
		broca2.setItemMeta(broca2m);
		
		ItemStack broca3 = new ItemStack(Material.NETHER_STAR);
		ItemMeta broca3m = broca3.getItemMeta();
		lore.clear();
		lore.add("§9Quebra §oum §r§9bloco a cada §omeio segundo");
		aux = (int) (plugin.cfg.precobroca3 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precobroca3 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precobroca3);
		broca3m.setDisplayName("§6Comprar Broca de Adamantita");
		broca3m.setLore(lore);
		broca3.setItemMeta(broca3m);
		
		ItemStack combustivel1 = new ItemStack(Material.EMERALD);
		ItemMeta combustivel1m = combustivel1.getItemMeta();
		lore.clear();
		lore.add("§9Gasta §oum §r§9combustível a cada §oum minuto e meio");
		aux = (int) (plugin.cfg.precocombustivel1 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precocombustivel1 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precocombustivel1);
		combustivel1m.setDisplayName("§6Comprar Combustivel Econômico");
		combustivel1m.setLore(lore);
		combustivel1.setItemMeta(combustivel1m);
		
		ItemStack combustivel2 = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta combustivel2m = combustivel2.getItemMeta();
		lore.clear();
		lore.add("§9Gasta §oum §r§9combustível a cada §odois minutos");
		aux = (int) (plugin.cfg.precocombustivel2 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precocombustivel2 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precocombustivel2);
		combustivel2m.setDisplayName("§6Comprar Combustível Moderno");
		combustivel2m.setLore(lore);
		combustivel2.setItemMeta(combustivel2m);
		
		ItemStack combustivel3 = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta combustivel3m = combustivel3.getItemMeta();
		lore.clear();
		lore.add("§9Gasta §oum §r§9combustível a cada §otrês minutos");
		aux = (int) (plugin.cfg.precocombustivel3 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precocombustivel3 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precocombustivel3);
		combustivel3m.setDisplayName("§6Comprar Combustível Infinitum");
		combustivel3m.setLore(lore);
		combustivel3.setItemMeta(combustivel3m);
		
		ItemStack melhoria1 = new ItemStack(Material.STRING);
		ItemMeta melhoria1m = melhoria1.getItemMeta();
		lore.clear();
		lore.add("§9Quebra os blocos com toque suave");
		aux = (int) (plugin.cfg.precomelhoria1 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precomelhoria1 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precomelhoria1);
		melhoria1m.setDisplayName("§6Comprar Broca de Seda");
		melhoria1m.setLore(lore);
		melhoria1.setItemMeta(melhoria1m);
		
		ItemStack melhoria2 = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta melhoria2m = melhoria2.getItemMeta();
		lore.clear();
		lore.add("§9Quebra e assa os minérios ao mesmo tempo");
		aux = (int) (plugin.cfg.precomelhoria2 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precomelhoria2 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precomelhoria2);
		melhoria2m.setDisplayName("§6Comprar Broca Ponta Quente");
		melhoria2m.setLore(lore);
		melhoria2.setItemMeta(melhoria2m);
		
		ItemStack melhoria3 = new ItemStack(Material.SLIME_BALL);
		ItemMeta melhoria3m = melhoria3.getItemMeta();
		lore.clear();
		lore.add("§9Tem 50% de chance de §oduplicar§r§9 o minério ao quebrar");
		aux = (int) (plugin.cfg.precomelhoria3 * (1-(plugin.cfg.porcentagemdescontovip * 0.01)));
		if(p.hasPermission("mineradoras.lojavip")) lore.add("§aPreço: §r§o§m" + plugin.cfg.precomelhoria3 + "§r§o " + aux);
		else lore.add("§aPreço: §r§o" + plugin.cfg.precomelhoria3);
		melhoria3m.setDisplayName("§6Comprar Reciclador");
		melhoria3m.setLore(lore);
		melhoria3.setItemMeta(melhoria3m);
		
		ItemStack info1 = new ItemStack(Material.NAME_TAG);
		ItemMeta info1m = info1.getItemMeta();
		info1m.setDisplayName("§bMelhorias da Broca");
		info1.setItemMeta(info1m);
		
		ItemStack info2 = new ItemStack(Material.NAME_TAG);
		ItemMeta info2m = info2.getItemMeta();
		info2m.setDisplayName("§bMelhorias do Combustivel");
		info2.setItemMeta(info2m);
		
		ItemStack info3 = new ItemStack(Material.NAME_TAG);
		ItemMeta info3m = info3.getItemMeta();
		info3m.setDisplayName("§bOutras Melhorias");
		info3.setItemMeta(info3m);
		
		ItemStack voltar = new ItemStack(Material.BARRIER);
		ItemMeta voltarm = voltar.getItemMeta();
		voltarm.setDisplayName("§cVoltar");
		voltar.setItemMeta(voltarm);

		loja.setItem(0, broca1);
		loja.setItem(1, broca2);
		loja.setItem(2, broca3);
		loja.setItem(10, info1);
		loja.setItem(21, combustivel1);
		loja.setItem(22, combustivel2);
		loja.setItem(23, combustivel3);
		loja.setItem(13, info2);
		loja.setItem(6, melhoria1);
		loja.setItem(7, melhoria2);
		loja.setItem(8, melhoria3);
		loja.setItem(16, info3);
		loja.setItem(26, voltar);
		
		p.openInventory(loja);
	}
	
	@EventHandler
	public void click(InventoryClickEvent event){
		if(!event.getInventory().getTitle().contains("Loja de Melhorias")) return;
		Player p = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if(!plugin.gui.menuComando.containsKey(p)){
			p.sendMessage("§cErro!! Jogador não encontrado no HashMap");
			return;
		}
		Mineradora min = plugin.gui.menuComando.get(p);
		switch(event.getSlot()){
			case 0:
				if(nivelBroca(min.getBroca()) >= 2){
					brocaAtualMaisEficiente(p);
					return;
				}
				int preco = plugin.cfg.precobroca1;
				if(p.hasPermission("mineradora.lojavip")) preco *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco){
					semDinheiro(p);
					return;
				}
				min.setBroca(Broca.DIAMANTE);
				plugin.economy.withdrawPlayer(p, preco);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Broca de Diamante"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 1:
				if(nivelBroca(min.getBroca()) >= 3){
					brocaAtualMaisEficiente(p);
					return;
				}
				int preco2 = plugin.cfg.precobroca2;
				if(p.hasPermission("mineradora.lojavip")) preco2 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco2){
					semDinheiro(p);
					return;
				}
				min.setBroca(Broca.PLATINA);
				plugin.economy.withdrawPlayer(p, preco2);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Broca de Platina"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 2:
				if(nivelBroca(min.getBroca()) >= 4){ //Nunca deve ser verdadeiro
					brocaAtualMaisEficiente(p);
					return;
				}
				int preco3 = plugin.cfg.precobroca3;
				if(p.hasPermission("mineradora.lojavip")) preco3 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco3){
					semDinheiro(p);
					return;
				}
				min.setBroca(Broca.ADAMANTITA);
				plugin.economy.withdrawPlayer(p, preco3);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Broca de Adamantita"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 21:
				if(nivelCombustivel(min.getComb()) >= 2){
					combustivelAtualMaisEficiente(p);
					return;
				}
				int preco4 = plugin.cfg.precocombustivel1;
				if(p.hasPermission("mineradora.lojavip")) preco4 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco4){
					semDinheiro(p);
					return;
				}
				min.setComb(Combustivel.ECONOMICO);
				plugin.economy.withdrawPlayer(p, preco4);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Combustivel Economico"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 22:
				if(nivelCombustivel(min.getComb()) >= 3){
					combustivelAtualMaisEficiente(p);
					return;
				}
				int preco5 = plugin.cfg.precocombustivel2;
				if(p.hasPermission("mineradora.lojavip")) preco5 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco5){
					semDinheiro(p);
					return;
				}
				min.setComb(Combustivel.MODERNO);
				plugin.economy.withdrawPlayer(p, preco5);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Combustivel Moderno"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 23:
				if(nivelCombustivel(min.getComb()) >= 4){//Nunca deve ser verdadeiro
					combustivelAtualMaisEficiente(p);
					return;
				}
				int preco6 = plugin.cfg.precocombustivel3;
				if(p.hasPermission("mineradora.lojavip")) preco6 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco6){
					semDinheiro(p);
					return;
				}
				min.setComb(Combustivel.INFINITUM);
				plugin.economy.withdrawPlayer(p, preco6);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Combustivel Infinitum"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 6:
				if(min.isBrocaDeSeda()){
					melhoriaJaComprada(p);
					return;
				}
				int preco7 = plugin.cfg.precomelhoria1;
				if(p.hasPermission("mineradora.lojavip")) preco7 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco7){
					semDinheiro(p);
					return;
				}
				min.setBrocaDeSeda(true);
				plugin.economy.withdrawPlayer(p, preco7);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Broca de Seda"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 7:
				if(min.isBrocaPontaQuente()){
					melhoriaJaComprada(p);
					return;
				}
				int preco8 = plugin.cfg.precomelhoria2;
				if(p.hasPermission("mineradora.lojavip")) preco8 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco8){
					semDinheiro(p);
					return;
				}
				min.setBrocaPontaQuente(true);
				plugin.economy.withdrawPlayer(p, preco8);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Broca Ponta Quente"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 8:
				if(min.isReciclador()){
					melhoriaJaComprada(p);
					return;
				}
				int preco9 = plugin.cfg.precomelhoria3;
				if(p.hasPermission("mineradora.lojavip")) preco9 *= (1-(plugin.cfg.porcentagemdescontovip * 0.01));
				if(plugin.economy.getBalance(p) < preco9){
					semDinheiro(p);
					return;
				}
				min.setReciclador(true);
				plugin.economy.withdrawPlayer(p, preco9);
				p.sendMessage(plugin.msg.melhoriaComprada.replace("%melhoria%", "Reciclador"));
				p.closeInventory();
				efeitoCompra(p);
				break;
			case 26:
				plugin.gui.abrirMenu(p, min);
		}
	}

    public void registrarEventos(){
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    public int nivelBroca(Broca broca){
    	if(broca.equals(Broca.FERRO)) return 1;
    	if(broca.equals(Broca.DIAMANTE)) return 2;
    	if(broca.equals(Broca.PLATINA)) return 3;
    	if(broca.equals(Broca.ADAMANTITA)) return 4;
    	return -1;
    }
    
    public int nivelCombustivel(Combustivel comb){
    	if(comb.equals(Combustivel.COMUM)) return 1;
    	if(comb.equals(Combustivel.ECONOMICO)) return 2;
    	if(comb.equals(Combustivel.MODERNO)) return 3;
    	if(comb.equals(Combustivel.INFINITUM)) return 4;
    	return -1;
    }
    
    public void brocaAtualMaisEficiente(Player p){
		p.sendMessage(plugin.msg.brocaAtualMaisEficiente);
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
    }

    public void combustivelAtualMaisEficiente(Player p){
		p.sendMessage(plugin.msg.combustivelAtualMaisEficiente);
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
    }
    
    public void melhoriaJaComprada(Player p){
		p.sendMessage(plugin.msg.melhoriaJaComprada);
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
    }
    
    public void semDinheiro(Player p){
		p.sendMessage(plugin.msg.semDinheiro);
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
    }
    
    void efeitoCompra(Player p){
    	p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        ParticleEffect.VILLAGER_HAPPY.display(1, 1, 1, 0, 10, p.getLocation(), 5);
    }
	
}
