package com.sofrecom.cobli.models;


import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Interconnexion extends Acte_traitement{


    private String cog;
    private  String ui ;

    private Date dateFlux;
    private Date dateDepBan;
    private Date dateRetourBan;

   private String phase ;


    public Interconnexion(Prestation type_prestation, String type_element, int quantite, String statutFacturation, String motif, Date dateReception, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(type_prestation, type_element, quantite, statutFacturation, motif, dateReception);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String idacte, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(idacte);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(Prestation type_prestation, String type_element, int quantite, String statutFacturation, String motif, Date dateReception, Date dateDeadline, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(type_prestation, type_element, quantite, statutFacturation, motif, dateReception, dateDeadline);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation, Date dateDeadline, String priorite, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation, dateDeadline, priorite);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, Date dateDeadline, String priorite, int duree, String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, dateDeadline, priorite, duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String motif, String type_element, String commentaire, Prestation type_prestation, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(motif, type_element, commentaire, type_prestation);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, Date dateDeadline, String priorite, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, dateDeadline, priorite);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String idacte, String refTacheBPU, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(idacte, refTacheBPU);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String motif, String type_element, String commentaire, Prestation type_prestation, int quantite, String statutFacturation, Date dateReception, Date dateDeadline, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(motif, type_element, commentaire, type_prestation, quantite, statutFacturation, dateReception, dateDeadline);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, Date dateDeadline, String priorite, String StatutFacturation, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, dateDeadline, priorite, StatutFacturation);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite, Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree, String commentaire, String motif, String statutFacturation, Date dateReprise, String repriseFacturable, Date dateDeadline, String priorite, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison, dateValidation, affectation, duree, commentaire, motif, statutFacturation, dateReprise, repriseFacturable, dateDeadline, priorite);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public Interconnexion(String idacte, Prestation type_prestation, String cog, String ui, Date dateFlux, Date dateDepBan, Date dateRetourBan) {
        super(idacte, type_prestation);
        this.cog = cog;
        this.ui = ui;
        this.dateFlux = dateFlux;
        this.dateDepBan = dateDepBan;
        this.dateRetourBan = dateRetourBan;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public Interconnexion() {

    }


    public String getCog() {
        return cog;
    }

    public void setCog(String cog) {
        this.cog = cog;
    }

    public Date getDateFlux() {
        return dateFlux;
    }

    public void setDateFlux(Date dateFlux) {
        this.dateFlux = dateFlux;
    }

    public Date getDateDepBan() {
        return dateDepBan;
    }

    public void setDateDepBan(Date dateDepBan) {
        this.dateDepBan = dateDepBan;
    }

    public Date getDateRetourBan() {
        return dateRetourBan;
    }

    public void setDateRetourBan(Date dateRetourBan) {
        this.dateRetourBan = dateRetourBan;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
