package Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Format_pays {
	@Id
	@GeneratedValue
	private int Id;
	private String Pays;
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
		this.Pays = pays;
	}
	
	
}
