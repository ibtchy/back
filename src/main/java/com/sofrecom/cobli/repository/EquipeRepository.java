package com.sofrecom.cobli.repository;

import com.sofrecom.cobli.models.Equipe;
import com.sofrecom.cobli.models.Graphic;
import com.sofrecom.cobli.models.Nropm;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EquipeRepository  extends JpaRepository<Equipe, Integer>{

    Optional<Equipe> findByidEquipe(int idEquipe);
}
