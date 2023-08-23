package com.sofrecom.cobli.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tarification {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String codeTarif;
	@ManyToOne
    @JoinColumn(name = "idPrestation")
    Prestation type_prestation;
	private long valeur;
	
	
	
	public String getCodeTarif() {
		return codeTarif;
	}
	public void setCodeTarif(String codeTarif) {
		this.codeTarif = codeTarif;
	}
	
	
	public Prestation getType_prestation() {
		return type_prestation;
	}
	public void setType_prestation(Prestation type_prestation) {
		this.type_prestation = type_prestation;
	}
	public long getValeur() {
		return valeur;
	}
	public void setValeur(long valeur) {
		this.valeur = valeur;
	}
	public Tarification() {
		super();
	}
	public Tarification(String codeTarif, Prestation type_prestation, long valeur) {
		super();
		this.codeTarif = codeTarif;
		this.type_prestation = type_prestation;
		this.valeur = valeur;
	}
	@Override
	public String toString() {
		return "Tarification [codeTarif=" + codeTarif + ", type_prestation=" + type_prestation + ", valeur=" + valeur
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	
	

}
