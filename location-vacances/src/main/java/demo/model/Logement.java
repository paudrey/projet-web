package demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Logement {

	@Id
	@GeneratedValue
	private int Id;
	@OneToOne
	private Utilisateur Proprietaire;
	private String Description;
	@OneToOne
	private Format_typeLogement TypeLogement;
	@OneToOne
	private AdressePostale adresse;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Photo> PhotoList;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Utilisateur getProprietaire() {
		return Proprietaire;
	}

	public void setProprietaire(Utilisateur proprietaire) {
		Proprietaire = proprietaire;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Format_typeLogement getTypeLogement() {
		return TypeLogement;
	}

	public void setTypeLogement(Format_typeLogement typeLogement) {
		TypeLogement = typeLogement;
	}

	public AdressePostale getAdresse() {
		return adresse;
	}

	public void setAdresse(AdressePostale adresse) {
		this.adresse = adresse;
	}

	public List<Photo> getPhotoList() {
		return PhotoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		PhotoList = photoList;
	}
	
}
