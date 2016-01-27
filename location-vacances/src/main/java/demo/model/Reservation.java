package demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Reservation {
	@Id
	@GeneratedValue
	private int Id;
	@OneToOne
	private Utilisateur locataire;
	@OneToOne
	private Logement Logement;
	//@OneToOne
	private String statut;
	private String dateDebut;
	private String dateFin;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Utilisateur getLocataire() {
		return locataire;
	}
	public void setLocataire(Utilisateur locataire) {
		this.locataire = locataire;
	}
	public Logement getLogement() {
		return Logement;
	}
	public void setLogement(Logement logement) {
		Logement = logement;
	}
	
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public String getDateFin() {
		return dateFin;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	
	
}
