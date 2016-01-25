package Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Photo {
	
	@Id
	@GeneratedValue
	private int Id;
	private String Titre;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getTitre() {
		return Titre;
	}
	public void setTitre(String titre) {
		Titre = titre;
	}

	
	

}
