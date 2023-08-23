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
@RequestMapping("/api/typeElement")
public class TypeElementController {
	@Autowired
    PrestationRepository prestationRepository;

    @Autowired
    TypeElementRepository typeElementRepository;


    @GetMapping("/getTypesElements/{typePrestation}")
    public ResponseEntity<List<TypeElement>> getTypesElements(@PathVariable("typePrestation") String typePrestation){
        try {
            List<TypeElement> typesElements = new ArrayList<TypeElement>();

            Prestation prestation= prestationRepository.findByNomPrestation(typePrestation).get();

            typesElements=typeElementRepository.findByTypePrestation(prestation);


            if (typesElements.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(typesElements, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
