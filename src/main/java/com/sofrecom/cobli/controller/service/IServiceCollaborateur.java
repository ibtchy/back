package com.sofrecom.cobli.controller.service;


import java.util.List;

import com.sofrecom.cobli.models.Collaborateur;

public interface IServiceCollaborateur {
    public Collaborateur getCollaborateurById(String CUID);
    public List<Collaborateur> getAllCollaborateurs();
    public Collaborateur addCollaborateur(Collaborateur collaborateur);
    public Collaborateur updateCollaborateur(Collaborateur collaborateur) ;
    public void deleteCollaborateur(String CUID);
}