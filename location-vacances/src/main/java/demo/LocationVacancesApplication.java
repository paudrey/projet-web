package demo;

import javax.persistence.GeneratedValue;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.enums.UserRole;
import demo.enums.UserStatus;
import demo.model.AdressePostale;
import demo.model.Format_pays;
import demo.model.Format_typeLogement;
import demo.model.Login;
import demo.model.Utilisateur;
import demo.repository.AdressePostaleRepository;
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
	@Autowired
	private AdressePostaleRepository adresseRepository;
	
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
    	AdressePostale adressAdmin = new AdressePostale();
    	adresseRepository.save(adressAdmin);
    	
    	 Utilisateur admin = new Utilisateur();
    	 admin.setPrenom("Super");
    	 admin.setNom("Administrateur");
    	 admin.setAdresse(adressAdmin);
    	 admin.setCurrentUserStatus(UserStatus.CONFIRMED);
    	 admin.setCurrentUserRole(UserRole.ADMIN);
    	 admin.setEmail("holidayme@gmail.com");
    	//admin.setFormatPays(paysRepository.findOne(1));
    	 admin.setPaysId(1);
    	 userRepository.save(admin);
    	 
    	 Login adminLogin = new Login();
    	 adminLogin.setId(admin.getId());
    	 adminLogin.setLogin("holidayme");
    	 adminLogin.setPassword(DigestUtils.sha512Hex("admin"));
    	 adminLogin.setUser(admin);   	 	 
    	 loginRepository.save(adminLogin);
    	
	}
    
    
   
}
