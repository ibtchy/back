package com.sofrecom.cobli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;

@Repository
public interface ESIMBRepository extends JpaRepository<ESIMB, String> {

		//Optional<ESIMB> findByCodeIMB(String codeIMB);
		List<ESIMB> findByCodeIMB(String codeIMB);
		//Optional<ESIMB> findByIdacte(String idacte);
		
		//Optional<ESIMB> findByidacte(String idacte);
		Optional<ESIMB> findByidacte(String idacte);
		

		List<ESIMB> findByaffectationContaining(String affectation);
		 List<ESIMB> findBydateLivraison(String dateLivraison);
		 List<ESIMB> findBycodeIMBContaining(String codeIMB);
			List<ESIMB>findBycodeBanbouContaining(String codeBanbou);
			 List<ESIMB> findByAffectation(String affectation);

			Optional<ESIMB> findByCodeBanbou(String codeBanbou);

			List<ESIMB> findByMotif(String motif);

			List<ESIMB> findByMotifInOrMotifInOrderByPrioriteAscDateDeadlineAsc(List motifs,
					List<String> asList); 

	List<ESIMB> findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc(String statutFacturation);





}
