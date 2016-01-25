package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Format_typeLogement {
	
	@Id
	@GeneratedValue
	private int Id;
	private String TypeLogement;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getTypeLogement() {
		return TypeLogement;
	}
	public void setTypeLogement(String typeLogement) {
		this.TypeLogement = typeLogement;
	}

}
