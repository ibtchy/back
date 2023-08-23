package com.sofrecom.cobli.models;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BPU {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String refTacheBPU; //version+type_prest+tarification
	private String type_element;
	private float PU; 
	private String tarif;
	private String dateDebut;
	private String dateValidite;
	private String dateExpiration;
	private String type_penalite;//variable ou fixe
	private String penalite;// pourc ou prix
	private String type_prestation;
	private String version;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRefTacheBPU() {
		return refTacheBPU;
	}
	public void setRefTacheBPU(String refTacheBPU) {
		this.refTacheBPU = refTacheBPU;
	}
	public String getType_element() {
		return type_element;
	}
	public void setType_element(String type_element) {
		this.type_element = type_element;
	}
	public float getPU() {
		return PU;
	}
	public void setPU(float pU) {
		PU = pU;
	}
	public String getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public String getDateValidite() {
		return dateValidite;
	}
	public void setDateValidite(String dateValidite) {
		this.dateValidite = dateValidite;
	}
	public String getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(String dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public String getType_penalite() {
		return type_penalite;
	}
	public void setType_penalite(String type_penalite) {
		this.type_penalite = type_penalite;
	}
	
	public String getPenalite() {
		return penalite;
	}
	public void setPenalite(String penalite) {
		this.penalite = penalite;
	}
	public String getType_prestation() {
		return type_prestation;
	}
	public void setType_prestation(String type_prestation) {
		this.type_prestation = type_prestation;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public BPU() {
		super();
	}
		
	public String getTarif() {
		return tarif;
	}
	public void setTarif(String tarif) {
		this.tarif = tarif;
	}
	
	public BPU(long id, String refTacheBPU, String type_element, float pU, String tarif, String dateDebut,
			String dateValidite, String dateExpiration, String type_penalite, String penalite, String type_prestation,
			String version) {
		super();
		this.id = id;
		this.refTacheBPU = refTacheBPU;
		this.type_element = type_element;
		PU = pU;
		this.tarif = tarif;
		this.dateDebut = dateDebut;
		this.dateValidite = dateValidite;
		this.dateExpiration = dateExpiration;
		this.type_penalite = type_penalite;
		this.penalite = penalite;
		this.type_prestation = type_prestation;
		this.version = version;
	}
	
	public BPU(String refTacheBPU, String type_element, float pU, String tarif, String dateDebut, String dateValidite,
			String dateExpiration, String type_penalite, String penalite, String type_prestation, String version) {
		super();
		this.refTacheBPU = refTacheBPU;
		this.type_element = type_element;
		PU = pU;
		this.tarif = tarif;
		this.dateDebut = dateDebut;
		this.dateValidite = dateValidite;
		this.dateExpiration = dateExpiration;
		this.type_penalite = type_penalite;
		this.penalite = penalite;
		this.type_prestation = type_prestation;
		this.version = version;
	}
	
	
	@Override
	public String toString() {
		return "BPU [id=" + id + ", refTacheBPU=" + refTacheBPU + ", type_element=" + type_element + ", PU=" + PU
				+ ", tarif=" + tarif + ", dateDebut=" + dateDebut + ", dateValidite=" + dateValidite
				+ ", dateExpiration=" + dateExpiration + ", type_penalite=" + type_penalite + ", penalite=" + penalite
				+ ", type_prestation=" + type_prestation + ", version=" + version + "]";
	}
	// Méthode de mise à jour de l'entité BPU
    public void update(BPU bpuToUpdate) {
        this.type_prestation = bpuToUpdate.getType_prestation();

        // Incrémenter la version
        this.version = this.version + 1;
    }
}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

