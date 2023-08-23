package com.sofrecom.cobli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.models.TypeElement;

public interface TarificationRepository extends JpaRepository<Tarification, String> {

	//Optional<Tarification> findByCodeTarif(String codeTarif);
	Tarification findByCodeTarif(String codeTarif);

}
