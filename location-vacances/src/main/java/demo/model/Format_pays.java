package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Format_pays {
	@Id
	private int Id;
	private String Pays;
	
	public Format_pays() {
		super();
	}
	
	public Format_pays(int id, String pays) {
		super();
		Id = id;
		Pays = pays;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getPays() {
		return Pays;
	}

	public void setPays(String pays) {
		Pays = pays;
	}
	

	
	
}
