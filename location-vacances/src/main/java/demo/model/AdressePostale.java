package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AdressePostale {
	
	@Id
	@GeneratedValue
	private int Id;
	private String Adresse;
	private String CodePostal;
	private String Ville;
	@OneToOne
	private Format_pays Pays;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getAdresse() {
		return Adresse;
	}
	public void setAdresse(String adresse) {
		Adresse = adresse;
	}
	public String getCodePostal() {
		return CodePostal;
	}
	public void setCodePostal(String codePostal) {
		CodePostal = codePostal;
	}
	public String getVille() {
		return Ville;
	}
	public void setVille(String ville) {
		Ville = ville;
	}
	public Format_pays getPays() {
		return Pays;
	}
	public void setPays(Format_pays pays) {
		Pays = pays;
	}
	
	

}
