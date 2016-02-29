package demo;

import javax.persistence.GeneratedValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.enums.UserRole;
import demo.enums.UserStatus;
import demo.model.Format_pays;
import demo.model.Format_typeLogement;
import demo.model.Login;
import demo.model.Utilisateur;
import demo.repository.LoginRepository;
import demo.repository.PaysRepository;
import demo.repository.TypeLogementRepository;
import demo.repository.UtilisateurRepository;

@SpringBootApplication
public class LocationVacancesApplication implements CommandLineRunner{

	@Autowired
	private PaysRepository paysRepository;
	@Autowired
	private TypeLogementRepository typeLogRepository;
	@Autowired
	private UtilisateurRepository userRepository;
	@Autowired
	private LoginRepository loginRepository;
	
    public static void main(String[] args) {  	
        SpringApplication.run(LocationVacancesApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//Initialisation de  la liste des types de logements
		//Format_typeLogement tl1 = new Format_typeLogement();
    	//tl1.setTypeLogement("Type de logement");
    	Format_typeLogement tl2 = new Format_typeLogement();
    	tl2.setTypeLogement("Camping");
    	Format_typeLogement tl3 = new Format_typeLogement();
    	tl3.setTypeLogement("Appartement");
    	Format_typeLogement tl4 = new Format_typeLogement();
    	tl4.setTypeLogement("Bateau");
    	Format_typeLogement tl5 = new Format_typeLogement();
    	tl5.setTypeLogement("Maison");
    	
    	//typeLogRepository.save(tl1);
    	typeLogRepository.save(tl2);
    	typeLogRepository.save(tl3);
    	typeLogRepository.save(tl4);
    	typeLogRepository.save(tl5);
    	
    	Format_pays p1 = new Format_pays(1, "Non défini");
    	Format_pays p2 = new Format_pays(2, "France");
    	Format_pays p3 = new Format_pays(3, "Italie");
    	Format_pays p4 = new Format_pays(4, "Espagne");
    	Format_pays p5 = new Format_pays(5, "États-Unis");
    	Format_pays p6 = new Format_pays(6, "Maldives");
    	
    	paysRepository.save(p1);
    	paysRepository.save(p2);
    	paysRepository.save(p3);
    	paysRepository.save(p4);
    	paysRepository.save(p5);  
    	paysRepository.save(p6);
    	
    	
    	/****Utilisateur administrateur ********/
    	 Utilisateur admin = new Utilisateur();
    	 admin.setPrenom("Admin");
    	 admin.setCurrentUserStatus(UserStatus.CONFIRMED);
    	 admin.setCurrentUserRole(UserRole.ADMIN);
    	 admin.setEmail("holidayme@gmail.com");
    	 
    	 Login adminLogin = new Login();
    	 adminLogin.setLogin("holidayme");
    	 adminLogin.setPassword("admin");
    	 adminLogin.setUser(admin);
    	 
    	 userRepository.save(admin);
    	 loginRepository.save(adminLogin);
    	
	}
    
    
   
}
