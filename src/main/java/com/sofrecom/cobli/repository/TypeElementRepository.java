package com.sofrecom.cobli.repository;

import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.models.TypeElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeElementRepository extends JpaRepository<TypeElement, Integer>  {
    List<TypeElement> findByTypePrestation(Prestation typePrestation);


}
