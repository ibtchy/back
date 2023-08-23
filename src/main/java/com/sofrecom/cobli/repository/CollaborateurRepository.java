package com.sofrecom.cobli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.Equipe;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, String>{
	
	Optional<Collaborateur> findByCUID(String CUID);
	//Collaborateur findByCUID(String CUID);
	//List<Collaborateur> findByidequipe(String idequipe);
	
	
	List<Collaborateur> findByEquipe(Equipe equipe);
	List<Collaborateur> findAllByOrderByDateInscriptionDesc();


}
