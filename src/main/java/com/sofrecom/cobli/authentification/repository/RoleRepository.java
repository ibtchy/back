package com.sofrecom.cobli.authentification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.authentification.models.ERole;
import com.sofrecom.cobli.authentification.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	  Optional<Role> findByName(ERole name);


}
