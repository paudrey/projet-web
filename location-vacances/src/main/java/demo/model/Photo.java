package demo.model;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Photo {
	
	@Id
	@GeneratedValue
	private int Id;
	private String Name;
	private String Path;
	
	public Photo(){
		super();
	}
	
	public Photo(String path) {
		super();
		Path = path;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}

}
