package com.sofrecom.cobli.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sofrecom.cobli.controller.service.AttachementsService;
import com.sofrecom.cobli.controller.service.Services;
import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Attachements;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.FacturationRepository;
import com.sofrecom.cobli.repository.Graphic_Repository;
import com.sofrecom.cobli.repository.PrestationRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/attachements")
public class FacturationController {
	@Autowired
	FacturationRepository facturationRepository;
	@Autowired
	Services services;
	@Autowired
	ESIMBRepository ESIMBRepo;
	@Autowired
	Graphic_Repository graphic_Repository;
	
	@Autowired
	AttachementsService attachementsService;
	
	@Autowired
    private Acte_traitementRepository acteTraitementRepo;
	@Autowired 
	PrestationRepository prestationRepository;
	
/*private List<Attachements>  facturerEsimb( ) {
    List<Acte_traitement> actes = new ArrayList<Acte_traitement>();
    List<Attachements> response = new ArrayList<Attachements>();

	for (Acte_traitement actetrait : actes) {
	if (actetrait.getMotif().equals("") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
        response.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
    } else if (actetrait.getMotif().equals("Requalification") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
        response.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
    } else if (actetrait.getMotif().equals("ORT") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
        response.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
    } else if (actetrait.getMotif().equals("Historique") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
        response.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
    
	}
	return response;
	}*/
	
