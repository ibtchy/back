package com.sofrecom.cobli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofrecom.cobli.controller.service.TarificationService;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.repository.DESATRepository;

@RestController
@RequestMapping("/tarification")
public class TarificationController {

    private final TarificationService tarificationService;

    @Autowired
    public TarificationController(TarificationService tarificationService) {
        this.tarificationService = tarificationService;
    }
    
    @Autowired
	DESATRepository desatRepo;

    /*@GetMapping("/calculate")
    public String calculateTarification() {
        // Appeler la méthode calculateTarification() du service
        tarificationService.calculateTarification();
        
        // Effectuer d'autres opérations ou renvoyer une réponse appropriée
        return "Tarification calculée avec succès";
    }*/
    
    @GetMapping("/desatTarif")
    private DESAT ajoutTarifDesat(@RequestBody DESAT desat) {
  	  
  	 Tarification tarif =  tarificationService.calculerTypeTarifDesat(desat);
  	 desat.setTarif(tarif);
  	 desatRepo.save(desat);
  	 
  	  return desat;
  	  
    }
}

