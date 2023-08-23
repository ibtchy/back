package com.sofrecom.cobli.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BRAME {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id_acte;

	public String getId_acte() {
		return id_acte;
	}

	public void setId_acte(String id_acte) {
		this.id_acte = id_acte;
	}
	
	
	

}
