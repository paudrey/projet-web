package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Login {
	
	@Id
	private int Id;
	private String Login;
	private String Password;
	@OneToOne Utilisateur User;
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getLogin() {
		return Login;
	}
	public void setLogin(String login) {
		Login = login;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public Utilisateur getUser() {
		return User;
	}
	public void setUser(Utilisateur user) {
		User = user;
	}
	
	
}