	/*@GetMapping("/gettt")
	public List<Attachements> getAttachhh(@RequestParam String date_Debut_s,@RequestParam String date_Fin_s) throws ParseException {
		
	List<Acte_traitement> actes = new ArrayList<Acte_traitement>();
	List<Attachements> response = new ArrayList<Attachements>();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    Date date_Debut = dateFormat.parse(date_Debut_s);
    Date date_Fin = dateFormat.parse(date_Fin_s);
    System.out.println("Date début : " + date_Debut);
    System.out.println("Date fin : " + date_Fin);
    
    actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
    System.out.println("Actes trouvés : " + actes);
    System.out.println("Nombre d'actes trouvés : " + actes.size());

	
	
    for (Acte_traitement actetrait : actes) {
        if (actetrait.getType_prestation().equals("Evolution statut IMB")) {
            List<Attachements> attachesEsimb = facturerEsimb(actes);
            response.addAll(attachesEsimb);
            System.out.println("Attachements ESIMB : " + response);
        } else if (actetrait.getType_prestation().equals("Grafic")) {
            facturerGrafic(actes);
        } else {
            System.out.println("Type de prestation non reconnu : " + actetrait.getType_prestation());
        }
    }

    return response;
	}*/
	
	
	private List<Attachements> facturerEsimb(List<Acte_traitement> actes) {
	    List<Attachements> response = new ArrayList<Attachements>();
	   

	    for (Acte_traitement actetrait : actes) {
	    	
	    	ESIMB actetrait2= (ESIMB)actetrait;
	        if (actetrait2.getMotif().equals("") && actetrait2.getDateLivraison() != null && !actetrait2.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait2.getIdacte(), actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait2.getType_prestation(), actetrait2.getType_element(), actetrait2.getQuantite(), 0, 0, "Tarif 1", actetrait2.getDuree()));
	        } else if (actetrait2.getMotif().equals("Requalification") && actetrait2.getDateLivraison() != null && !actetrait2.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait2.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait2.getType_prestation(), actetrait2.getType_element(), actetrait2.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
	        } else if (actetrait2.getMotif().equals("ORT") && actetrait2.getDateLivraison() != null && !actetrait2.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait2.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait2.getType_prestation(), actetrait2.getType_element(), actetrait2.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
	        } else if (actetrait.getMotif().equals("Historique") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
	        }
			else if (actetrait.getMotif().equals("Immeuble") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Inexistence IMB optimum") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")){
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Incohérence") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Rami") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("PB inexistant sur GFI") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Blocage Optimum") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("IMB raccordable sur Optimum") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")){
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Clé en main client") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("PB saturé") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Inexistence IMB Ipon") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Position IMB introuvable") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals(""))  {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("PB inexistant Ipon") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals(""))  {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Non evolution stat IMB") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals(""))  {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("En attente") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "En cours CDS",actetrait2.getDuree()));
			}
			else if (actetrait.getMotif().equals("Reprise non facturable") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
	            response.add(new Attachements(actetrait.getIdacte(),actetrait2.getCodeIMB(), actetrait2.getCodeBanbou(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Non facturable",actetrait2.getDuree()));
			}
		
	    }
		return response;
	   
	}
	
	
	private List<Attachements> facturerGrafic(List<Acte_traitement> actes) {
	    List<Attachements> response = new ArrayList<Attachements>();

	    for (Acte_traitement actetrait : actes) {
	    	if (actetrait.getMotif().equals("Appel sortant") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		        response.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 9"));
		    } else if (actetrait.getMotif().equals("Classique Grafic") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		        response.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 8"));
		    }
	    }
	    return response;
	}	
	
	@GetMapping("/gettt")
	public List<Attachements> getAttachhh(@RequestParam String date_Debut_s,@RequestParam String date_Fin_s) throws ParseException { 
	List<Attachements> response = new ArrayList<>();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    Date date_Debut = dateFormat.parse(date_Debut_s);
    Date date_Fin = dateFormat.parse(date_Fin_s);
        System.out.println("Date début : " + date_Debut);
        System.out.println("Date fin : " + date_Fin);
    
        List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
        System.out.println("Actes trouvés : " + actes);
        System.out.println("Nombre d'actes trouvés : " + actes.size());

        List<Attachements> attachesEsimb = facturerEsimb(actes);
        response.addAll(attachesEsimb);
        System.out.println("Attachements ESIMB : " + attachesEsimb);
        for (Acte_traitement actetrait : actes) {
            Prestation prestation = actetrait.getType_prestation();
            if (prestation.getNomPrestation().equals("Evolution statut IMB")) {
                // Ne rien faire ici, on a déjà ajouté les attachements ESIMB dans la liste de réponse
            } else {
                System.out.println("Type de prestation non reconnu : idPrestation=" + prestation.getIdPrestation() + ", nomPrestation=" + prestation.getNomPrestation() + ", deadline=" + prestation.getDeadline());
            }
        }
        return response;
	}
	
	
	@GetMapping("/excel")
	public void generateExcelReport(HttpServletResponse response, @RequestParam String date_Debut_s,@RequestParam String date_Fin_s) throws Exception{
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=attach.xls";

		response.setHeader(headerKey, headerValue);
		
		attachementsService.generateExcel(response,date_Debut_s, date_Fin_s );
		
		System.out.println("------------- done excel");
		response.flushBuffer();
	}
	
	/*@GetMapping("/excel")
	public void generateExcelReport(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s, HttpServletResponse response) throws Exception {
	    response.setContentType("application/octet-stream");

	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment;filename=attach.xls";
	    response.setHeader(headerKey, headerValue);

	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    Date date_Debut = dateFormat.parse(date_Debut_s);
	    Date date_Fin = dateFormat.parse(date_Fin_s);

	    List<Attachements> attachesEsimb =facturerEsimb(acteTraitementRepo.findActeAttach(date_Debut, date_Fin));
	    List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
	    List<Attachements> responseAttachements = new ArrayList<>();
	    responseAttachements.addAll(attachesEsimb);

	    for (Acte_traitement actetrait : actes) {
	        if (actetrait.getType_prestation().equals("Evolution statut IMB")) {
	            // Ne rien faire ici, on a déjà ajouté les attachements ESIMB dans la liste de réponse
	        } else {
	            System.out.println("Type de prestation non reconnu : " + actetrait.getType_prestation());
	            responseAttachements.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getMotif(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
	        }
	    }

	    attachementsService.generateExcel(response);
	    response.flushBuffer();
	}*/
	
	/*@GetMapping("/excel")
	public void generateExcelReport(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s, HttpServletResponse response) throws Exception {
	    response.setContentType("application/octet-stream");

	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment;filename=attach.xls";
	    response.setHeader(headerKey, headerValue);

	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    Date date_Debut = dateFormat.parse(date_Debut_s);
	    Date date_Fin = dateFormat.parse(date_Fin_s);

	    List<Attachements> response = getAttachhh(date_Debut_s, date_Fin_s); // appel de la méthode getAttachhh avec les mêmes paramètres

	    attachementsService.generateExcel(response); // utilisation de la liste d'attachements de la réponse pour générer le fichier Excel
	    response.flushBuffer();
	}*/


	
	
	

	@GetMapping("/gett")
	public List<Attachements> getAttache()  {
		return facturationRepository.getAttachementss();
	}

	// Donee ------------------
	
}
