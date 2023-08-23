package com.sofrecom.cobli.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.sofrecom.cobli.controller.service.BpuService;
import com.sofrecom.cobli.models.BPU;
import com.sofrecom.cobli.repository.BPURepository;


@RestController
@RequestMapping("/api")
public class BPUController {
	
	@Autowired
	BPURepository bpuRepository;
	private BpuService bpuService;
	
	public BPUController(BpuService bpuService) {
        this.bpuService = bpuService;
    }
	private final List<BPU> bpus = new ArrayList<>();

    /*@PostMapping
    public ResponseEntity<BPU> createBpu(@RequestBody BPU bpu) {
        // Ajouter la logique métier ici
        bpu.setVersion("V1");
        LocalDate now = LocalDate.now();
        bpu.setDateDebut(now);
        bpu.setDateExpiration(null);
        
        BPU savedBpu = bpuRepository.save(bpu);
        return ResponseEntity.ok(savedBpu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BPU> updateBpu(@PathVariable Long id, @RequestBody BPU bpu) {
        // Ajouter la logique métier ici
        for (BPU existingBpu : bpulist) {
            if (existingBpu.getId().equals(id)) {
                LocalDate newDateDebut = LocalDate.now();
                LocalDate newDateExpiration = newDateDebut.minusDays(1);
                existingBpu.setRefTacheBPU(bpu.getRefTacheBPU());
                existingBpu.setType_prestation(bpu.getType_prestation());
                existingBpu.setType_element(bpu.getType_element());
                existingBpu.setDateExpiration(bpu.getDateExpiration());
                existingBpu.setDateValidite(bpu.getDateValidite());
                existingBpu.setPenalite(bpu.getPenalite());
                existingBpu.setType_penalite(bpu.getType_penalite());
                existingBpu.setPU(bpu.getPU());
                existingBpu.setDateDebut(newDateDebut);
                existingBpu.setDateExpiration(newDateExpiration);
                existingBpu.setVersion("V2");
                return ResponseEntity.ok(existingBpu);
            }
        }
        return ResponseEntity.notFound().build();
    }*/
	
	
	/*@PutMapping("/bpus/{id}")
    public ResponseEntity<BPU> updateBpu(@PathVariable(value = "id") Long id, @RequestBody BPU bpuToUpdate) throws ResourceNotFoundException {
        BPU updatedBpu = bpuService.updateBpu(id, bpuToUpdate);
        return ResponseEntity.ok(updatedBpu);
    }*/
	
