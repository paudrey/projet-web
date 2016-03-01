package demo;

import java.util.ArrayList;
import java.util.List;

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
import demo.model.Logement;
import demo.model.Login;
import demo.model.Photo;
import demo.model.Utilisateur;
import demo.repository.AdressePostaleRepository;
import demo.repository.LogementRepository;
import demo.repository.LoginRepository;
import demo.repository.PaysRepository;
import demo.repository.PhotoRepository;
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
	@Autowired
	private LogementRepository logRepository;
	@Autowired
	private PhotoRepository photoRepository;
	
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
    	 admin.setEmail("holidayme.project@gmail.com");
    	//admin.setFormatPays(paysRepository.findOne(1));
    	 admin.setPaysId(1);
    	 userRepository.save(admin);
    	 
    	 Login adminLogin = new Login();
    	 adminLogin.setId(admin.getId());
    	 adminLogin.setLogin("holidayme");
    	 adminLogin.setPassword(DigestUtils.sha512Hex("admin"));
    	 adminLogin.setUser(admin);   	 	 
    	 loginRepository.save(adminLogin);
    	 
    	 
    	 /*******Utilisateur apautot **********/
    	AdressePostale adressApautot = new AdressePostale();
    	adressApautot.setAdresse("1 rue Paul Eluard");
    	adressApautot.setCodePostal("70400");
		adressApautot.setVille("Héricourt");
		adressApautot.setPays("France");
    	
     	adresseRepository.save(adressApautot);
     	
		Utilisateur apautot = new Utilisateur();
		apautot.setPrenom("Audrey");
		apautot.setNom("Pautot");
		apautot.setAdresse(adressApautot);
		apautot.setCurrentUserStatus(UserStatus.CONFIRMED);
		apautot.setCurrentUserRole(UserRole.USER);
		apautot.setEmail("audrey.pautot@gmail.com");
		apautot.setPaysId(1);
		userRepository.save(apautot);
     	 
		Login loginApautot = new Login();
		loginApautot.setId(apautot.getId());
		loginApautot.setLogin("audrey.pautot");
		loginApautot.setPassword(DigestUtils.sha512Hex("test"));
		loginApautot.setUser(apautot);   	 	 
		loginRepository.save(loginApautot);
		
		
		/********Logement de apautot **********/
		//*****FERME*******//
		
		
		AdressePostale adresseLogAp1 = new AdressePostale();
		adresseLogAp1.setAdresse("5 rue des Pommes");
		adresseLogAp1.setCodePostal("89740");
		adresseLogAp1.setVille("Cruzy le Chatel");
		adresseLogAp1.setPays("France");
     	adresseRepository.save(adresseLogAp1);
     	
     	Logement logApautot1 = new Logement();
		logApautot1.setAdresse(adresseLogAp1);
		logApautot1.setShortDescription("Ferme avec piscine, gîte et vue, belle situation");
		logApautot1.setProprietaire(apautot);
		logApautot1.setDescription("Cette charmante propriété avec piscine, gîte séparé profite d'une vue imprenable avec sa situation dans un village médiéval niché au dessus d'une colline à la frontière de la Champagne et la Côte d'Or.");
		logApautot1.setTypeLogement("Maison");
		logApautot1.setPrixTTC(426.32);
		logRepository.save(logApautot1);
		
		List<Photo> photoList1 = new ArrayList<Photo>();
		Photo log1Photo1 = new Photo();
		log1Photo1.setName("Ferme1");
		log1Photo1.setPath("src/main/resources/static/logement/" + logApautot1.getId());
		log1Photo1.setPathImage("../logement/" + logApautot1.getId() +"/ferme_1.jpg");
		photoRepository.save(log1Photo1);
    	photoList1.add(log1Photo1);
    	Photo log1Photo2 = new Photo();
    	log1Photo2.setName("Ferme2");
    	log1Photo2.setPath("src/main/resources/static/logement/" + logApautot1.getId());
    	log1Photo2.setPathImage("../logement/" + logApautot1.getId() +"/ferme_2.jpg");
		photoRepository.save(log1Photo2);
    	photoList1.add(log1Photo2);
    	Photo log1Photo3 = new Photo();
    	log1Photo3.setName("Ferme3");
		log1Photo3.setPath("src/main/resources/static/logement/" + logApautot1.getId());
		log1Photo3.setPathImage("../logement/" + logApautot1.getId() +"/ferme_3.jpg");
		photoRepository.save(log1Photo3);
    	photoList1.add(log1Photo3);
    	Photo log1Photo4 = new Photo();
    	log1Photo4.setName("Ferme4");
    	log1Photo4.setPath("src/main/resources/static/logement/" + logApautot1.getId());
    	log1Photo4.setPathImage("../logement/" + logApautot1.getId() +"/ferme_4.jpg");
		photoRepository.save(log1Photo4);
    	photoList1.add(log1Photo4);
    	
    	logApautot1.setPhotoList(photoList1);
    	logRepository.save(logApautot1);
    	
    	
    	//*******MAISON *****/////
    		
		AdressePostale adresseLogAp2 = new AdressePostale();
		adresseLogAp2.setAdresse("10 rue des Fleurs");
		adresseLogAp2.setCodePostal("83600");
		adresseLogAp2.setVille("Fréjus");
		adresseLogAp2.setPays("France");
     	adresseRepository.save(adresseLogAp2);
     	Logement logApautot2 = new Logement();
     	logApautot2.setAdresse(adresseLogAp2);
     	logApautot2.setShortDescription("Une grande Villa Contemporaine au calme dans un quartier résidentiel");
     	logApautot2.setProprietaire(apautot);
     	logApautot2.setDescription("Dans un quartier résidentiel, au calme, découvrez cette villla contemporaine récente, offrant un séjour-salon plein sud, cuisine américaine équipée. Avec 4 chambres, 4 salles d´eau, 2 très belles terrasses et une piscine.");
     	logApautot2.setTypeLogement("Maison");
     	logApautot2.setPrixTTC(725.32);
		logRepository.save(logApautot2);
		
		List<Photo> photoList2 = new ArrayList<Photo>();
		Photo log2Photo1 = new Photo();
		log2Photo1.setName("Maison1");
		log2Photo1.setPath("src/main/resources/static/logement/" + logApautot2.getId());
		log2Photo1.setPathImage("../logement/" + logApautot2.getId() +"/maison_1.jpg");
		photoRepository.save(log2Photo1);
		photoList2.add(log2Photo1);
    	Photo log2Photo2 = new Photo();
    	log2Photo2.setName("Maison2");
    	log2Photo2.setPath("src/main/resources/static/logement/" + logApautot2.getId());
    	log2Photo2.setPathImage("../logement/" + logApautot2.getId() +"/maison_2.jpg");
		photoRepository.save(log2Photo2);
		photoList2.add(log2Photo2);
    	Photo log2Photo3 = new Photo();
    	log2Photo3.setName("Maison3");
    	log2Photo3.setPath("src/main/resources/static/logement/" + logApautot2.getId());
    	log2Photo3.setPathImage("../logement/" + logApautot2.getId() +"/maison_3.jpg");
		photoRepository.save(log2Photo3);
		photoList2.add(log2Photo3);
    	Photo log2Photo4 = new Photo();
    	log2Photo4.setName("Maison4");
    	log2Photo4.setPath("src/main/resources/static/logement/" + logApautot2.getId());
    	log2Photo4.setPathImage("../logement/" + logApautot2.getId() +"/maison_4.jpg");
		photoRepository.save(log2Photo4);
		photoList2.add(log2Photo4);
    	
    	logApautot2.setPhotoList(photoList2);
    	logRepository.save(logApautot2);
	}
    
    
   
}
