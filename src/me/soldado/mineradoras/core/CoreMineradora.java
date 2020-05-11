package me.soldado.mineradoras.core;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BlockData;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.soldado.mineradoras.Main;
import me.soldado.mineradoras.ParticleEffect;
import me.soldado.mineradoras.ParticleEffect.ParticleData;

public class CoreMineradora implements Listener {

	public Main plugin;
	
	public CoreMineradora(Main plugin)
	{
		this.plugin = plugin;
	}

	public HashMap<Location, Mineradora> mineradoras = new HashMap<Location, Mineradora>();
	public HashMap<Block, Mineradora> commandBlocks = new HashMap<Block, Mineradora>();
	public HashMap<Player, Mineradora> menuCombustivel = new HashMap<Player, Mineradora>();

	
	@EventHandler
	public void colocar(BlockPlaceEvent event){
		if(event.isCancelled()) return;
		if(event.getBlock().getType().equals(Material.DISPENSER)){
			ItemStack item = event.getItemInHand();
			if(item != null && item.hasItemMeta() 
					&& item.getItemMeta().hasDisplayName() 
					&& item.getItemMeta().getDisplayName().contains("Mineradora")){
				Player p = event.getPlayer();
				Location loc = event.getBlock().getLocation();
				String nome = item.getItemMeta().getDisplayName();
				
				Tipo tipo = null;
				if(nome.contains("3x3")) tipo = Tipo.x3;
				if(nome.contains("5x5")) tipo = Tipo.x5;
				if(nome.contains("7x7")) tipo = Tipo.x7;
				if(nome.contains("9x9")) tipo = Tipo.x9;
				
				if(localValido(loc, tipo)){
					
					if(!estaProtegido(event.getBlock(), p.getName())){
				
						schematic(tipo, loc.clone().add(0, -1, 0), true);
		
						if(tipo.equals(Tipo.x3) || tipo.equals(Tipo.x5)) loc.add(0, 2, 0);
						if(tipo.equals(Tipo.x7) || tipo.equals(Tipo.x9)) loc.add(0, 3, 0);
						
						loc.getBlock().setType(Material.DISPENSER);
						Mineradora min = new Mineradora(loc, tipo, p.getName(), 0, 0, 0, Broca.FERRO,
								Combustivel.COMUM, false, false, false, false);
						
						mineradoras.put(min.getLoc(), min);
						commandBlocks.put(min.getCmdBlock(), min);
						
						p.sendMessage(plugin.msg.colocarMineradora);
						
						event.getBlock().setType(Material.AIR);
					
					}else{
						event.setCancelled(true);
						p.sendMessage(plugin.msg.semPermissaoParaConstruir);
					}
					
				}else{
					event.setCancelled(true);
					p.sendMessage(plugin.msg.localInvalido);
				}
			}
		}
	}
	
	/*@EventHandler
	public void retirar(BlockBreakEvent event){
		if(event.getBlock().getType().equals(Material.DISPENSER)){
			Location loc = event.getBlock().getLocation();
			if(mineradoras.containsKey(loc)){
				Mineradora min = mineradoras.get(loc);
				mineradoras.remove(loc);
				commandBlocks.remove(min.getCmdblock());
				//plugin.getServer().broadcastMessage("Removida");
			}
		}
	}*/
	
	@EventHandler
	public void abrirCombustivel(PlayerInteractEvent event){
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!event.getClickedBlock().getType().equals(Material.DISPENSER)) return;
		
		Block b = event.getClickedBlock();
		
		if(!plugin.core.mineradoras.containsKey(b.getLocation())) return;
		
		Mineradora min = plugin.core.mineradoras.get(b.getLocation());
		Player p = event.getPlayer();
		
		if(min.isLigada()){
			p.sendMessage(plugin.msg.desligarParaAbrirCombustivel);
			event.setCancelled(true);
			return;
		}

