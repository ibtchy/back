package com.sofrecom.cobli.authentification.payload.request;

import com.sofrecom.cobli.models.Equipe;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {
	@NotBlank
    @Size(min = 3, max = 20)
    private String username;
	
	@NotBlank
    @Size(min = 3, max = 20)
    private String nom;
	
	@NotBlank
    @Size(min = 3, max = 20)
    private String prenom;
	
	private int idEquipe;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
  //  private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private int telephone;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
 
    public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    /*   public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }*/

}