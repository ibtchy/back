package com.sofrecom.cobli.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sofrecom.cobli.models.BRAME;
import com.sofrecom.cobli.models.ControleTravaux;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;
import com.sofrecom.cobli.models.Nropm;
import com.sofrecom.cobli.repository.ControleTravauxRepository;
import com.sofrecom.cobli.repository.DESATRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.Graphic_Repository;
import com.sofrecom.cobli.repository.NropmRepository;

@Service
public class Services implements ServicesInterface{
   
	@Autowired
	ESIMBRepository ESIMBRepo;
	@Autowired
	DESATRepository DESATRepo;
	@Autowired
	Graphic_Repository Graphic_Repo;
	@Autowired
	NropmRepository nropmRepository;
	@Autowired
	ControleTravauxRepository controleTravauxRepo;
	
	@Override
	public boolean isExisteEsimb(ESIMB esimb) {
		
		List<ESIMB> esimbs = new ArrayList<ESIMB>();
		ESIMBRepo.findByCodeIMB(esimb.getCodeIMB()).forEach(esimbs::add);
		
		if(esimbs.size() > 0) {
			for(int i =0;i<esimbs.size();i++) {
				if (esimbs.get(i).getCodeBanbou().equals(esimb.getCodeBanbou()) ) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean isExisteGrafic(Graphic graphic) {
		List<Graphic> grafics = new ArrayList<Graphic>();
		Graphic_Repo.findByidGrafic(graphic.getidGrafic()).forEach(grafics::add);
		
		if(grafics.size() > 0) {
			for(int i =0;i<grafics.size();i++) {
				if (grafics.get(i).getDateTraitement().equals(graphic.getDateTraitement()) ) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean isExisteDesat(DESAT desat) {
		// TODO Auto-generated method stub
		List<DESAT> desats = new ArrayList<DESAT>();
		DESATRepo.findByFi(desat.getFi()).forEach(desats::add);
		if(desats.size()>0) {
			for(int i=0;i<desats.size();i++) {
				if (desats.get(i).getCog().equals(desat.getCog())) {
					return true;
				}
			}
		}
		return false;
	}
	
	 @Override
	public boolean isExisteDesatR(DESAT desat) {

	Optional<DESAT> desat_ = DESATRepo.findByCog(desat.getCog());
	if(desat_.isPresent()){
	return true;
	}else{
	return false;
	}

	}

	@Override
	public boolean isExisteNropm(Nropm nropm) {

	Optional<Nropm> nropm_ = nropmRepository.findByCog(nropm.getCog());
	if(nropm_.isPresent()){
	return true;
	}else{
	return false;
	}

	}

	@Override
	public boolean isExisteBrame(BRAME brame) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String importData() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isExisteCT(ControleTravaux ct) {
	    List<ControleTravaux> controleT = controleTravauxRepo.findByCogContaining(ct.getCog());
	    
	    return !controleT.isEmpty();
	}

	
	
	/* @Override
	public boolean isExisteCTT(ControleTravaux ct) {
		// TODO Auto-generated method stub
		List<ControleTravaux> cts = new ArrayList<ControleTravaux>();
		controleTravauxRepo.findByCogContaining(ct.getCog()).forEach(cts::add);
		if(cts.size()>0) {
			for(int i=0;i<cts.size();i++) {
				if (cts.get(i).getMotif().equals(ct.getMotif())) {
					return true;
				}
			}
		}
		return false;
	}
	 */
	
	
	
	
}
