package com.sofrecom.cobli.repository;

import com.sofrecom.cobli.models.Nropm;
import com.sofrecom.cobli.models.Pm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PmRepository extends JpaRepository<Pm, Integer> {

Optional<Pm> findByRefPm(String refPm);
List<Pm> findByNropm(Nropm nropm);
}