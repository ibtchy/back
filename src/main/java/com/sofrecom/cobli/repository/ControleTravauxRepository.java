package com.sofrecom.cobli.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.models.ControleTravaux;

public interface ControleTravauxRepository extends JpaRepository<ControleTravaux, String> {
	
	Optional<ControleTravaux> findByCog(String cog);
	//List<ControleTravaux> findByCog(String cog);
	List<ControleTravaux> findByCogContaining(String cog);
	List<ControleTravaux> findByAffectation(String affectation);
	List <ControleTravaux> findByMotif(String motif);
	List<ControleTravaux> findByUi(String ui);
	List<ControleTravaux> findByFi(String fi);

	List<ControleTravaux> findByMotifAndCog(String motif, String cog);



	Optional<ControleTravaux> findByIdacte(String idacte);
	List<ControleTravaux> findByTypeTravaux(String typeTravaux);
	List<ControleTravaux> findByMotifInOrMotifReaffectationInOrderByPrioriteAscDateDeadlineAsc(Collection<String> motifs,Collection<String> motifsReaffectation);



}
