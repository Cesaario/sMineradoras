package me.soldado.mineradoras.core;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Mineradora {
	
	Location loc;
	Tipo tipo;
	String dono;
	int combustivel = 0;
	int posx = 0;
	int posy = 2; //Começa como 2 para não destruir a schematic
	int ticks = 0;
	int ticksCombustivel = 0;
	Broca broca = Broca.FERRO;
	Combustivel comb = Combustivel.COMUM;
	boolean brocaDeSeda = false;
	boolean brocaPontaQuente = false;
	boolean reciclador = false;
	boolean ligada = false;
	Block cmdblock;

	public Mineradora(Location loc, Tipo tipo, String dono, int combustivel, int posx, int posy,
			Broca broca, Combustivel comb, boolean seda, boolean quente, boolean recic, boolean ligada){
		this.loc = loc;
		this.tipo = tipo;
		this.dono = dono;
		cmdblock = getCmdblock();
		this.combustivel = combustivel;
		if(posy < 5){
			if(tipo.equals(Tipo.x3) || tipo.equals(Tipo.x5)) this.posy = 3;
			if(tipo.equals(Tipo.x7) || tipo.equals(Tipo.x9)) this.posy = 4;
		}else this.posy = posy;
		this.posx = posx;
		this.broca = broca;
		this.comb = comb;
		this.brocaDeSeda = seda;
		this.brocaPontaQuente = quente;
		this.reciclador = recic;
		this.ligada = ligada;
		
	}
	
	public int getTicksCombustivel() {
		return ticksCombustivel;
	}
	public void setTicksCombustivel(int ticksCombustivel) {
		this.ticksCombustivel = ticksCombustivel;
	}
	public Block getCmdblock() {
		return cmdblock;
	}
	public void setCmdblock(Block cmdblock) {
		this.cmdblock = cmdblock;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public String getDono() {
		return dono;
	}
	public void setDono(String dono) {
		this.dono = dono;
	}
	public int getCombustivel() {
		return combustivel;
	}
	public void setCombustivel(int combustivel) {
		this.combustivel = combustivel;
	}
	public int getPosx() {
		return posx;
	}
	public void setPosx(int posx) {
		this.posx = posx;
	}
	public int getPosy() {
		return posy;
	}
	public void setPosy(int posy) {
		this.posy = posy;
	}
	public int getTicks() {
		return ticks;
	}
	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
	public Broca getBroca() {
		return broca;
	}
	public void setBroca(Broca broca) {
		this.broca = broca;
	}
	public Combustivel getComb() {
		return comb;
	}
	public void setComb(Combustivel comb) {
		this.comb = comb;
	}
	public boolean isBrocaDeSeda() {
		return brocaDeSeda;
	}
	public void setBrocaDeSeda(boolean brocaDeSeda) {
		this.brocaDeSeda = brocaDeSeda;
	}
	public boolean isBrocaPontaQuente() {
		return brocaPontaQuente;
	}
	public void setBrocaPontaQuente(boolean brocaPontaQuente) {
		this.brocaPontaQuente = brocaPontaQuente;
	}
	public boolean isReciclador() {
		return reciclador;
	}
	public void setReciclador(boolean reciclador) {
		this.reciclador = reciclador;
	}
	public boolean isLigada() {
		return ligada;
	}
	public void setLigada(boolean ligada) {
		this.ligada = ligada;
	}
	
	public Block getCmdBlock(){
		if(tipo.equals(Tipo.x3)) return loc.clone().add(0, 0, -1).getBlock();
		if(tipo.equals(Tipo.x5)) return loc.clone().add(0, 0, 1).getBlock();
		if(tipo.equals(Tipo.x7)) return loc.clone().add(0, 0, 1).getBlock();
		if(tipo.equals(Tipo.x9)) return loc.clone().add(0, 1, 1).getBlock();
		return null;
	}
	
}
