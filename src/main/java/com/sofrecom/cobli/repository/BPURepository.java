package com.sofrecom.cobli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sofrecom.cobli.models.BPU;


@Repository
public interface BPURepository extends JpaRepository<BPU, Long > {

	Optional<BPU> findById(long id);
	

}
