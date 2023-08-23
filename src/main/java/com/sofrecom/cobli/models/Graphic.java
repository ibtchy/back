package com.sofrecom.cobli.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Graphic extends Acte_traitement{
	    
	    private String idGrafic;
	    private String iar;
	    private String code_imb;
	    private String groupe_operation;
	    private Date dateTraitement;
	    private String statut_graphic;
	    private String traitement_effectue;
	    private String type_traitement;
		private boolean active;

	    
		public String getidGrafic() {
	        return idGrafic;
	    }

	    public void setidGrafic(String idGrafic) {
	        this.idGrafic = idGrafic;
	    }

	    public String getIar() {  
	        return this.iar;
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

	    public String getgroupe_operation() {
	        return groupe_operation;
	    }

	    public void setgroupe_operation(String groupe_operation) {
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

	    

	    @Override
		public String toString() {
			return "Graphic [idGrafic=" + idGrafic + ", iar=" + iar + ", code_imb=" + code_imb + ", groupe_operation="
					+ groupe_operation + ", dateTraitement=" + dateTraitement + ", statut_graphic=" + statut_graphic
					+ ", traitement_effectue=" + traitement_effectue + ", type_traitement=" + type_traitement
					+ ", active=" + active + "]";
		}

	    
	    
		public Graphic(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
				Date dateReception, Date dateLivraison, Date dateValidation, String affectation, Date dateDeadline,
				String priorite, int duree, String commentaire, String motif, String statutFacturation,
				Date dateReprise, String repriseFacturable, String idGrafic, String iar, String code_imb,
				String groupe_operation, Date dateTraitement, String statut_graphic, String traitement_effectue,
				String type_traitement, boolean active) {
			super(idacte, refTacheBPU, type_prestation, type_element, quantite, dateReception, dateLivraison,
					dateValidation, affectation, dateDeadline, priorite, duree, commentaire, motif, statutFacturation,
					dateReprise, repriseFacturable);
			this.idGrafic = idGrafic;
			this.iar = iar;
			this.code_imb = code_imb;
			this.groupe_operation = groupe_operation;
			this.dateTraitement = dateTraitement;
			this.statut_graphic = statut_graphic;
			this.traitement_effectue = traitement_effectue;
			this.type_traitement = type_traitement;
			this.active = active;
		}

		/*public Graphic(String idacte, String refTacheBPU, Prestation type_prestation, String type_element, int quantite,
				Date dateReception, Date dateLivraison, Date dateValidation, String affectation, int duree,
				String commentaire, String motif, String statutFacturation, Date dateReprise,
				String repriseFacturable,String idGrafic, String iar, String code_imb, String groupe_operation,
				Date dateTraitement, String statut_graphic, String traitement_effectue, String type_traitement, 
				boolean active) {
			super(idacte,refTacheBPU,type_prestation,type_element,quantite,dateReception,dateLivraison,dateValidation,affectation,duree,commentaire,motif,statutFacturation,dateReprise,repriseFacturable);
			this.idGrafic = idGrafic;
			this.iar = iar;
			this.code_imb = code_imb;
			this.groupe_operation = groupe_operation;
			this.dateTraitement = dateTraitement;
			this.statut_graphic = statut_graphic;
			this.traitement_effectue = traitement_effectue;
			this.type_traitement = type_traitement;
			this.active = active;
		}*/
		
		
		public Graphic() {
	    }

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}
  
		
}
