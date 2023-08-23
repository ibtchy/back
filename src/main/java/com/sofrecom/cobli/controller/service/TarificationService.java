package com.sofrecom.cobli.controller.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.ControleTravaux;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.Graphic_Repository;
import com.sofrecom.cobli.repository.PrestationRepository;
import com.sofrecom.cobli.repository.TarificationRepository;

@Service
public class TarificationService {

    @Autowired
    private Graphic_Repository graphicRepository;

    @Autowired
    private ESIMBRepository esimbRepository;

    @Autowired
    private PrestationRepository prestationRepository;

    @Autowired
    private Acte_traitementRepository acteTraitementRepository;
    
    @Autowired
    private TarificationRepository tarifRepo;

       
    public Tarification calculerTypeTarifEsimb(ESIMB esimb) {
    	
    	String motifCle= "Clé en main Client" ;
    	String motifEvol= "Non Evolution statut IMB  " ;

		
		 Tarification tarif1= tarifRepo.findByCodeTarif("Tarif 1");
		  Tarification tarif2= tarifRepo.findByCodeTarif("Tarif 2");
		  Tarification tarifCDS= tarifRepo.findByCodeTarif("en cours CDS");
	      Tarification tarifNonFact= tarifRepo.findByCodeTarif("nonFacturable");
		
       if (esimb.getType_prestation().getNomPrestation().equals("Evolution statut IMB")) {
           
           if (esimb.getMotif().equals("") && esimb.getDateLivraison() != null && !esimb.getDateLivraison().toString().equals("")) {
           	esimb.setTarif(tarif1);
               return tarif1;
	        } else if (esimb.getMotif().equals("Requalification") && esimb.getDateLivraison() != null ) {
	        	esimb.setTarif(tarif2);
               return tarif2;
	        } else if (esimb.getMotif().equals("ORT") && esimb.getDateLivraison() != null ) {
	        	esimb.setTarif(tarif2);
               return tarif2;
	        } else if (esimb.getMotif().equals("Historique") && esimb.getDateLivraison() != null ) {
	        	esimb.setTarif(tarif2);
               return tarif2;	        }
			else if (esimb.getMotif().equals("Immeuble") && esimb.getDateLivraison() != null ) {
				esimb.setTarif(tarif2);
               return tarif2;				}
			else if (esimb.getMotif().equals("Inexistence IMB optimum") && esimb.getDateLivraison() != null ){
				esimb.setTarif(tarif2);
               return tarif2;				}
			else if (esimb.getMotif().equals("Inexistence IMB Banbou") && esimb.getDateLivraison() != null ){
				esimb.setTarif(tarif2);
               return tarif2;				}
			else if (esimb.getMotif().equals("incohérence") && esimb.getDateLivraison() != null ) {
				esimb.setTarif(tarif1);
               return tarif1;				}
			else if (esimb.getMotif().equals("rami") && esimb.getDateLivraison() != null ) {
				esimb.setTarif(tarif1);
               return tarif1;				}
			else if (esimb.getMotif().equals("Pb inexistant GFI") && esimb.getDateLivraison() != null ) {
				esimb.setTarif(tarif1);
               return tarif1;				}
			else if (esimb.getMotif().equals("Blocage Optimum") && esimb.getDateLivraison() != null) {
				esimb.setTarif(tarif2);
               return tarif2;				}
			else if (esimb.getMotif().equals("IMB Raccordable Optimum") && esimb.getDateLivraison() != null ){
				esimb.setTarif(tarif2);
               return tarif2;				}
			else if (esimb.getMotif().equalsIgnoreCase(motifCle) && esimb.getDateLivraison() != null) {
				esimb.setTarif(tarif2);
               return tarif2;				}
			else if (esimb.getMotif().equals("PB Saturé") && esimb.getDateLivraison() != null ) {
				esimb.setTarif(tarif1);
               return tarif1;				}
			else if (esimb.getMotif().equals("Inexistence IMB Ipon") && esimb.getDateLivraison() != null ) {
				esimb.setTarif(tarif1);
               return tarif1;			}
			else if (esimb.getMotif().equalsIgnoreCase(motifEvol) && esimb.getDateLivraison() != null )  {
				esimb.setTarif(tarif1);
               return tarif1;			}
			else if (esimb.getMotif().equals("PB inexistant Ipon") && esimb.getDateLivraison() != null )  {
				esimb.setTarif(tarif1);
               return tarif1;			}
			else if (esimb.getMotif().equals("Non evolution statut IMB") && esimb.getDateLivraison() != null )  {
				esimb.setTarif(tarif2);
               return tarif2;				}
			else if (esimb.getMotif().equals("En attente")) {
				esimb.setTarif(tarifCDS);
               return tarifCDS;				}
			else if (esimb.getMotif().equals("Reprise non facturable") && esimb.getDateLivraison() != null ) {
				esimb.setTarif(tarifNonFact);
               return tarifNonFact;				}
       }
   
   return null; // Retourner une valeur par défaut si aucune condition n'est satisfaite
}

