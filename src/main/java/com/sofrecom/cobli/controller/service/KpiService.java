package com.sofrecom.cobli.controller.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Attachements;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.Graphic_Repository;
import com.sofrecom.cobli.repository.PrestationRepository;

@Service
public class KpiService {
	
	@Autowired
	private Graphic_Repository Graphic_repository;
	
	@Autowired
	ESIMBRepository ESIMBRepo;
	
	@Autowired
	PrestationRepository prestationRepository;
	
	@Autowired
    private Acte_traitementRepository acteTraitementRepo;
	
	
	public Integer getSM( @RequestBody List<Attachements> actes)  {         
        int sommeDuree=0;
        for(Attachements list :actes ) {
            sommeDuree =+list.getDuree() ;
        }        
        return  sommeDuree;
	}
	
	
	public Double CalculStaffing (Double sommeMin  ){
		return sommeMin/(60*7.5) ;
	}
	public Double CalculCTJ (List<Acte_traitement>  List ,int nbJour,double staffing){

		return (List.size()/nbJour)/staffing ;
	}
	public Double CapaciteQte_Mois (List<Acte_traitement>  List ){

		return Double.valueOf(List.size());
	}
	public Double RealiseQte_MoisParequipe (List<Acte_traitement>  List ,int nbJour){

		return Double.valueOf(List.size()/nbJour);
	}
	public Double RealiseSurCapactie (Double realise ,Double capacite){

		return realise/capacite;
	}
	public Float Dmt_Declare (List<Acte_traitement>  ListAct  , Long nbjour){
		float sommeDuree=0;
		for(Acte_traitement list :ListAct ){

			sommeDuree =sommeDuree +list.getDuree() ;
		}

		return Float.valueOf((sommeDuree/ListAct.size()));
	}
	public Float Dmt_calcule (Float ctj){

		return Float.valueOf((float) ((7.5*60)/ctj));
	}
	public Float ETP ( Double staffing , int nbJoursOuvre){

		return Float.valueOf((float) (staffing/nbJoursOuvre));
	}
	public Double DollarETP ( Double staffing , Double prixJour){

		return staffing*prixJour;
	}
/////// manque explication
	public Double DollarBPU ( List<Acte_traitement> Listact , Double prixUO){
		return Listact.size()*prixUO;
	}


	public Double Productivite ( List<Acte_traitement> Listact , Double prixUO , int staffingdef){
		int sommeDuree=0;
		for(Acte_traitement list :Listact ){
			sommeDuree =+list.getDuree() ;
		}
		//Double.valueOf(ListAct.size()/sommeDuree)
		return Double.valueOf(sommeDuree/(staffingdef*7.5*60)) ;
	}


	public Double CalculPourcentage ( List<Acte_traitement> ListactParequipe  , int staffingdef){
		int sommeDuree=0;
		for(Acte_traitement list :ListactParequipe ){
			sommeDuree =+list.getDuree() ;
		}

		return Double.valueOf(((sommeDuree/(staffingdef*7.5*60)))*100);
		//(sommeDuree//dureeFormulaire°*100;
	}
	
	
	public static long getDaysBetween(String startDateStr, String endDateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        return ChronoUnit.DAYS.between(startDate, endDate);
    }
	
	
	public Map<Prestation, List<Acte_traitement>> groupActesByPrest(List<Acte_traitement> actes) {
        Map<Prestation, List<Acte_traitement>> groupedElements = new HashMap<>();

 
        	List<Prestation> prestation = prestationRepository.findAll().stream()
        	        .distinct()
        	        .collect(Collectors.toList());
         // Initialize the map with all the months of the year
        for (Prestation prest : prestation) {
        
        	List<Acte_traitement> acteee = acteTraitementRepo.findByTypeprestation(prest) ;
            groupedElements.put(prest, acteee);
            
        }
        
/*
        for (Acte_traitement acte : actes) {
        	String prest = acte.getType_prestation().getNomPrestation();
        	groupedElements.get(prest).add(acte);
        }    
        */
    /*    for (Map.Entry<Prestation, List<Acte_traitement>> entry : groupedElements.entrySet()) {
        	String key = entry.getKey().getNomPrestation();
        	List<Acte_traitement> elements = entry.getValue();
        	System.out.println("Key: " + key);
        	System.out.println("Elements: " + elements); System.out.println();
        	}
        */
          
        return groupedElements;
        
        
       
    }
	
	
	
	public List<Acte_traitement> groupActesByPrestt(List<Acte_traitement> actes) {
        Map<String, List<Acte_traitement>> groupedElements = new HashMap<>(); 
        	List<Prestation> prestation = prestationRepository.findAll().stream()
        	        .distinct()
        	        .collect(Collectors.toList());
         // Initialize the map with all the months of the year
        List <Acte_traitement> tajribi = new ArrayList<Acte_traitement>() ;	
        	   List<String> ibtihel= new ArrayList<String>();
        for (Prestation prest : prestation) {
        	List<Acte_traitement> acteee = acteTraitementRepo.findByTypeprestation(prest) ;
        	tajribi.addAll(acteee);
            groupedElements.put(prest.getNomPrestation(), new ArrayList<>());
            ibtihel.add(prest.getNomPrestation());
        }
        List<String> gh= new ArrayList<String>();

        for (Acte_traitement acte : actes) {
        	String prest = acte.getType_prestation().getNomPrestation();
        
        	gh.add(prest);
        	groupedElements.get(prest).add(acte);
        }
        
        return tajribi;
       
    }

}
