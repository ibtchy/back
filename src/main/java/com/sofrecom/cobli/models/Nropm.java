package com.sofrecom.cobli.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
public class Nropm extends Acte_traitement{

    String uniteIntervention;

    String cog;


    Date dateReafectation;

    Date dateLivraisonReaffectation;

    String motifReaffectation;

    Boolean statutTravaux;

    @JsonManagedReference
    @OneToMany(mappedBy="nropm")
    private Set<Pm> pms;


    public Nropm(Prestation type_prestation, String type_element, int quantite, String statutFacturation, String motif,Date dateReception,Date dateDeadline, String cog) {
        super(type_prestation, type_element, quantite, statutFacturation,motif,dateReception,dateDeadline);
        this.cog = cog;
    }

    public Nropm(Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation,Date dateDeadline,String priorite, String uniteIntervention, String cog, Date dateReafectation, Date dateLivraisonReaffectation, String motifReaffectation, Boolean statutTravaux) {
        super(type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif,statutFacturation,dateDeadline,priorite);
        this.uniteIntervention = uniteIntervention;
        this.cog = cog;
        this.dateReafectation = dateReafectation;
        this.dateLivraisonReaffectation = dateLivraisonReaffectation;
        this.motifReaffectation = motifReaffectation;
        this.statutTravaux = statutTravaux;

    }

    public Nropm(String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable, String uniteIntervention, String cog, Date dateReafectation, Date dateLivraisonReaffectation, String motifReaffectation, Boolean statutTravaux) {
        super(refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable);
        this.uniteIntervention = uniteIntervention;
        this.cog = cog;
        this.dateReafectation = dateReafectation;
        this.dateLivraisonReaffectation = dateLivraisonReaffectation;
        this.motifReaffectation = motifReaffectation;
        this.statutTravaux = statutTravaux;
    }

    public Nropm(){

    }


    public Nropm(String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable, String uniteIntervention, String cog, Date dateReafectation, Date dateLivraisonReaffectation, String motifReaffectation, Boolean statutTravaux, Set<Pm> pms) {
        super(refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable);
        this.uniteIntervention = uniteIntervention;
        this.cog = cog;
        this.dateReafectation = dateReafectation;
        this.dateLivraisonReaffectation = dateLivraisonReaffectation;
        this.motifReaffectation = motifReaffectation;
        this.statutTravaux = statutTravaux;
        this.pms = pms;
    }

    public Nropm(Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation,Date dateDeadline,String priorite, String uniteIntervention, String cog, Boolean statutTravaux, Set<Pm> pms) {
        super(type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation,dateDeadline,priorite);
        this.uniteIntervention = uniteIntervention;
        this.cog = cog;
        this.pms = pms;
    }

    public String getUniteIntervention() {
        return uniteIntervention;
    }

    public void setUniteIntervention(String uniteIntervention) {
        this.uniteIntervention = uniteIntervention;
    }

    public String getCog() {
        return cog;
    }

    public void setCog(String cog) {
        this.cog = cog;
    }

    public Date getDateReafectation() {
        return dateReafectation;
    }

    public void setDateReafectation(Date dateReafectation) {
        this.dateReafectation = dateReafectation;
    }

    public Date getDateLivraisonReaffectation() {
        return dateLivraisonReaffectation;
    }

    public void setDateLivraisonReaffectation(Date dateLivraisonReaffectation) {
        this.dateLivraisonReaffectation = dateLivraisonReaffectation;
    }

    public String getMotifReaffectation() {
        return motifReaffectation;
    }

    public void setMotifReaffectation(String motifReaffectation) {
        this.motifReaffectation = motifReaffectation;
    }

    public Boolean getStatutTravaux() {
        return statutTravaux;
    }

    public void setStatutTravaux(Boolean statutTravaux) {
        this.statutTravaux = statutTravaux;
    }



    public Set<Pm> getPms() {
        return pms;
    }

    public void setPms(Set<Pm> pms) {
        this.pms = pms;
    }


}