   public Tarification calculerTypeTarifDesat(DESAT acte) { 
	//Tarification tarif =new Tarification() ;
	  Tarification tarif5= tarifRepo.findByCodeTarif("Tarif 5");
	  Tarification tarif7= tarifRepo.findByCodeTarif("Tarif 7");
      Tarification tarifCDS= tarifRepo.findByCodeTarif("encoursCDS");
      Tarification tarifNonFact= tarifRepo.findByCodeTarif("nonFacturable");
      Tarification tarif6= tarifRepo.findByCodeTarif("Tarif 6");

	   if (acte.getType_prestation().getNomPrestation().equals("Désaturations Coupleurs")) {
       	 
       	 // recuperer mos de dateLiv
       	 Date dateLivraison = acte.getDateLivraison();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateLivraison);
            int moisLivraison = calendar.get(Calendar.MONTH) + 1;
            
            // recuperer mois en cours
            Date dateAujourdhui = new Date();
            Calendar calenda = Calendar.getInstance();
            calendar.setTime(dateAujourdhui);
            int moisActuel = calenda.get(Calendar.MONTH) + 1;
                    
           if (acte.getMotif().equals("simple") && acte.getDateLivraison() != null ) {
        	   acte.setTarif(tarif5);
        	  // tarif = tarif5 ;
             return  tarif5;
	        } else if (acte.getMotif().equals("Complexe") && acte.getDateLivraison() != null ) {
	        	 acte.setTarif(tarif6);
	            return tarif6;
	        } else if (acte.getMotif().equals("Absence de commentaire")  && acte.getDateLivraison() != null) {
	        	acte.setTarif(tarif7);
	            return  tarif7;
	        }else if (acte.getMotif().equals("Coupleurs allumés")  && acte.getDateLivraison() != null) {
	        	acte.setTarif(tarif7);
	        	 return   tarif7;
	        }else if (acte.getMotif().equals("Absence de la ferme de transport")  && acte.getDateLivraison() != null) {
	        	acte.setTarif(tarif7);
	        	 return   tarif7;
	        }else if (acte.getMotif().equals("Coupleurs déffectueux")  && acte.getDateLivraison() != null) {
	        	acte.setTarif(tarif7);
	        	 return   tarif7;
	        }else if (acte.getMotif().equals("En attente")) {
	        	acte.setTarif(tarifCDS);
	        	 return   tarifCDS;
	        }else if (acte.getMotif().equals("Reprise non facturable") ) {
	        	acte.setTarif(tarifNonFact);
	        	 return tarifNonFact;
	        
	        }
           
           
           
           
           
	   
       }
	   
  // Retourner une valeur par défaut si aucune condition n'est satisfaite
	return null;
}

public Tarification calculerTypeTarifCT(ControleTravaux controletravaux) {
	// TODO Auto-generated method stub
	return null;
}
    
    
}
