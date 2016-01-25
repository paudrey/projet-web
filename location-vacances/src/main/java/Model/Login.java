package Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Login {
	
	@Id
	@GeneratedValue
	private int Id;
	private String Login;
	private String Password;
	
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
	
	
}
