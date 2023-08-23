package com.sofrecom.cobli.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sofrecom.cobli.repository.Acte_traitementRepository;

@Service
public class ActeTraitementService {
	
	@Autowired
	Acte_traitementRepository acteTraitementRepository;
	
	
	public List<Object[]> getGroupedActeTraitementByAffectation() {
		return acteTraitementRepository.countByAffectation(); }

}
