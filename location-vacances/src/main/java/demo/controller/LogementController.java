package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;





import demo.model.AdressePostale;
import demo.model.Contact;
import demo.model.Logement;
import demo.model.Photo;
import demo.repository.AdressePostaleRepository;
import demo.repository.LogementRepository;
import demo.repository.PaysRepository;
import demo.repository.PhotoRepository;
import demo.repository.TypeLogementRepository;
import demo.repository.UtilisateurRepository;


@Controller
public class LogementController {

	@Autowired
	private UtilisateurRepository usersRepository;
	@Autowired
	private AdressePostaleRepository adresseRepository;
	@Autowired
	private PhotoRepository photoRespository;
	@Autowired
	private LogementRepository logementRepository;
	
	@Autowired
	PaysRepository paysRepository;
	@Autowired
	TypeLogementRepository typeLogRepository;
	
	private Logement currentLog;
	
	@RequestMapping("/createEditHousing")
	public String requestHousing(Model model)
	{	    
		model.addAttribute("typeLogList", typeLogRepository.findAll());
		model.addAttribute("paysList", paysRepository.findAll());
		model.addAttribute("housing", new Logement());
		return "createEditHousing";
	}
	
	
	
	@RequestMapping(value="/logement", method=RequestMethod.POST)
	public String requestCreate(Logement logement, RedirectAttributes redirectAttribute)
	{
		//Récupération de l'user 
		logementRepository.save(logement);
		AdressePostale adresse = logement.getAdresse();
		adresseRepository.save(adresse);
		return "home";
	}
	/*
	@RequestMapping(value="/photo", method=RequestMethod.POST)
	public String requestSaveCreatePhoto(Photo photo, RedirectAttributes redirectAttribute, Model model)
	{
		
		int id = (int)model.asMap().get("id");
		currentLog = logementRepository.findOne(id);
		model.addAttribute("logement", currentLog);
		model.addAttribute("photo", new Photo());
		photoRespository.save(photo);
		return "redirect:home";
	}*/
	
}