		if(!min.getDono().equals(p.getName())){
			p.sendMessage(plugin.msg.somenteDonoPodeAbrir);
			event.setCancelled(true);
			return;
		}
		
		Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.HOPPER, "§cCombustivel");
		int amt = min.getCombustivel();
		
		ItemStack comb = new ItemStack(Material.SULPHUR);
		ItemMeta combm = comb.getItemMeta();
		combm.setDisplayName("§eCombustivel");
		comb.setItemMeta(combm);
		for(int i = 0; i < 5 && amt > 0; i++){
			int aux;
			if(amt > 64) aux = 64;
			else aux = amt;
			comb.setAmount(aux);
			inv.setItem(i, comb);
			amt -= aux;
		}
		
		p.openInventory(inv);
		menuCombustivel.put(p, min);
		event.setCancelled(true);
	}
	
	@EventHandler
	public void fecharCombustivel(InventoryCloseEvent event){
		if(!event.getInventory().getType().equals(InventoryType.HOPPER)) return;
		if(!event.getInventory().getTitle().contains("Combustivel")) return;
		
		Inventory inv = event.getInventory();
		Player p = (Player) event.getPlayer();
		
		int amt = 0;
		for(int i = 0; i < 5; i++){
			if(inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR && inv.getItem(i).getAmount() > 0){
				amt += inv.getItem(i).getAmount();
			}
		}
		
		Mineradora min = menuCombustivel.get(p);
		min.setCombustivel(amt);
		menuCombustivel.remove(p);
	}
	
	@EventHandler
	public void invClick(InventoryClickEvent event){
		if(!event.getInventory().getType().equals(InventoryType.HOPPER)) return;
		if(!event.getInventory().getTitle().contains("Combustivel")) return;
		if(event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) event.setCancelled(true);
		ItemStack item = event.getCursor();
		if(item == null || item.getType().equals(Material.AIR)) return;
		if(event.getSlot() < 5){
			if(!item.hasItemMeta()){
				event.setCancelled(true);
				return;
			}
			if(!item.getItemMeta().hasDisplayName()){
				event.setCancelled(true);
				return;
			}
			if(item.getItemMeta().getDisplayName().equals("Combustivel")){
				event.setCancelled(true);
				return;
			}
			if(!item.getType().equals(Material.SULPHUR)){
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event){
		if(menuCombustivel.containsKey(event.getPlayer())) menuCombustivel.remove(event.getPlayer());
	}
	
	public void schematic(Tipo tipo, Location loc, boolean colocar){
		if(!colocar){
			if(tipo.equals(Tipo.x3) || tipo.equals(Tipo.x5)) loc.add(0, -3, 0);
			if(tipo.equals(Tipo.x7) || tipo.equals(Tipo.x9)) loc.add(0, -4, 0);
		}
		WorldEditPlugin worldEditPlugin = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
		String stipo = tipo.toString();
		String nomeschematic = stipo.charAt(stipo.length()-1) + stipo;
		if(!colocar) nomeschematic += "clear";
		File schematic = new File(plugin.getDataFolder() + File.separator + "/schematics/"+nomeschematic+".schematic");
		EditSession session = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 10000);
		try{
			CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(schematic).load(schematic);
			clipboard.paste(session, new Vector(loc.getX(), loc.getY() + 1, loc.getZ()), colocar);
		}
		catch (IOException | MaxChangedBlocksException | DataException  e)
		{
			e.printStackTrace();
		}
	}
   
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
      Material tipo = event.getBlock().getType();
      if(tipo.equals(Material.WATER) || tipo.equals(Material.STATIONARY_WATER) ||
    		  tipo.equals(Material.LAVA) || tipo.equals(Material.STATIONARY_LAVA)){
    	  if(proximoDeMineradora(event.getBlock().getLocation(), true)) event.setCancelled(true);
      }
    }
	
	public void tick(Mineradora min){
		if(min.getCombustivel() <= 0) return;
		if(min.getTicksCombustivel() >= getTicksCombustivel(min.getComb())){
			min.setTicksCombustivel(0);
			min.setCombustivel(min.getCombustivel() - 1);
		}else min.setTicksCombustivel(min.getTicksCombustivel() + 1);
		if(min.getTicks() >= getTicks(min.getBroca())){
			min.setTicks(0);
			quebrar(min);
		}else min.setTicks(min.getTicks() + 1);
	}

	public void quebrar(Mineradora min){
		Location loc = min.getLoc();
		Location minLoc = getPosicao(loc.clone(), min.getPosx(), min.getPosy(), min.getTipo());
		if(minLoc.getBlock().getType().equals(Material.AIR)){
			incrementarPosx(min);
			quebrar(min);
			return;
		}
		if(minLoc.getBlock().getType().equals(Material.BEDROCK)){
			min.setLigada(false);
			OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(min.getDono());
			if(p.isOnline()) p.getPlayer().sendMessage(plugin.msg.mineradoraChegouNoFim);
			return;
		}
		incrementarPosx(min);

		Block b = minLoc.getBlock();
		
		if(estaProtegido(b, min.getDono())) return;

		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
		Collection<ItemStack> drops = b.getDrops(pick); //Silk touch não funcionou
		for(ItemStack item : drops){
			if(min.isBrocaPontaQuente()){
				if(item.getType().equals(Material.IRON_ORE)) item.setType(Material.IRON_INGOT);
				if(item.getType().equals(Material.GOLD_ORE)) item.setType(Material.GOLD_INGOT);
			}
			if(min.isBrocaDeSeda()){
				if(b.getType().equals(Material.STONE)) item.setType(Material.STONE);
				if(b.getType().equals(Material.DIAMOND_ORE)) item.setType(Material.DIAMOND_ORE);
				if(b.getType().equals(Material.REDSTONE_ORE)) item.setType(Material.REDSTONE_ORE);
				if(b.getType().equals(Material.EMERALD_ORE)) item.setType(Material.EMERALD_ORE);
				if(b.getType().equals(Material.COAL_ORE)) item.setType(Material.COAL_ORE);
				if(b.getType().equals(Material.LAPIS_ORE)) item.setType(Material.LAPIS_ORE);
			}
			if(min.isReciclador()){
				int rand = (int) (Math.random() * 100);
				if(rand < 50){
					item.setAmount(item.getAmount() * 2);
				}
			}
			Chest c = getChest(min);
			if(c != null){
				c.getInventory().addItem(item);
			}else b.getWorld().dropItem(b.getLocation(), item);
		}
		b.setType(Material.AIR);
		plugin.particulas.laser(min.getLoc().clone(), b.getLocation().clone());
		b.getWorld().playSound(b.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 1);
		ParticleEffect.SMOKE_NORMAL.display(1, 1, 1, 0, 10, b.getLocation(), 20);
	}
	
	@SuppressWarnings("deprecation")
	boolean estaProtegido(Block b, String dono) {
		Location loc = b.getLocation();
		boolean aux = true;
		/*Region r = getRegion(loc);
		if(r == null) return true;
		
		for(String s : r.getAdmins()){
			if(s.equalsIgnoreCase(dono)) aux = false;
		}
		for(String s : r.getLeaders()){
			if(s.equalsIgnoreCase(dono)) aux = false;
		}*/
		boolean wg = true;
		ApplicableRegionSet set = plugin.wg.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
		OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(dono);
		LocalPlayer lp = plugin.wg.wrapOfflinePlayer(p);
		wg = !set.testState(lp, DefaultFlag.BUILD);
		if(set.size() == 0) wg = false;
		if(wg) return true;
		return aux;
	}

	void incrementarPosx(Mineradora min){
		if(min.getPosx() >= Math.pow(getTamanho(min.getTipo()), 2)-1){
			min.setPosx(0);
			min.setPosy(min.getPosy() + 1);
		}else{
			min.setPosx(min.getPosx() + 1);
		}
	}
	
	Location getPosicao(Location loc, int posx, int posy, Tipo tipo){
		int tamanho = getTamanho(tipo);
		if(tamanho == 0){
			plugin.getLogger().severe("Tamanho inválido!!!");
			return null;
		}
		int metade = (tamanho - 1)/2;
		int x = posx % tamanho;
		int y = (int) posx/tamanho;
		loc.add(-metade, 0, -metade);
		loc.add(x, -posy, y);
		return loc;
	}
	
	int getTamanho(Tipo tipo){
		switch(tipo){
			case x3:
				return 3;
			case x5:
				return 5;
			case x7:
				return 7;
			case x9:
				return 9;
		}
		return 0;
	}
	
	int getTicks(Broca broca){
		switch(broca){
			case FERRO:
				return 6;
			case DIAMANTE:
				return 4;
			case PLATINA:
				return 2;
			case ADAMANTITA:
				return 1;
		}
		return 10; //(?)
	}
	
	int getTicksCombustivel(Combustivel comb){
		switch(comb){
			case COMUM:
				return 120;
			case ECONOMICO:
				return 180;
			case MODERNO:
				return 240;
			case INFINITUM:
				return 360;
		}
		return 60; //(?)
	}
	
	Chest getChest(Mineradora min){
		org.bukkit.util.Vector vec = null;
		switch(min.getTipo()){
			case x3:
				vec = new org.bukkit.util.Vector(0, -2, -1);
				break;
			case x5:
				vec = new org.bukkit.util.Vector(0, -2, 2);
				break;
			case x7:
				vec = new org.bukkit.util.Vector(-1, -3, 3);
				break;
			case x9:
				vec = new org.bukkit.util.Vector(-2, -3, 4);
				break;
		}
		Block b = min.getLoc().clone().add(vec).getBlock();
		Chest c = (Chest) b.getState();
		if(temEspaco(c.getInventory())){
			return c;
		}else{
			if(min.getTipo().equals(Tipo.x9)){
				b = b.getLocation().add(4, 0, 0).getBlock();
				Chest c2 = (Chest) b.getState();
				if(!temEspaco(c2.getInventory())){
					return null;
				}else return c2;
			}
			return null;
		}
	}

	public boolean temEspaco(Inventory inv){
		for(int i = 0; i < inv.getSize(); i++){
			if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) return true;
		}
		return false;
	}
	
	/*public Region getRegion(Location loc){
		Region r = RedProtect.get().rm.getTopRegion(loc);
	    return r;
	}*/
	
	public boolean localValido(Location l, Tipo tipo){
		Location loc = l.clone();
		int raio = 0;
		int altura = 0;
		switch(tipo){
			case x3:
				raio = 5;
				altura = 7;
				break;
			case x5:
				raio = 7;
				altura = 9;
				break;
			case x7:
				raio = 9;
				altura = 12;
				break;
			case x9:
				raio = 11;
				altura = 15;
				break;
		}
		int metade = (raio - 1)/2;
		loc.add(-metade, 0, -metade);
		boolean valido = true;
		int dispenserCounter = 0;
		for(int i = 0; i < raio; i++){
			for(int j = 0; j < altura; j++){
				for(int k = 0; k < raio; k++){
					Block b = loc.getBlock();
					if(b.getType().equals(Material.DISPENSER)) dispenserCounter++;
					if(!b.getType().equals(Material.AIR)){
						if(dispenserCounter > 1 || b.getType() != Material.DISPENSER) valido = false;
					}
					loc.add(0, 0, 1);
				}
				loc.add(0, 1, -raio);
			}
			loc.add(1, -altura, 0);
		}
		return valido;
	}
	
	boolean proximoDeMineradora(Location l, boolean ignorarY){
		for(Location loc : mineradoras.keySet()){
			Mineradora min = mineradoras.get(loc);
			Location lo = l.clone();
			if(ignorarY) lo.setY(loc.getY());
			int raio = 0;
			switch(min.getTipo()){
				case x3:
					raio = 5;
					break;
				case x5:
					raio = 7;
					break;
				case x7:
					raio = 9;
					break;
				case x9:
					raio = 11;
					break;
			}
			if(loc.getWorld().equals(l.getWorld())){
				if(l.distance(loc) < raio) return true;
			}
		}
		return false;
	}
	
    public void registrarEventos(){
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
