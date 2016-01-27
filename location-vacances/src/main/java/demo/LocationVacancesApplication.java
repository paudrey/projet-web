package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.model.Format_pays;
import demo.model.Format_typeLogement;
import demo.repository.PaysRepository;
import demo.repository.TypeLogementRepository;

@SpringBootApplication
public class LocationVacancesApplication implements CommandLineRunner{

	@Autowired
	private PaysRepository paysRepository;
	@Autowired
	private TypeLogementRepository typeLogRepository;
	
    public static void main(String[] args) {  	
        SpringApplication.run(LocationVacancesApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
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
    	
    	Format_pays p1 = new Format_pays();
    	p1.setPays("France");
    	Format_pays p2 = new Format_pays();
    	p2.setPays("Italie");
    	Format_pays p3 = new Format_pays();
    	p3.setPays("Espagne");
    	Format_pays p4 = new Format_pays();
    	p4.setPays("Etat-Unis");
    	Format_pays p5 = new Format_pays();
    	p5.setPays("Maldives");
    	
    	paysRepository.save(p1);
    	paysRepository.save(p2);
    	paysRepository.save(p3);
    	paysRepository.save(p4);
    	paysRepository.save(p5);
    
	}
    
    
   
}
