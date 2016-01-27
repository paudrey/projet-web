package demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;







import org.thymeleaf.dom.Document;

import demo.model.AdressePostale;
import demo.model.Contact;
import demo.model.Format_pays;
import demo.model.Format_typeLogement;
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
	
	Logement currentLog=null;
	@RequestMapping(value="/createEditHousing",method=RequestMethod.GET)
	public String requestHousing(Model model)
	{	
		model.addAttribute("typeLogList", typeLogRepository.findAll());
		model.addAttribute("paysList", paysRepository.findAll());
		try{
			int id = (int)model.asMap().get("LogId");
			currentLog = logementRepository.findOne(id);
			model.addAttribute("housing", currentLog);
			return "createEditHousing";
		}
		catch(Exception e){
			model.addAttribute("housing", new Logement());
			return "createEditHousing";
		}	
	}
	
	
	
	@RequestMapping(value="/createEditHousing", method=RequestMethod.POST)
	public String requestCreate(Logement logement, RedirectAttributes redirectAttribute) throws ScriptException, IOException, NoSuchMethodException
	{
		//Récupération de l'user 
		if (currentLog == null){
			AdressePostale adresse = logement.getAdresse();
			ScriptEngineManager manager = new ScriptEngineManager();		
			//Format_typeLogement type = logement.getTypeLogement();
			//System.out.println(pays);
			adresseRepository.save(adresse);
			logementRepository.save(logement);
			
		}
		else
		{
			AdressePostale currentAdr = currentLog.getAdresse();
			AdressePostale adresse = logement.getAdresse();
			currentAdr.setAdresse(adresse.getAdresse());
			currentAdr.setCodePostal(adresse.getCodePostal());
			currentAdr.setVille(adresse.getVille());
			currentAdr.setPays(adresse.getPays());
			adresseRepository.save(currentAdr);
			currentLog.setAdresse(currentAdr);
			currentLog.setProprietaire(logement.getProprietaire());
			currentLog.setShortDescription(logement.getShortDescription());
			currentLog.setDescription(logement.getDescription());
			currentLog.setTypeLogement(logement.getTypeLogement());
			logementRepository.save(currentLog);
		}
		return "redirect:adminHousing";
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
