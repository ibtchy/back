package com.sofrecom.cobli.repository;

import com.sofrecom.cobli.models.Motif;
import com.sofrecom.cobli.models.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface MotifRepository extends JpaRepository<Motif, Integer> {
    List<Motif> findByTypePrestation(Prestation typePrestation);


}
