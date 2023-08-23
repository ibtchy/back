package com.sofrecom.cobli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sofrecom.cobli.models.Interconnexion;

@Repository
public interface InterconnexionRepository extends JpaRepository<Interconnexion, String> {

 List<Interconnexion> findByCog(String cog);
 Optional<Interconnexion> findByCogContaining(String cog);



}
