package com.sofrecom.cobli.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.Prestation;

@Repository
public interface Acte_traitementRepository extends JpaRepository<Acte_traitement, String> {
	Optional<Acte_traitement> findByidacte(String idacte);
    List<Acte_traitement> findByAffectation(String affectation);
    
    @Query("SELECT a FROM Acte_traitement a WHERE a.statutFacturation='facturable' AND a.dateLivraison BETWEEN :dateDebut AND :dateFin")
    List<Acte_traitement> findActeAttach(@Param("dateDebut") Date date_Debut, @Param("dateFin") Date date_Fin);


//Backolg
    List<Acte_traitement> findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc(String statutFacturation);

//KPI
  
    List<Acte_traitement> findByMotifOrStatutFacturation( String motifs, String statutFacturation);


    @Query("SELECT a FROM Acte_traitement a WHERE a.statutFacturation='facturable' OR a.motif='En attente' AND a.dateLivraison BETWEEN :dateDebut AND :dateFin")
    List<Acte_traitement> findActeKpi(@Param("dateDebut") Date date_Debut, @Param("dateFin") Date date_Fin);

    
    @Query("SELECT a.affectation, COUNT(a) FROM Acte_traitement a GROUP BY a.affectation") List<Object[]> countByAffectation();
   
	
	
	List<Acte_traitement> findByTypeprestation(Prestation p);
	
	

}