	/*@PutMapping("/bpus/{id}")
	  public BPU updateBpu(@PathVariable("id") Long id, @RequestBody BPU bpu) throws ResourceNotFoundException {
	    BPU existingBpu = bpuRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("BPU not found with id: " + id));
	    existingBpu.setDateDebut(bpu.getDateDebut());
	    existingBpu.setDateExpiration(bpu.getDateExpiration());
	    existingBpu.setDateValidite(bpu.getDateValidite());
	    existingBpu.setPenalite(bpu.getPenalite());
	    existingBpu.setPU(bpu.getPU());
	    existingBpu.setRefTacheBPU(bpu.getRefTacheBPU());
	    existingBpu.setType_penalite(bpu.getType_penalite());
	    existingBpu.setType_prestation(bpu.getType_prestation());
	    existingBpu.setType_element(bpu.getType_element());
	    BPU updatedBpu = bpuRepository.save(existingBpu);
	    return updatedBpu;
	  }*/

	
	
	
	/*// Add BPU
	@PostMapping("/bpus")
	public ResponseEntity<BPU> createBPU(@RequestBody BPU bpu) {
		try {
			BPU _bpu = bpuRepository
					.save(new BPU(bpu.getRefTacheBPU(),bpu.getType_element(),bpu.getPU(), bpu.getTarif(),bpu.getDateDebut(),
							bpu.getDateValidite(),bpu.getDateExpiration(),bpu.getType_penalite(),bpu.getPenalite(), 
							  bpu.getType_prestation(), bpu.getVersion()));
			return new ResponseEntity<>(_bpu, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	@PostMapping("/bpus")
	public BPU createBPU(@RequestBody BPU bpu) {
	    bpu.setDateDebut(LocalDate.now().toString());
	    bpu.setDateExpiration("");
	    bpu.setVersion("V1");
	    return bpuRepository.save(bpu);
	}

	
	//Get all BPUs
	@GetMapping("/bpus")
	public List<BPU> getAll(){
		return bpuRepository.findAll();
	}
	
	//Get BPU by id
	@GetMapping("/bpus/{id}")
	public ResponseEntity<BPU> getBPUById(@PathVariable("id") long id) {
		Optional<BPU> bpuData = bpuRepository.findById(id);

		if (bpuData.isPresent()) {
			return new ResponseEntity<>(bpuData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*//Modif BPU
	@PutMapping("/bpus/{id}")
	public ResponseEntity<BPU> updateBPU(@PathVariable("id") long id, @RequestBody BPU bpu) {
		Optional<BPU> bpuData = bpuRepository.findById(id);

		if (bpuData.isPresent()) {
			BPU _bpu = bpuData.get();
			_bpu.setRefTacheBPU(bpu.getRefTacheBPU());
			_bpu.setType_prestation(bpu.getType_prestation());
			_bpu.setType_element(bpu.getType_element());
			_bpu.setDateExpiration(bpu.getDateExpiration());
			_bpu.setDateValidite(bpu.getDateValidite());
			_bpu.setPenalite(bpu.getPenalite());
			_bpu.setType_penalite(bpu.getType_penalite());
			_bpu.setPU(bpu.getPU());
			return new ResponseEntity<>(bpuRepository.save(_bpu), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}*/
	
	@PutMapping("/bpus/{id}")
	public ResponseEntity<BPU> updateBPU(@PathVariable Long id, @RequestBody BPU newBPU) {
	    Optional<BPU> optionalBPU = bpuRepository.findById(id);
	    if (optionalBPU.isPresent()) {
	        BPU bpu = optionalBPU.get();
	        String currentDate = LocalDate.now().toString();
	        if (bpu.getVersion().equals("V1")) {
	            // Créer une nouvelle entrée V2 avec date début actuelle et date expiration vide
	            BPU bpuV2 = new BPU();
	            bpuV2.setRefTacheBPU(bpu.getRefTacheBPU());
	            bpuV2.setType_element(bpu.getType_element());
	            bpuV2.setPU(bpu.getPU());
	            bpuV2.setTarif(bpu.getTarif());
	            bpuV2.setDateDebut(currentDate);
	            bpuV2.setDateValidite(bpu.getDateValidite());
	            bpuV2.setDateExpiration("");
	            bpuV2.setType_penalite(bpu.getType_penalite());
	            bpuV2.setPenalite(bpu.getPenalite());
	            bpuV2.setType_prestation(bpu.getType_prestation());
	            bpuV2.setVersion("V2");
	            bpuRepository.save(bpuV2);
	            // Mettre à jour l'entrée V1 pour mettre sa date d'expiration à la date de début de l'entrée V2 moins un jour
	            LocalDate dateDebutV2 = LocalDate.parse(currentDate);
	            LocalDate dateExpirationV1 = dateDebutV2.minusDays(1);
	            bpu.setDateExpiration(dateExpirationV1.toString());
	            bpuRepository.save(bpu);
	            return ResponseEntity.ok(bpuV2);
	        } else {
	            // Si l'entrée est déjà V2, la mettre simplement à jour
	        	bpu.setRefTacheBPU(newBPU.getRefTacheBPU());
	            bpu.setType_element(newBPU.getType_element());
	            bpu.setPU(newBPU.getPU());
	            bpu.setTarif(newBPU.getTarif());
	            bpu.setDateValidite(newBPU.getDateValidite());
	            bpu.setType_penalite(newBPU.getType_penalite());
	            bpu.setPenalite(newBPU.getPenalite());
	            bpu.setType_prestation(newBPU.getType_prestation());
	            bpu.setDateDebut(currentDate);
	            bpu.setDateExpiration("");
	            bpu.setVersion("V2");
	            bpuRepository.save(bpu);
	            return ResponseEntity.ok(bpu);
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }}
	
	//Supp BPU by id
	@DeleteMapping("/bpus/{id}")
	public ResponseEntity<HttpStatus> deleteBPU(@PathVariable("id") long id) {
		try {
			bpuRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Supp all BPUs
	@DeleteMapping("/bpus")
	public ResponseEntity<HttpStatus> deleteAllBPUs() {
		try {
			bpuRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
