package demo.enums;

public enum UserRole {

	ADMIN("Administrateur"),
	USER("Utilisateur");
	
	public final String nom;
	
	UserRole()
	{
		this.nom = name();
	}
	
	UserRole(String nom)
	{
		this.nom = nom;
	}
	
}
