package com.sofrecom.cobli.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEquipe;
	private String nom;
	
	
	public int getIdEquipe() {
		return idEquipe;
	}
	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Equipe() {
		super();
	}
	public Equipe(int idEquipe, String nom) {
		super();
		this.idEquipe = idEquipe;
		this.nom = nom;
	}
	@Override
	public String toString() {
		return "Equipe [id_equipe=" + idEquipe + ", nom=" + nom + "]";
	}

	
	
	
	
	

}