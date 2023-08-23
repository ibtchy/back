package com.sofrecom.cobli.controller.service;


import org.springframework.stereotype.Service;

import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.PrestationRepository;

import java.util.List;
@Service


public class ServicePrestation implements IServicePrestation {
    PrestationRepository prestationRepository;

    @Override
    public Prestation getPrestationById(Long prestationId) {
        return prestationRepository.findById(prestationId).orElse(null);
    }

    @Override
    public List<Prestation> getAllPrestations() {
        return prestationRepository.findAll();
    }

    @Override
    public Prestation addPrestation(Prestation prestation) {
        return prestationRepository.save(prestation);
    }

    @Override
    public Prestation updatePrestation(Prestation prestation) {
        return prestationRepository.save(prestation);
    }

    @Override
    public void deletePrestation(Long prestationId) {
        prestationRepository.deleteById(prestationId);
    }

    @Override
    public Prestation getPrestationByNomPrestation(String nomPrestation) {
        return prestationRepository.findPretationByNomPrestation(nomPrestation);
    }
}
