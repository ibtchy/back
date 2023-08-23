package com.sofrecom.cobli.controller;

import com.sofrecom.cobli.models.*;
import com.sofrecom.cobli.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/motif")
public class MotifController {
	 @Autowired
	    PrestationRepository prestationRepository;

	    @Autowired
	    MotifRepository motifRepository;

	    @GetMapping("/getMotifs/{typePrestation}")
	    public ResponseEntity<List<Motif>> getMotifs(@PathVariable("typePrestation") String typePrestation){
	        try {
	            List<Motif> motifs = new ArrayList<Motif>();

	            Prestation prestation= prestationRepository.findByNomPrestation(typePrestation).get();

	            motifs=motifRepository.findByTypePrestation(prestation);


	            if (motifs.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }

	            return new ResponseEntity<>(motifs, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	    }

}
