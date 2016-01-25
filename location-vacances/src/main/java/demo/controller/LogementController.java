package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import demo.model.AdressePostale;
import demo.model.Logement;
import demo.model.Photo;
import demo.repository.AdressePostaleRepository;
import demo.repository.LogementRepository;
import demo.repository.PhotoRepository;
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
