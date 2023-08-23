package com.sofrecom.cobli.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DESAT  extends Acte_traitement{

	//private String id_acte;
	
	private String zone;
	private String fi;
	private Date date_refus;
	private String motifReaffectation;
	private String statut_operatonnels;
	private Date date_reafictation;
	private String cog;
	private String ui;
	private Date date_liv_reafictation;
	

	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getFi() {
		return fi;
	}
	public void setFi(String fi) {
		this.fi = fi;
	}
	public String getCog() {
		return cog;
	}
	public void setCog(String cog) {
		this.cog = cog;
	}
	
	public String getMotifReaffectation() {
		return motifReaffectation;
	}
	public void setMotifReaffectation(String motifReaffectation) {
		this.motifReaffectation = motifReaffectation;
	}
	public String getStatut_operatonnels() {
		return statut_operatonnels;
	}
	public void setStatut_operatonnels(String statut_operatonnels) {
		this.statut_operatonnels = statut_operatonnels;
	}
	
	
	public String getUi() {
		return ui;
	}
	public void setUi(String ui) {
		this.ui = ui;
	}
	
	
	public Date getDate_refus() {
		return date_refus;
	}
	public void setDate_refus(Date date_refus) {
		this.date_refus = date_refus;
	}
	public Date getDate_reafictation() {
		return date_reafictation;
	}
	public void setDate_reafictation(Date date_reafictation) {
		this.date_reafictation = date_reafictation;
	}
	public Date getDate_liv_reafictation() {
		return date_liv_reafictation;
	}
	public void setDate_liv_reafictation(Date date_liv_reafictation) {
		this.date_liv_reafictation = date_liv_reafictation;
	}

	
	
	// affichage 
	@Override
	public String toString() {
		return "DESAT [ ZONE=" + zone + ", FI=" + fi + ", date_refus=" + date_refus
				+ ", motifReaffectation=" + motifReaffectation + ", statut_operatonnels=" + statut_operatonnels + ", date_reafictation="
				+ date_reafictation + ", COG=" + cog+ ", ui=" + ui + ", date_liv_reafictation=" + date_liv_reafictation
				+ "]";
	}

	
	// constructeur vide
	 public DESAT() {
	    }
	
// constructeur pour desat a traiter
public DESAT(String cog,String fi,String motif, String type_element, String commentaire, Prestation type_prestation, int quantite,
		String statutFacturation, Date dateReception, Date dateDeadline) {
	super(motif, type_element, commentaire, type_prestation, quantite, statutFacturation, dateReception, dateDeadline);
	// TODO Auto-generated constructor stub
	this.cog= cog;
	this.fi= fi;
	
}

// constructeur importer historique desat
public DESAT(String zONE,String fI,Date date_refus, String motifReaffectation,String statut_operatonnels,
		Date date_reafictation,String cOG, String ui, Date date_liv_reafictation, Prestation type_prestation, String type_element,
		Date dateReception, int quantite, 
		Date dateLivraison, Date dateValidation, String affectation, String commentaire,int duree,
		String refTacheBPU, String motif,Date dateDeadline, String priorite, String statutFacturation ) {
	super(refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation,
			affectation, duree, commentaire, motif,dateDeadline,priorite,statutFacturation);
	
	zone = zONE;
	fi = fI;
	this.date_refus = date_refus;
	this.motifReaffectation = motifReaffectation;
	this.statut_operatonnels = statut_operatonnels;
	this.date_reafictation = date_reafictation;
	cog = cOG;
	this.ui = ui;
	this.date_liv_reafictation = date_liv_reafictation;
}


// constructeur ajout dessat 
public DESAT(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception,
		Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif,
		String statutFacturation, Date dateReprise, String repriseFacturable, Date dateDeadline, String priorite, String zONE,String fI,
		Date date_refus, String motifReaffectation,String statut_operatonnels,
		Date date_reafictation,String cOG, String ui, Date date_liv_reafictation) {
	super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation,
			duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable, dateDeadline, priorite);
	// TODO Auto-generated constructor stub
	
	this.zone = zONE;
	this.fi = fI;
	this.date_refus = date_refus;
	this.motifReaffectation = motifReaffectation;
	this.statut_operatonnels = statut_operatonnels;
	this.date_reafictation = date_reafictation;
	this.cog = cOG;
	this.ui = ui;
	this.date_liv_reafictation = date_liv_reafictation;
}








	
	

	
	
	
	
	
}

