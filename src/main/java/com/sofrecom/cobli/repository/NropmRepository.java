package com.sofrecom.cobli.repository;

import com.sofrecom.cobli.models.Graphic;
import com.sofrecom.cobli.models.Nropm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NropmRepository extends JpaRepository<Nropm, String> {

Optional<Nropm> findByCog(String cog);

List<Nropm> findByMotif(String motif);

List<Nropm> findByMotifOrMotifReaffectation(String motif, String motifReaffectation);

List<Nropm> findByMotifNotIn(Collection<String> motifs);

List<Nropm> findByMotifIn(Collection<String> motifs);

List<Nropm> findByUniteIntervention(String uniteIntervention);

List<Nropm> findByAffectation(String affectation);

List<Nropm> findByMotifInOrMotifReaffectationIn(Collection<String> motifs, Collection<String> motifsReaffectation);

List<Nropm> findByMotifInOrMotifReaffectationInOrderByPrioriteAscDateDeadlineAsc(Collection<String> motifs, Collection<String> motifsReaffectation);

List<Nropm> findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc(String statutFacturation);

}