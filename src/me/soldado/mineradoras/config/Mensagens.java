package me.soldado.mineradoras.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.soldado.mineradoras.Main;

public class Mensagens {
	
	public Main plugin;
	
	public Mensagens(Main plugin)
	{
		this.plugin = plugin;
	}
	
	File msgFile;
	FileConfiguration msgs;
	
	public String ligarMineradora;
	public String desligarMineradora;
	public String colocarMineradora;
	public String removerMineradora;
	public String desligarParaAbrirCombustivel;
	public String somenteDonoPodeAbrir;
	public String semDinheiro;
	public String brocaAtualMaisEficiente;
	public String combustivelAtualMaisEficiente;
	public String melhoriaJaComprada;
	public String melhoriaComprada;
	public String desligarParaAbrirLoja;
	public String mineradoraChegouNoFim;
	public String somenteDonoPodeControlar;
	public String localInvalido;
	public String mineradoraProxima;
	public String semPermissao;
	public String semPermissaoParaConstruir;
	
	private void iniciarValores(){
		ligarMineradora = getString("LigarMineradora");
		desligarMineradora = getString("DesligarMineradora");
		colocarMineradora = getString("ColocarMineradora");
		removerMineradora = getString("RemoverMineradora");
		desligarParaAbrirCombustivel = getString("DesligarParaAbrirCombustivel");
		somenteDonoPodeAbrir = getString("SomenteDonoPodeAbrir");
		semDinheiro = getString("SemDinheiro");
		brocaAtualMaisEficiente = getString("BrocaAtualMaisEficiente");
		combustivelAtualMaisEficiente = getString("CombustivelAtualMaisEficiente");
		melhoriaJaComprada = getString("MelhoriaJaComprada");
		melhoriaComprada = getString("MelhoriaComprada");
		desligarParaAbrirLoja = getString("DesligarParaAbrirLoja");
		mineradoraChegouNoFim = getString("MineradoraChegouNoFim");
		somenteDonoPodeControlar = getString("SomenteDonoPodeControlar");
		localInvalido = getString("LocalInvalido");
		mineradoraProxima = getString("MineradoraProxima");
		semPermissao = getString("SemPermissao");
		semPermissaoParaConstruir = getString("SemPermissaoParaConstruir");
	}
	
	public void iniciarMensagens(){

		if (msgFile == null) {
			msgFile = new File(plugin.getDataFolder(), "mensagens.yml");
		}
		if (!msgFile.exists()) {
			plugin.saveResource("mensagens.yml", false);
		}
		msgs = YamlConfiguration.loadConfiguration(msgFile);
		iniciarValores();
	}
	
	public String getString(String s){
		return msgs.getString(s).replace("&", "§");
	}
}
