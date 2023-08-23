package com.sofrecom.cobli.models.request;

import java.util.Date;

public class Grafic_Resp {
    // Grafic
        private String idGrafic;
	    private String iar;
	    private String code_imb;
	    private String groupe_operation;
	    private Date dateTraitement;
	    private String statut_graphic;
	    private String traitement_effectue;
	    private String type_traitement;

    //Acte de traitement
    private String idactetrait;
    private String affectation;
	private int duree;
	private String commentaire;

    public Grafic_Resp() {
    }

    public Grafic_Resp(String idGrafic, String iar, String code_imb, String groupe_operation, Date dateTraitement,
            String statut_graphic, String traitement_effectue, String type_traitement, 
            String idactetrait, String affectation, int duree, String commentaire) {
        this.idGrafic = idGrafic;
        this.iar = iar;
        this.code_imb = code_imb;
        this.groupe_operation = groupe_operation;
        this.dateTraitement = dateTraitement;
        this.statut_graphic = statut_graphic;
        this.traitement_effectue = traitement_effectue;
        this.type_traitement = type_traitement;
        this.idactetrait = idactetrait;
        this.affectation = affectation;
        this.duree = duree;
        this.commentaire = commentaire;
    }
    
    

	public String getIdGrafic() {
        return idGrafic;
    }

    public void setIdGrafic(String idGrafic) {
        this.idGrafic = idGrafic;
    }

    public String getIar() {
        return iar;
    }

    public void setIar(String iar) {
        this.iar = iar;
    }

    public String getCode_imb() {
        return code_imb;
    }

    public void setCode_imb(String code_imb) {
        this.code_imb = code_imb;
    }

    public String getGroupe_operation() {
        return groupe_operation;
    }

    public void setGroupe_operation(String groupe_operation) {
        this.groupe_operation = groupe_operation;
    }


    public Date getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

	public String getStatut_graphic() {
        return statut_graphic;
    }

    public void setStatut_graphic(String statut_graphic) {
        this.statut_graphic = statut_graphic;
    }

    public String getTraitement_effectue() {
        return traitement_effectue;
    }

    public void setTraitement_effectue(String traitement_effectue) {
        this.traitement_effectue = traitement_effectue;
    }

    public String getType_traitement() {
        return type_traitement;
    }

    public void setType_traitement(String type_traitement) {
        this.type_traitement = type_traitement;
    }

    public String getIdactetrait() {
        return idactetrait;
    }

    public void setIdactetrait(String idactetrait) {
        this.idactetrait = idactetrait;
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

    

    

}
