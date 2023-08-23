package com.sofrecom.cobli.models;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Attachements {
	public Attachements(String idacte, Prestation type_prestation, String typeelement,
			int quantite, float prix_BPU,
			float prix_total,String tarif) {
		super();
		this.idacte = idacte;
		this.type_prestation = type_prestation;
		this.typeelement = typeelement;
		this.quantite = quantite;
		this.prix_BPU = prix_BPU;
		this.prix_total = prix_total;
		this.tarif = tarif;
	}
	
	
		
	public Attachements(String idacte,String codeIMB, String codeBanbou, Prestation type_prestation, 
			String typeelement, int quantite, float prix_BPU, float prix_total, String tarif, int duree) {
		super();
		this.idacte = idacte;
		this.type_prestation = type_prestation;
		this.codeIMB = codeIMB;
		this.codeBanbou = codeBanbou;
		this.typeelement = typeelement;
		this.quantite = quantite;
		this.prix_BPU = prix_BPU;
		this.prix_total = prix_total;
		this.tarif = tarif;
		Duree = duree;
	}







	public Attachements(String idacte,String codeIMB, String codeBanbou, Prestation type_prestation, 
			String typeelement, int quantite, float prix_BPU, float prix_total, String tarif) {
		super();
		this.idacte = idacte;
		this.type_prestation = type_prestation;
		this.codeIMB = codeIMB;
		this.codeBanbou = codeBanbou;
		this.typeelement = typeelement;
		this.quantite = quantite;
		this.prix_BPU = prix_BPU;
		this.prix_total = prix_total;
		this.tarif = tarif;
	}




	private String idacte;
	private Prestation type_prestation;
	private String codeIMB;
	private String codeBanbou;
	private String typeelement;
	private int quantite;
	private float prix_BPU;
	private float prix_total;
	private String tarif;
	private int Duree;
	
	public Attachements() {
		super();
	}
	
	
	
	
	
	public int getDuree() {
		return Duree;
	}




	public void setDuree(int duree) {
		Duree = duree;
	}




	public float getPrix_BPU() {
		return prix_BPU;
	}



	public void setPrix_BPU(float prix_BPU) {
		this.prix_BPU = prix_BPU;
	}



	public float getPrix_total() {
		return prix_total;
	}



	public void setPrix_total(float prix_total) {
		this.prix_total = prix_total;
	}



	public String getIdacte() {
		return idacte;
	}
	public void setIdacte(String idacte) {
		this.idacte = idacte;
	}
	
	public Prestation getType_prestation() {
		return type_prestation;
	}



	public void setType_prestation(Prestation type_prestation) {
		this.type_prestation = type_prestation;
	}



	public String getTypeelement() {
		return typeelement;
	}
	public void setTypeelement(String typeelement) {
		this.typeelement = typeelement;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}



	public String getTarif() {
		return tarif;
	}



	public void setTarif(String tarif) {
		this.tarif = tarif;
	}
	
	



	public String getCodeIMB() {
		return codeIMB;
	}



	public void setCodeIMB(String codeIMB) {
		this.codeIMB = codeIMB;
	}



	public String getCodeBanbou() {
		return codeBanbou;
	}



	public void setCodeBanbou(String codeBanbou) {
		this.codeBanbou = codeBanbou;
	}



	@Override
	public String toString() {
		return "Attachements [idacte=" + idacte + ", type_prestation=" + type_prestation + ", codeIMB=" + codeIMB
				+ ", codeBanbou=" + codeBanbou + ", typeelement=" + typeelement + ", quantite=" + quantite
				+ ", prix_BPU=" + prix_BPU + ", prix_total=" + prix_total + ", tarif=" + tarif + "]";
	}





	
	
	

}
