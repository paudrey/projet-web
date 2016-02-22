package demo.enums;

public enum UserStatus 
{
	TOBECONFIRMED("À confirmer"),
	CONFIRMED("Confirmé"),
	BLOCKED("Bloqué");
	
	public final String nom;
	
	UserStatus()
	{
		this.nom = name();
	}
	
	UserStatus(String nom)
	{
		this.nom = nom;
	}
}
