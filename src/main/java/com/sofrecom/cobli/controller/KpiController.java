package com.sofrecom.cobli.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sofrecom.cobli.controller.service.ActeTraitementService;
import com.sofrecom.cobli.controller.service.AttachementsService;
import com.sofrecom.cobli.controller.service.KpiService;
import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Attachements;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.Acte_traitementRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/kpi")
public class KpiController {
	
	@Autowired
	ActeTraitementService acteTraitementService;
	
	@Autowired
	KpiService kpiService;
	
	@Autowired
    private Acte_traitementRepository acteTraitementRepo;
	
	
	@GetMapping("/group-by-affectation")
	public ResponseEntity<List<Object[]>> getGroupedByAffectation() {
		List<Object[]> groupedResults = acteTraitementService.getGroupedActeTraitementByAffectation();
		return ResponseEntity.ok(groupedResults);
		}
	
	
	/*@PostMapping("/Staffing")
	public Double getSommeDuree( @RequestBody List<Attachements> actes  ) 
 { 				
		int sommeDuree=0;
		for(Attachements list :actes ){
			sommeDuree =+list.getDuree() ;		
		}	
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));		
	    return  Staffing;
		}*/
	
	
	@GetMapping("/Staffing")
	public Double getSttaffing(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);


		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		return  Staffing;
	}
		
    
	@GetMapping("/CTJ")
	public Double CTJ(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);


		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);

		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));
		Double CTJ = kpiService.CalculCTJ(actes, (int) nbJr, Staffing);

		return  CTJ;
	}
	
	@GetMapping("/Capacite")
	public Double getCapacite(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());

		Double CapaciteQte_Mois= kpiService.CapaciteQte_Mois(actes);
		return  CapaciteQte_Mois;

	}
	
	@GetMapping("/dmtDeclaree")
	public Float getkpiByPrest(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());

		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}


		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);

		Float  Dmt_Declare = kpiService.Dmt_Declare(actes, nbJr);
		return Dmt_Declare;
	}
	
	
	@GetMapping("/dmtCalculee")
	public Float getDmtCalculee(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());
		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);
		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		double CTJ = kpiService.CalculCTJ(actes, (int) nbJr, Staffing);
		float  Dmt_Calculee = kpiService.Dmt_calcule((float) CTJ);
		return  Dmt_Calculee;

	}	
	
	@GetMapping("/ETP")
	public Float getETP(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());
		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);
		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;

		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		long nbJOuvre = (long) actes.size();
		float  ETP = kpiService.ETP(Staffing, (int) nbJOuvre);
		return  ETP;

	}
	
	/*@PostMapping("/group-actes-by-prest")
    public Map<Prestation, List<Acte_traitement>> groupActesByPrest(@RequestBody List<Acte_traitement> actes) {
        actTraitement.clearElements();
        for (Acte_traitement acte : actes) {
            actTraitement.addElement(acte);
        }
        return actTraitement.groupElementsByPrestation();
    }*/
	
	
	/*@PostMapping("/ActesByPrest")
	public List<Acte_traitement> ActesByPrestt(@RequestBody List<Acte_traitement> actes) {
	    return attachService.groupActesByPrestt(actes);
	}*/
	@GetMapping("/ActesByPrest")
	public Map<Prestation, List<Acte_traitement>> ActesByPrest(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());
		return kpiService.groupActesByPrest(actes);

	}
}
