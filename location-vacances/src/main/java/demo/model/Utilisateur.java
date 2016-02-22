package demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import demo.enums.UserStatus;

@Entity
public class Utilisateur {
	@Id
	@GeneratedValue
	private int Id;
	/*@OneToOne
	private Login Login;*/
	private String Nom;
	private String Prenom;
	private String Email;	
	private String DateNaissance;
	
	@OneToOne
	private AdressePostale Adresse;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Logement> LogementList;
	
	private int PaysId;
	@OneToOne
	private Format_pays FormatPays;
	
	private UserStatus CurrentUserStatus;
	

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	/*public Login getLogin() {
		return Login;
	}
	public void setLogin(Login login) {
		Login = login;
	}*/
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
	
	public List<Logement> getLogementList() {
		return LogementList;
	}
	public void setLogementList(List<Logement> logementList) {
		LogementList = logementList;
	}
	public int getPaysId() {
		return PaysId;
	}
	public void setPaysId(int paysId) {
		PaysId = paysId;
	}
	public Format_pays getFormatPays() {
		return FormatPays;
	}
	public void setFormatPays(Format_pays formatPays) {
		FormatPays = formatPays;
	}
	public UserStatus getCurrentUserStatus() {
		return CurrentUserStatus;
	}
	public void setCurrentUserStatus(UserStatus currentUserStatus) {
		CurrentUserStatus = currentUserStatus;
	}

	
	
}
