package com.sofrecom.cobli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.models.Prestation;

public interface PrestationRepository extends JpaRepository<Prestation, Long> {

	Optional<Prestation> findByNomPrestation(String nomPrestation);
	 Prestation findPretationByNomPrestation(String nomPrestation);

}
