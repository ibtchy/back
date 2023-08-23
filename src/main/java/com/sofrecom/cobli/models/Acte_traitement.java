package com.sofrecom.cobli.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Indexed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Indexed
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "acte_traitement")
@JsonIdentityInfo(
generator = ObjectIdGenerators.PropertyGenerator.class,
property = "idacte")
public class Acte_traitement {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "idacte")
    private String idacte;

    @Column(name = "ref_tachebpu")
    private String refTacheBPU;

    
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idPrestation")
    Prestation typeprestation;
    
    //private String type_prestation;

    @Column(name = "type_element")
    private String type_element;

    @Column(name = "quantite")
    private int quantite;

    @Column(name = "date_reception")
    private Date dateReception;

    @Column(name = "date_livraison")
    private Date dateLivraison;

    @Column(name = "date_validation")
    private Date dateValidation;
    
  //@ManyToOne
    //@JoinColumn(name="affectation")
    //private Collaborateur affectation;
    @Column(name = "affectation")
    private String affectation;

    @Column(name = "duree")
    private int duree;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "motif")
    private String motif;

    @Column(name = "statut_facturation")
    private String statutFacturation;

    @Column(name = "date_reprise")
    private Date dateReprise;

    @Column(name = "reprise_facturable")
    private String repriseFacturable;
    
    private Date dateDeadline;
    private String priorite="P1";
     
    @ManyToOne
    @JoinColumn(name = "codeTarif")
    Tarification tarif;

	
	public String getIdacte() {
		return idacte;
	}
	public void setIdacte(String idacte) {
		this.idacte = idacte;
	}
	public String getRefTacheBPU() {
		return refTacheBPU;
	}
	public void setRefTacheBPU(String refTacheBPU) {
		this.refTacheBPU = refTacheBPU;
	}
	
	public Prestation getType_prestation() {
		return typeprestation;
	}
	public void setType_prestation(Prestation type_prestation) {
		this.typeprestation = type_prestation;
	}
	public String getType_element() {
		return type_element;
	}
	public void setType_element(String type_element) {
		this.type_element = type_element;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	
	public Date getDateLivraison() {
		return dateLivraison;
	}
	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}
	
	
	
	public Date getDateReception() {
		return dateReception;
	}
	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}
	public Date getDateValidation() {
		return dateValidation;
	}
	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}
	public String getAffectation() {
		return affectation;
	}
	public void setAffectation(String affectation) {
		this.affectation = affectation;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public String getStatutFacturation() {
		return statutFacturation;
	}
	public void setStatutFacturation(String statutFacturation) {
		this.statutFacturation = statutFacturation;
	}

	public Date getDateReprise() {
		return dateReprise;
	}
	public void setDateReprise(Date dateReprise) {
		this.dateReprise = dateReprise;
	}
	public String getRepriseFacturable() {
		return repriseFacturable;
	}
	public void setRepriseFacturable(String repriseFacturable) {
		this.repriseFacturable = repriseFacturable;
	}
	
	public Date getDateDeadline() {
		return dateDeadline;
	}
	public void setDateDeadline(Date dateDeadline) {
		this.dateDeadline = dateDeadline;
	}
	public String getPriorite() {
		return priorite;
	}
	public void setPriorite(String priorite) {
		this.priorite = priorite;
	}

	public Tarification getTarif() {
		return tarif;
	}
	public void setTarif(Tarification tarif) {
		this.tarif = tarif;
	}
	
//const using tarif
	public Acte_traitement(String idacte, String refTacheBPU, Prestation type_prestation, String type_element,
			int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
			String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable,
			Date dateDeadline, String priorite, Tarification tarif) {
		super();
		this.idacte = idacte;
		this.refTacheBPU = refTacheBPU;
		this.typeprestation = type_prestation;
		this.type_element = type_element;
		this.quantite = quantite;
		this.dateReception = dateReception;
		this.dateLivraison = dateLivraison;
		this.dateValidation = dateValidation;
		this.affectation = affectation;
		this.duree = duree;
		this.commentaire = commentaire;
		this.motif = motif;
		this.statutFacturation = statutFacturation;
		this.dateReprise = dateReprise;
		this.repriseFacturable = repriseFacturable;
		this.dateDeadline = dateDeadline;
		this.priorite = priorite;
		this.tarif = tarif;
	}
	
	
	public Acte_traitement(Prestation type_prestation, String type_element, int quantite,String statutFacturation,String motif, Date dateReception) {
		this.typeprestation = type_prestation;
		this.type_element = type_element;
		this.quantite = quantite;
		this.statutFacturation= statutFacturation;
		this.motif=motif;
		this.dateReception=dateReception;
		}
	
	public Acte_traitement(Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation) {
		this.typeprestation = type_prestation;
		this.type_element = type_element;
		this.quantite = quantite;
		this.dateReception = dateReception;
		this.dateLivraison = dateLivraison;
		this.dateValidation = dateValidation;
		this.affectation = affectation;
		this.duree = duree;
		this.commentaire = commentaire;
		this.motif = motif;
		this.statutFacturation = statutFacturation;
		}
	
	

	public Acte_traitement(String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
			Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
			String commentaire, String motif) {
		super();
		this.refTacheBPU = refTacheBPU;
		this.typeprestation = type_prestation;
		this.type_element = type_element;
		this.quantite = quantite;
		this.dateReception = dateReception;
		this.dateLivraison = dateLivraison;
		this.dateValidation = dateValidation;
		this.affectation = affectation;
		this.duree = duree;
		this.commentaire = commentaire;
		this.motif= motif;

	}



		public Acte_traitement(String idacte) {
		this.idacte = idacte;
		}

		public Acte_traitement(Prestation type_prestation, String type_element, int quantite, String statutFacturation, String motif, Date dateReception, Date dateDeadline) {
		this.typeprestation = type_prestation;
		this.type_element = type_element;
		this.quantite = quantite;
		this.statutFacturation= statutFacturation;
		this.motif=motif;
		this.dateReception=dateReception;
		this.dateDeadline=dateDeadline;
		}

		
		public Acte_traitement(Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation,Date dateDeadline,String priorite) {
		this.typeprestation = type_prestation;
		this.type_element = type_element;
		this.quantite = quantite;
		this.dateReception = dateReception;
		this.dateLivraison = dateLivraison;
		this.dateValidation = dateValidation;
		this.affectation = affectation;
		this.duree = duree;
		this.commentaire = commentaire;
		this.motif = motif;
		this.statutFacturation = statutFacturation;
		this.dateDeadline= dateDeadline;
		this.priorite=priorite;
		}
		
//------------------
		public Acte_traitement() {
			super();
		}
		public Acte_traitement( String idacte, String refTacheBPU, Prestation type_prestation,
				String type_element, int quantite, Date dateReception,Date dateLivraison,Date dateValidation,
				String affectation, int duree, String commentaire, String motif, String statutFacturation,
				Date dateReprise, String repriseFacturable) {
			super();
			this.idacte = idacte;
			this.refTacheBPU = refTacheBPU;
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.quantite = quantite;
			this.dateReception = dateReception;
			this.dateLivraison = dateLivraison;
			this.dateValidation = dateValidation;
			this.affectation = affectation;
			this.duree = duree;
			this.commentaire = commentaire;
			this.motif = motif;
			this.statutFacturation = statutFacturation;
			this.dateReprise = dateReprise;
			this.repriseFacturable = repriseFacturable;
		}
		

		public Acte_traitement(String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
				Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
				String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable) {
			super();
			this.refTacheBPU = refTacheBPU;
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.quantite = quantite;
			this.dateReception = dateReception;
			this.dateLivraison = dateLivraison;
			this.dateValidation = dateValidation;
			this.affectation = affectation;
			this.duree = duree;
			this.commentaire = commentaire;
			this.motif = motif;
			this.statutFacturation = statutFacturation;
			this.dateReprise = dateReprise;
			this.repriseFacturable = repriseFacturable;
		}
		@Override
		public String toString() {
			return "Acte_traitement [ idacte=" + idacte + ", refTacheBPU=" + refTacheBPU
					+ ", type_prestation=" + typeprestation + ", type_element=" + type_element + ", quantite=" + quantite
					+ ", dateReception=" + dateReception + ", dateLivraison=" + dateLivraison + ", dateValidation="
					+ dateValidation + ", affectation=" + affectation + ", duree=" + duree + ", commentaire=" + commentaire
					+ ", motif=" + motif + ", statutFacturation=" + statutFacturation + ", dateReprise=" + dateReprise
					+ ", repriseFacturable=" + repriseFacturable + "]";
		}

		
		
		
		
		
		public Acte_traitement(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
				Date dateReception, Date dateLivraison, Date dateValidation, String affectation, Date dateDeadline,
				String priorite, int duree, String commentaire, String motif, String statutFacturation, Date dateReprise,
				String repriseFacturable) {
			super();
			this.idacte = idacte;
			this.refTacheBPU = refTacheBPU;
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.quantite = quantite;
			this.dateReception = dateReception;
			this.dateLivraison = dateLivraison;
			this.dateValidation = dateValidation;
			this.affectation = affectation;
			this.dateDeadline = dateDeadline;
			this.priorite = priorite;
			this.duree = duree;
			this.commentaire = commentaire;
			this.motif = motif;
			this.statutFacturation = statutFacturation;
			this.dateReprise = dateReprise;
			this.repriseFacturable = repriseFacturable;
		}
		

		public Acte_traitement(String motif, String type_element, String commentaire,Prestation type_prestation ) {
			super();
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.commentaire = commentaire;
			this.motif = motif;
		}
		public Acte_traitement(String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
				Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
				String commentaire, String motif,Date dateDeadline,String priorite) {
			super();
			this.refTacheBPU = refTacheBPU;
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.quantite = quantite;
			this.dateReception = dateReception;
			this.dateLivraison = dateLivraison;
			this.dateValidation = dateValidation;
			this.affectation = affectation;
			this.duree = duree;
			this.commentaire = commentaire;
			this.motif= motif;
			this.dateDeadline= dateDeadline;
			this.priorite=priorite;

		}
		
		//Constr pour Desat
		
	

		public Acte_traitement(String idacte, String refTacheBPU) {
			super();
			this.idacte = idacte;
			this.refTacheBPU = refTacheBPU;
		}
		

		

		public Acte_traitement(String motif, String type_element, String commentaire,Prestation type_prestation, int quantite , String statutFacturation, Date dateReception, Date dateDeadline  ) {
			super();
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.commentaire = commentaire;
			this.motif = motif;
			this.quantite=quantite;
			this.statutFacturation= statutFacturation;
			this.dateReception = dateReception;
			this.dateDeadline = dateDeadline ;
			}
		
		
		public Acte_traitement(String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
				Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
				String commentaire, String motif,Date dateDeadline,String priorite, String StatutFacturation) {
			super();
			this.refTacheBPU = refTacheBPU;
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.quantite = quantite;
			this.dateReception = dateReception;
			this.dateLivraison = dateLivraison;
			this.dateValidation = dateValidation;
			this.affectation = affectation;
			this.duree = duree;
			this.commentaire = commentaire;
			this.motif= motif;
			this.dateDeadline= dateDeadline;
			this.priorite=priorite;
			this.statutFacturation= StatutFacturation;

		}
		public Acte_traitement(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
				Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
				String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable,
				Date dateDeadline, String priorite) {
			super();
			this.idacte=idacte;
			this.refTacheBPU = refTacheBPU;
			this.typeprestation = type_prestation;
			this.type_element = type_element;
			this.quantite = quantite;
			this.dateReception = dateReception;
			this.dateLivraison = dateLivraison;
			this.dateValidation = dateValidation;
			this.affectation = affectation;
			this.duree = duree;
			this.commentaire = commentaire;
			this.motif = motif;
			this.statutFacturation = statutFacturation;
			this.dateReprise = dateReprise;
			this.repriseFacturable = repriseFacturable;
			this.dateDeadline = dateDeadline;
			this.priorite = priorite;
		}
		public Acte_traitement(String idacte, Prestation type_prestation) {
			super();
			this.idacte = idacte;
			this.typeprestation = type_prestation;
		}
		
		
	
	
	
	
	
	
	
	

}
