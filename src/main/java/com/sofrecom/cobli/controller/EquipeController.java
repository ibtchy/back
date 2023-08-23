package com.sofrecom.cobli.controller;


import com.sofrecom.cobli.models.Equipe;
import com.sofrecom.cobli.models.Nropm;
import com.sofrecom.cobli.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    @Autowired
    EquipeRepository equipeRepository;

    @GetMapping("/equipes")
    public ResponseEntity<List<Equipe>> getEquipes(){
        try {
            List<Equipe> equipes = new ArrayList<>();

            equipes=equipeRepository.findAll();


            if (equipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(equipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
