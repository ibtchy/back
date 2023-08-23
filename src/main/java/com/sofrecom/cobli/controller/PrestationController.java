package com.sofrecom.cobli.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofrecom.cobli.controller.service.IServicePrestation;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.PrestationRepository;

@RestController
@RequestMapping("/api/prestations")
public class PrestationController {
	
	
	@Autowired
	 IServicePrestation servicePrestation;

	@Autowired
    PrestationRepository prestationRepo;
	@GetMapping("/getPrestations/{typePrestation}")
	  public ResponseEntity<Prestation> getPrestationByNom(@PathVariable("typePrestation") String typePrestation) {
	    try {
	      Optional<Prestation> prestation = prestationRepo.findByNomPrestation(typePrestation);
	      if (prestation.isPresent()) {
	        return ResponseEntity.ok(prestation.get());
	      } else {
	        return ResponseEntity.notFound().build();
	      }
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	  }
	
	// pour l calendrier 
	  @GetMapping("/getAllPrestations")
	    public List<Prestation> getAllPrestations() {
	        return servicePrestation.getAllPrestations();
	    }

	    @GetMapping("/getPrestationById/{prestationId}")
	    public Prestation getPrestationById(@PathVariable Long prestationId) {
	        return servicePrestation.getPrestationById(prestationId);
	    }

	    @PostMapping("/addPrestation")
	    public Prestation addPrestation(@RequestBody Prestation prestation) {
	        return servicePrestation.addPrestation(prestation);
	    }

	    @PutMapping("/updatePrestation")
	    public Prestation updatePrestation(@RequestBody Prestation prestation) {
	        return servicePrestation.updatePrestation(prestation);
	    }

	    @DeleteMapping("/deletePrestation/{prestationId}")
	    public void deletePrestation(@PathVariable Long prestationId) {
	        servicePrestation.deletePrestation(prestationId);
	    }

	    @GetMapping("/getPrestationByNomPrestation/{nomPrestation}")
	    public Prestation getPrestationByNomPrestation(@PathVariable("nomPrestation") String nomPrestation){
	        return servicePrestation.getPrestationByNomPrestation(nomPrestation);
	    }

}
