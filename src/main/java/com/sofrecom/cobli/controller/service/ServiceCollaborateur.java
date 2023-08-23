package com.sofrecom.cobli.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.repository.CollaborateurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCollaborateur implements IServiceCollaborateur{
    @Autowired
    CollaborateurRepository collaborateurRepository;

    @Override
    public Collaborateur getCollaborateurById(String CUID) {
        return collaborateurRepository.findById(CUID).orElse(null);
    }

    @Override
    public List<Collaborateur> getAllCollaborateurs() {
        return collaborateurRepository.findAll();
    }

    @Override
    public Collaborateur addCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }

    @Override
    public Collaborateur updateCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }


    @Override
    public void deleteCollaborateur(String CUID) {
        collaborateurRepository.deleteById(CUID);
    }


}
