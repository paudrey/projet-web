package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Model.AdressePostale;
import Model.Contact;
import Model.Logement;
import Model.Photo;
import Repository.Repository_AdressePostale;
import Repository.Repository_Logement;
import Repository.Repository_Photo;
import Repository.Repository_Utilisateur;

public class Controller_Logement {
	
	@Autowired
	private Repository_Utilisateur usersRepository;
	@Autowired
	private Repository_AdressePostale adresseRepository;
	@Autowired
	private Repository_Photo photoRespository;
	@Autowired
	private Repository_Logement logementRepository;
	
	private Logement currentLog;
	
	@RequestMapping(value="/logement", method=RequestMethod.POST)
	public String requestSaveCreate(AdressePostale adresse,Logement logement, RedirectAttributes redirectAttribute)
	{
		adresseRepository.save(adresse);
		logementRepository.save(logement);
		return "redirect:home";
	}
	
	@RequestMapping(value="/photo", method=RequestMethod.POST)
	public String requestSaveCreatePhoto(Photo photo, RedirectAttributes redirectAttribute, Model model)
	{
		
		int id = (int)model.asMap().get("id");
		currentLog = logementRepository.findOne(id);
		model.addAttribute("logement", currentLog);
		model.addAttribute("photo", new Photo());
		photoRespository.save(photo);
		return "redirect:home";
	}
	
	
	
	

}
