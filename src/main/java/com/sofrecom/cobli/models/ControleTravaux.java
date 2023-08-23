package com.sofrecom.cobli.models;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ControleTravaux extends Acte_traitement {
		
	private String ui;
	private String cog;
	private String fi;
	private String typeTravaux;
	private Date dateFlux;
	private Date dateReafictation;
	private Date datelivReafictation;
	private String motifReaffectation;

	public String getUi() {
		return ui;
	}
	public void setUi(String ui) {
		this.ui = ui;
	}
	
	public String getCog() {
		return cog;
	}
	public void setCog(String cog) {
		this.cog = cog;
	}
	public String getFi() {
		return fi;
	}
	public void setFi(String fi) {
		this.fi = fi;
	}
	public Date getDateFlux() {
		return dateFlux;
	}
	public void setDateFlux(Date dateFlux) {
		this.dateFlux = dateFlux;
	}
	
	public Date getDateReafictation() {
		return dateReafictation;
	}
	public void setDateReafictation(Date dateReafictation) {
		this.dateReafictation = dateReafictation;
	}
	public Date getDatelivReafictation() {
		return datelivReafictation;
	}
	public void setDatelivReafictation(Date datelivReafictation) {
		this.datelivReafictation = datelivReafictation;
	}
		
	public String getTypeTravaux() {
		return typeTravaux;
	}
	public void setTypeTravaux(String typeTravaux) {
		this.typeTravaux = typeTravaux;
	}
	
	public String getMotifReaffectation() {
		return motifReaffectation;
	}
	public void setMotifReaffectation(String motifReaffectation) {
		this.motifReaffectation = motifReaffectation;
	}
	//Constr vide
	public ControleTravaux() {
		
	}
	

	
//Constructeur Add	et Import Hist
	public ControleTravaux(String idacte, String refTacheBPU, Prestation type_prestation, String type_element,
			int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation,
			int duree, String commentaire, String motif,String priorite,
			Date dateDeadline, String statutFacturation, String repriseFacturable, String cog,
			Date dateReprise,  String ui,String fi, Date dateFlux,
			Date dateReafictation, Date datelivReafictation, String typeTravaux, String motifReaffectation) {
		super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison,
				dateValidation, affectation, dateDeadline, priorite, duree, commentaire, motif, statutFacturation,
				dateReprise, repriseFacturable);
		this.ui = ui;
		this.cog = cog;
		this.fi = fi;
		this.dateFlux = dateFlux;
		this.dateReafictation = dateReafictation;
		this.datelivReafictation = datelivReafictation;
		this.typeTravaux = typeTravaux;
		this.motifReaffectation = motifReaffectation;


	}

	// Constr Import SPI
	public ControleTravaux(String type_element, String statutFacturation, String motif, String ui, String cog,
			Prestation type_prestation,  int quantite,  String fi, Date dateReception, Date dateFlux, Date dateReafictation) {
		super(type_prestation, type_element, quantite, statutFacturation, motif, dateReception);
		this.ui = ui;
		this.cog = cog;
		this.fi = fi;
		this.dateFlux = dateFlux;
		this.dateReafictation = dateReafictation;
	}
	
	@Override
	public String toString() {
		return "ControleTravaux [ui=" + ui + ", cog=" + cog + ", fi=" + fi + ", typeTravaux=" + typeTravaux
				+ ", dateFlux=" + dateFlux + ", dateReafictation=" + dateReafictation + ", datelivReafictation="
				+ datelivReafictation + ", motifReaffectation=" + motifReaffectation + "]";
	}
	
	
	
	
	
	public ControleTravaux(String idacte, String refTacheBPU, Prestation type_prestation, String type_element,
			int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
			String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable,
			Date dateDeadline, String priorite, Tarification tarif, String ui, String cog, String fi, Date dateFlux,
			String motifReaffectation, Date dateReafictation, Date datelivReafictation) {
		super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison,
				dateValidation, affectation, duree, commentaire, motif, statutFacturation, dateReprise,
				repriseFacturable, dateDeadline, priorite, tarif);
		this.ui = ui;
		this.cog = cog;
		this.fi = fi;
		this.dateFlux = dateFlux;
		this.motifReaffectation = motifReaffectation;
		this.dateReafictation = dateReafictation;
		this.datelivReafictation = datelivReafictation;
	}
	 // constructeur ajout 
public ControleTravaux(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception,
		Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif,
		String statutFacturation, Date dateReprise, String repriseFacturable, Date dateDeadline, String priorite, String ui, String cog, String fi, Date dateFlux,
			String motifReaffectation, Date dateReafictation, Date datelivReafictation,String typeTravaux) {
	super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation,
			duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable, dateDeadline, priorite);
	// TODO Auto-generated constructor stub
	
		this.ui = ui;
		this.cog = cog;
		this.fi = fi;
		this.dateFlux = dateFlux;
		this.motifReaffectation = motifReaffectation;
		this.dateReafictation = dateReafictation;
		this.datelivReafictation = datelivReafictation;
		this.typeTravaux = typeTravaux;
}

// const import spi

 
public ControleTravaux(String cog,String fi,String motif, String type_element, String commentaire, Prestation type_prestation, int quantite,
		String statutFacturation, Date dateReception, Date dateDeadline,Date dateFlux, String typeTravaux) {
	super(motif, type_element, commentaire, type_prestation, quantite, statutFacturation, dateReception, dateDeadline);
	// TODO Auto-generated constructor stub
	this.cog= cog;
	this.fi= fi;
	this.dateFlux = dateFlux;
	this.typeTravaux= typeTravaux;
	
}



	

	
	

}
