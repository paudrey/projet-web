package demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Utilisateur {
	@Id
	@GeneratedValue
	private int Id;
	@OneToOne
	private Login Login;
	private String Nom;
	private String Prenom;
	private String Email;	
	private String DateNaissance;
	
	@OneToOne
	private AdressePostale Adresse;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Login getLogin() {
		return Login;
	}
	public void setLogin(Login login) {
		Login = login;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getDateNaissance() {
		return DateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		DateNaissance = dateNaissance;
	}
	public AdressePostale getAdresse() {
		return Adresse;
	}
	public void setAdresse(AdressePostale adresse) {
		Adresse = adresse;
	}
	
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	
}
