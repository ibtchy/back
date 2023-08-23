package com.sofrecom.cobli.controller.service;

import java.util.List;

import com.sofrecom.cobli.models.Prestation;

public interface IServicePrestation {
    public Prestation getPrestationById(Long prestationId);
    public List<Prestation> getAllPrestations();
    public Prestation addPrestation(Prestation prestation);
    public Prestation updatePrestation(Prestation prestation) ;
    public void deletePrestation(Long prestationId);
    public Prestation getPrestationByNomPrestation(String nomPrestation);
}