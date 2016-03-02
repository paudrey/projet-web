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
import demo.model.Reservation;
import demo.model.Utilisateur;
import demo.repository.AdressePostaleRepository;
import demo.repository.LogementRepository;
import demo.repository.LoginRepository;
import demo.repository.PaysRepository;
import demo.repository.PhotoRepository;
import demo.repository.ReservationRepository;
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
	@Autowired
	private ReservationRepository resRepository;
	
    public static void main(String[] args) {  	
        SpringApplication.run(LocationVacancesApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//Initialisation de  la liste des types de logements
		Format_typeLogement tl1 = new Format_typeLogement();
    	tl1.setTypeLogement("Non défini");
    	Format_typeLogement tl2 = new Format_typeLogement();
    	tl2.setTypeLogement("Camping");
    	Format_typeLogement tl3 = new Format_typeLogement();
    	tl3.setTypeLogement("Appartement");
    	Format_typeLogement tl4 = new Format_typeLogement();
    	tl4.setTypeLogement("Bateau");
    	Format_typeLogement tl5 = new Format_typeLogement();
    	tl5.setTypeLogement("Maison");
    	
    	typeLogRepository.save(tl1);
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
		
		
		
		 /*******Utilisateur hantzperg **********/
    	AdressePostale adressHantzperg = new AdressePostale();
    	adressHantzperg.setAdresse("21 rue de la maladière");
    	adressHantzperg.setCodePostal("21121");
    	adressHantzperg.setVille("Daix");
    	adressHantzperg.setPays("France");
    	
     	adresseRepository.save(adressHantzperg);
     	
		Utilisateur hantzperg = new Utilisateur();
		hantzperg.setPrenom("Sophie");
		hantzperg.setNom("Hantzperg");
		hantzperg.setAdresse(adressHantzperg);
		hantzperg.setCurrentUserStatus(UserStatus.CONFIRMED);
		hantzperg.setCurrentUserRole(UserRole.USER);
		hantzperg.setEmail("sophie.hantzperg@gmail.com");
		hantzperg.setPaysId(1);
		userRepository.save(hantzperg);
     	 
		Login loginHantzperg = new Login();
		loginHantzperg.setId(hantzperg.getId());
		loginHantzperg.setLogin("sophie.hantzperg");
		loginHantzperg.setPassword(DigestUtils.sha512Hex("bonjour"));
		loginHantzperg.setUser(hantzperg);   	 	 
		loginRepository.save(loginHantzperg);
		
		
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
    	
    	
    	
    	
    	
    	//*******APPARTEMENT ITALIE *****/////
		
		AdressePostale adresseLogAp3 = new AdressePostale();
		adresseLogAp3.setAdresse("10 via Aureggio");
		adresseLogAp3.setCodePostal("22021");
		adresseLogAp3.setVille("Bellagio");
		adresseLogAp3.setPays("Italie");
     	adresseRepository.save(adresseLogAp3);
     	Logement logApautot3 = new Logement();
     	logApautot3.setAdresse(adresseLogAp3);
     	logApautot3.setShortDescription("Appart. 43 m2 terrasse vue lac de Côme");
     	logApautot3.setProprietaire(apautot);
     	logApautot3.setDescription("Au bord du lac de Côme (Italie) à 100 mètres d'un petit port, à vendre appartement dans maison traditionnelle de 3 étages.Avec un salon cuisine, deux chambre, une salle de bain et une terrasse avec une magnifique vue sur le lac de Côme.");
     	logApautot3.setTypeLogement("Appartement");
     	logApautot3.setPrixTTC(150.32);
		logRepository.save(logApautot3);
		
		List<Photo> photoList3 = new ArrayList<Photo>();
		Photo log3Photo1 = new Photo();
		log3Photo1.setName("Appartement1");
		log3Photo1.setPath("src/main/resources/static/logement/" + logApautot3.getId());
		log3Photo1.setPathImage("../logement/" + logApautot3.getId() +"/appartement_1.jpeg");
		photoRepository.save(log3Photo1);
		photoList3.add(log3Photo1);
    	Photo log3Photo2 = new Photo();
    	log3Photo2.setName("Appartement2");
    	log3Photo2.setPath("src/main/resources/static/logement/" + logApautot3.getId());
    	log3Photo2.setPathImage("../logement/" + logApautot3.getId() +"/appartement_2.jpeg");
		photoRepository.save(log3Photo2);
		photoList3.add(log3Photo2);
    	
		logApautot3.setPhotoList(photoList3);
    	logRepository.save(logApautot3);
    	
    	//*******CAMPING  SUD*****/////
		
		AdressePostale adresseLogAp4 = new AdressePostale();
		adresseLogAp4.setAdresse("189 Les grands châteaux de Villepey");
		adresseLogAp4.setCodePostal("83370");
		adresseLogAp4.setVille("Saint-Aygulf");
		adresseLogAp4.setPays("France");
     	adresseRepository.save(adresseLogAp4);
     	Logement logApautot4 = new Logement();
     	logApautot4.setAdresse(adresseLogAp4);
     	logApautot4.setShortDescription("Camping Riviera s'Azur");
     	logApautot4.setProprietaire(apautot);
     	logApautot4.setDescription("A côté de Fréjus, à seulement 4km de la plage.  Sur place, un beau Parc Aquatique avec toboggans et des Animations familiales en journée et en soirée. ");
     	logApautot4.setTypeLogement("Camping");
     	logApautot4.setPrixTTC(75.21);
		logRepository.save(logApautot4);
		
		List<Photo> photoList4 = new ArrayList<Photo>();
		Photo log4Photo1 = new Photo();
		log4Photo1.setName("Camping1");
		log4Photo1.setPath("src/main/resources/static/logement/" + logApautot4.getId());
		log4Photo1.setPathImage("../logement/" + logApautot4.getId() +"/camping_1.jpg");
		photoRepository.save(log4Photo1);
		photoList4.add(log4Photo1);
    	Photo log4Photo2 = new Photo();
    	log4Photo2.setName("Camping2");
    	log4Photo2.setPath("src/main/resources/static/logement/" + logApautot4.getId());
    	log4Photo2.setPathImage("../logement/" + logApautot4.getId() +"/camping_2.jpg");
		photoRepository.save(log4Photo2);
		photoList4.add(log4Photo2);
		Photo log4Photo3 = new Photo();
		log4Photo3.setName("Camping3");
		log4Photo3.setPath("src/main/resources/static/logement/" + logApautot4.getId());
		log4Photo3.setPathImage("../logement/" + logApautot4.getId() +"/camping_3.jpg");
		photoRepository.save(log4Photo3);
		photoList4.add(log4Photo3);
		
		logApautot4.setPhotoList(photoList4);
    	logRepository.save(logApautot4);
    	
    	
    	//*******BATEAU*****/////

		AdressePostale adresseLogAp6 = new AdressePostale();
		adresseLogAp6.setAdresse("Raguenez");
		adresseLogAp6.setCodePostal("29920");
		adresseLogAp6.setVille("Nevez");
		adresseLogAp6.setPays("France");
     	adresseRepository.save(adresseLogAp6);
     	Logement logApautot6 = new Logement();
     	logApautot6.setAdresse(adresseLogAp6);
     	logApautot6.setShortDescription("Charon");
     	logApautot6.setProprietaire(apautot);
     	logApautot6.setDescription("Magnifique bateau de rêve qui ruinera votre compte en banque mais très confortable. Avec 4 chambres et 5 salles de bain.");
     	logApautot6.setTypeLogement("Bateau");
     	logApautot6.setPrixTTC(1232.25);
		logRepository.save(logApautot6);
		
		List<Photo> photoList6 = new ArrayList<Photo>();
		Photo log6Photo1 = new Photo();
		log6Photo1.setName("Bateau1");
		log6Photo1.setPath("src/main/resources/static/logement/" + logApautot6.getId());
		log6Photo1.setPathImage("../logement/" + logApautot6.getId() +"/bateau_1.jpg");
		photoRepository.save(log6Photo1);
		photoList6.add(log6Photo1);
    	Photo log6Photo2 = new Photo();
    	log6Photo2.setName("Bateau2");
    	log6Photo2.setPath("src/main/resources/static/logement/" + logApautot6.getId());
    	log6Photo2.setPathImage("../logement/" + logApautot6.getId() +"/bateau_2.jpg");
		photoRepository.save(log6Photo2);
		photoList6.add(log6Photo2);
		Photo log6Photo3 = new Photo();
		log6Photo3.setName("Bateau3");
		log6Photo3.setPath("src/main/resources/static/logement/" + logApautot6.getId());
		log6Photo3.setPathImage("../logement/" + logApautot6.getId() +"/bateau_3.jpg");
		photoRepository.save(log6Photo3);
		photoList6.add(log6Photo3);
    	Photo log6Photo4 = new Photo();
    	log6Photo4.setName("Bateau4");
    	log6Photo4.setPath("src/main/resources/static/logement/" + logApautot6.getId());
    	log6Photo4.setPathImage("../logement/" + logApautot6.getId() +"/bateau_4.jpg");
		photoRepository.save(log6Photo4);
		photoList6.add(log6Photo4);
		
		
		logApautot6.setPhotoList(photoList6);
    	logRepository.save(logApautot6);
    	
    	/********Logement de hantzberg **********/
    	//*******MAISON *****/////
		
		AdressePostale adresseLogSh2 = new AdressePostale();
		adresseLogSh2.setAdresse("10 rue des Fleurs");
		adresseLogSh2.setCodePostal("83600");
		adresseLogSh2.setVille("Fréjus");
		adresseLogSh2.setPays("France");
     	adresseRepository.save(adresseLogSh2);
     	Logement logHantzperg2 = new Logement();
     	logHantzperg2.setAdresse(adresseLogSh2);
     	logHantzperg2.setShortDescription("Une grande Villa Contemporaine au calme dans un quartier résidentiel");
     	logHantzperg2.setProprietaire(hantzperg);
     	logHantzperg2.setDescription("Dans un quartier résidentiel, au calme, découvrez cette villla contemporaine récente, offrant un séjour-salon plein sud, cuisine américaine équipée. Avec 4 chambres, 4 salles d´eau, 2 très belles terrasses et une piscine.");
     	logHantzperg2.setTypeLogement("Maison");
     	logHantzperg2.setPrixTTC(725.32);
		logRepository.save(logHantzperg2);
		
		List<Photo> photoList2 = new ArrayList<Photo>();
		Photo log2Photo1 = new Photo();
		log2Photo1.setName("Maison1");
		log2Photo1.setPath("src/main/resources/static/logement/" + logHantzperg2.getId());
		log2Photo1.setPathImage("../logement/" + logHantzperg2.getId() +"/maison_1.jpg");
		photoRepository.save(log2Photo1);
		photoList2.add(log2Photo1);
    	Photo log2Photo2 = new Photo();
    	log2Photo2.setName("Maison2");
    	log2Photo2.setPath("src/main/resources/static/logement/" + logHantzperg2.getId());
    	log2Photo2.setPathImage("../logement/" + logHantzperg2.getId() +"/maison_2.jpg");
		photoRepository.save(log2Photo2);
		photoList2.add(log2Photo2);
    	Photo log2Photo3 = new Photo();
    	log2Photo3.setName("Maison3");
    	log2Photo3.setPath("src/main/resources/static/logement/" + logHantzperg2.getId());
    	log2Photo3.setPathImage("../logement/" + logHantzperg2.getId() +"/maison_3.jpg");
		photoRepository.save(log2Photo3);
		photoList2.add(log2Photo3);
    	Photo log2Photo4 = new Photo();
    	log2Photo4.setName("Maison4");
    	log2Photo4.setPath("src/main/resources/static/logement/" + logHantzperg2.getId());
    	log2Photo4.setPathImage("../logement/" + logHantzperg2.getId() +"/maison_4.jpg");
		photoRepository.save(log2Photo4);
		photoList2.add(log2Photo4);
    	
		logHantzperg2.setPhotoList(photoList2);
    	logRepository.save(logHantzperg2);
    	
    	//*******CAMPING  BRETAGNE*****/////

		AdressePostale adresseLogSh5 = new AdressePostale();
		adresseLogSh5.setAdresse("Raguenez");
		adresseLogSh5.setCodePostal("29920");
		adresseLogSh5.setVille("Nevez");
		adresseLogSh5.setPays("France");
     	adresseRepository.save(adresseLogSh5);
     	Logement logHantzperg5 = new Logement();
     	logHantzperg5.setAdresse(adresseLogSh5);
     	logHantzperg5.setShortDescription("Camping Les 2 fontaines");
     	logHantzperg5.setProprietaire(hantzperg);
     	logHantzperg5.setDescription("À seulement 900 mètres de la plage. Ici l’air iodé regonflera à bloc vos poumons. Repos, confort et découverte seront les maitres-mots de vos vacances. Sur place, un beau Parc Aquatique avec toboggans et des Animations familiales en journée et en soirée.");
     	logHantzperg5.setTypeLogement("Camping");
     	logHantzperg5.setPrixTTC(55.26);
		logRepository.save(logHantzperg5);
		
		List<Photo> photoList5 = new ArrayList<Photo>();
		Photo log5Photo1 = new Photo();
		log5Photo1.setName("Camping1");
		log5Photo1.setPath("src/main/resources/static/logement/" + logHantzperg5.getId());
		log5Photo1.setPathImage("../logement/" + logHantzperg5.getId() +"/camping_1.jpg");
		photoRepository.save(log5Photo1);
		photoList5.add(log5Photo1);
    	Photo log5Photo2 = new Photo();
    	log5Photo2.setName("Camping2");
    	log5Photo2.setPath("src/main/resources/static/logement/" + logHantzperg5.getId());
    	log5Photo2.setPathImage("../logement/" + logHantzperg5.getId() +"/camping_2.jpg");
		photoRepository.save(log5Photo2);
		photoList5.add(log5Photo2);
		
		
		logHantzperg5.setPhotoList(photoList5);
    	logRepository.save(logHantzperg5);
    	
    	
    	/********Réservation de hantzperg**********/
    	//*******réservation bateau*****/////
    	Reservation resHantzperg1 = new Reservation();
    	resHantzperg1.setLogement(logApautot6);
    	resHantzperg1.setLocataire(hantzperg);
    	resHantzperg1.setDateDebut("11/04/2016");
    	resHantzperg1.setDateFin("26/04/2016");
    	resHantzperg1.setStatut("Validée");
    	resRepository.save(resHantzperg1);
    	
    	//*******réservation appartement*****/////
    	Reservation resHantzperg2 = new Reservation();
    	resHantzperg2.setLogement(logApautot3);
    	resHantzperg2.setLocataire(hantzperg);
    	resHantzperg2.setDateDebut("13/06/2016");
    	resHantzperg2.setDateFin("20/06/2016");
    	resHantzperg2.setStatut("Annulation validée par le propriètaire");
    	resRepository.save(resHantzperg2);
    	
    	//*******réservation ferme*****/////
    	Reservation resHantzperg3 = new Reservation();
    	resHantzperg3.setLogement(logApautot1);
    	resHantzperg3.setLocataire(hantzperg);
    	resHantzperg3.setDateDebut("08/08/2016");
    	resHantzperg3.setDateFin("30/08/2016");
    	resHantzperg3.setStatut("Validée par le locataire");
    	resRepository.save(resHantzperg3);
    	
    	
    	/********Réservation de hantzperg**********/
    	//*******réservation maison****/////
    	Reservation resApautot1 = new Reservation();
    	resApautot1.setLogement(logHantzperg2);
    	resApautot1.setLocataire(apautot);
    	resApautot1.setDateDebut("29/07/12016");
    	resApautot1.setDateFin("20/08/2016");
    	resApautot1.setStatut("Modification : en attente validation");
    	resRepository.save(resApautot1);
    	
    	//*******réservation camping bretagne*****/////
    	Reservation resApautot2 = new Reservation();
    	resApautot2.setLogement(logHantzperg5);
    	resApautot2.setLocataire(apautot);
    	resApautot2.setDateDebut("05/05/2016");
    	resApautot2.setDateFin("15/05/2016");
    	resApautot2.setStatut("En attente d'annulation");
    	resRepository.save(resApautot2);
    	
    	
	}
	
	
    
    
   
}
