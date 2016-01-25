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
	private double prixTTC;
	@OneToOne
	private Format_statutReservation statut;
	private Date dateDebut;
	private Date dateFin;
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
	public double getPrixTTC() {
		return prixTTC;
	}
	public void setPrixTTC(double prixTTC) {
		this.prixTTC = prixTTC;
	}
	public Format_statutReservation getStatut() {
		return statut;
	}
	public void setStatut(Format_statutReservation statut) {
		this.statut = statut;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	
}
