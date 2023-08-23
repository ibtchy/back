package com.sofrecom.cobli.repository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.ESIMB;

public interface DESATRepository extends JpaRepository<DESAT, String>  {
  

	List<DESAT> findByAffectation(String affectation);
	List<DESAT> findByDateLivraison(String dateLiv);
	List<DESAT> findByFi(String ref);
	Optional<DESAT> findByCog(String cog);
	List<DESAT> findByUi(String ui);
	List <DESAT> findByDateLivraisonIsNull();
	List <DESAT> findByDateLivraisonIsNotNull();
	List <DESAT> findByMotif(String motif);
	List<DESAT> findByCogContaining(String cog);



	
	List<DESAT> findByMotifInOrMotifReaffectationInOrderByPrioriteAscDateDeadlineAsc(Collection<String> motifs,Collection<String> motifsReaffectation);
	//Optional<DESAT> findByCog(String cog);
	//List<DESAT> findByMotifDesatIn(Collection<String> motif_desat);
	//List<DESAT> findByMotifDesatIn(Collection<String> motif_desat);


	Optional<DESAT> findByIdacte(String idacte);
	List<DESAT> findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc(String statutFacturation);
	boolean existsByCog(String cog);

	
	
 
}
