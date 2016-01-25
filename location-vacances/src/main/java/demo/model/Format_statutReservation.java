package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Format_statutReservation {
	
	@Id
	@GeneratedValue
	private int Id;
	private String Statut;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getStatut() {
		return Statut;
	}
	public void setStatut(String statut) {
		this.Statut = statut;
	}
	

}
