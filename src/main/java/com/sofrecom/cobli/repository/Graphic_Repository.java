package com.sofrecom.cobli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;

public interface Graphic_Repository extends JpaRepository<Graphic, String> {
	//Graphic findByid_Grafic(String id_Grafic);
	 //ist<Graphic> findByidContaining(String id_Grafic);
	 //Graphic  findBycode_imb(String id_Grafic); 
	List<Graphic>  findByidGrafic(String id_Grafic);
	 Graphic findByidacte(String idacte);
	 List<Graphic> findByidGraficContaining(String id_Grafic);
	 List<Graphic> findBydateTraitement(String dateTraitement);
	 List<Graphic> findBydateTraitementContaining(String dateTraitement);
	 List<Graphic> findByAffectation(String affectation);
	 
	 
	Optional<Graphic> findByIdGrafic(String id_Grafic);

	List<Graphic> findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc(String statutFacturation);




}
