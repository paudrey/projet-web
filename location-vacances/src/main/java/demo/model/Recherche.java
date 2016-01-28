package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Recherche {

	@Id
	@GeneratedValue
	private int Id;
	private int SelectedCountryId;
	
	private String Ville;
	private int TypeLogementId;
	private String DateDebut;
	private String DateFin;
	private int NbVoyageurs;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getVille() {
		return Ville;
	}
	public void setVille(String ville) {
		Ville = ville;
	}
	public String getDateDebut() {
		return DateDebut;
	}
	public void setDateDebut(String dateDebut) {
		DateDebut = dateDebut;
	}
	public String getDateFin() {
		return DateFin;
	}
	public void setDateFin(String dateFin) {
		DateFin = dateFin;
	}
	public int getNbVoyageurs() {
		return NbVoyageurs;
	}
	public void setNbVoyageurs(int nbVoyageurs) {
		NbVoyageurs = nbVoyageurs;
	}
	public int getSelectedCountryId() {
		return SelectedCountryId;
	}
	public void setSelectedCountryId(int selectedCountryId) {
		SelectedCountryId = selectedCountryId;
	}
	public int getTypeLogementId() {
		return TypeLogementId;
	}
	public void setTypeLogementId(int typeLogementId) {
		TypeLogementId = typeLogementId;
	}




	
	
}
