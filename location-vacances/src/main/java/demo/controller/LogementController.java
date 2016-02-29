package demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.dom.Document;

import demo.model.AdressePostale;
import demo.model.Contact;
import demo.model.File;
import demo.model.Format_pays;
import demo.model.Format_typeLogement;
import demo.model.Logement;
import demo.model.Photo;
import demo.model.Utilisateur;
import demo.repository.AdressePostaleRepository;
import demo.repository.LogementRepository;
import demo.repository.PaysRepository;
import demo.repository.PhotoRepository;
import demo.repository.TypeLogementRepository;
import demo.repository.UtilisateurRepository;


@Controller
public class LogementController {

	@Autowired
	private UtilisateurRepository userRepository;
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
	
	Map<Integer, Photo> photoMap = new HashMap();
	List <Photo> listPhoto = new ArrayList();
	Logement currentLog = null;
	
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
	
	@RequestMapping("/listHousing")
	public String requestListHousing(Model model, HttpSession session)
	{
		boolean userConnected;
		Utilisateur user = (Utilisateur)session.getAttribute("user");

		if(user != null)
			userConnected = true;
		else
			userConnected = false;
		model.addAttribute("userConnected", userConnected);
		model.addAttribute("housingList", logementRepository.findAll());
		return "/listHousing";
	}
	
	@RequestMapping("/detailsHousing/{id}")
	public String requestHouseDetail(@PathVariable("id") Integer LogId, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("LogId", LogId);				
		return "redirect:/housingDetails";	
	}
	
	@RequestMapping("/createBooking/{action}/{id}")
	public String requestBooking(@PathVariable("id") Integer LogId,@PathVariable("action") String action, RedirectAttributes redirectAttributes, HttpSession session)
	{
		Utilisateur user = (Utilisateur)session.getAttribute("user");
		if(user != null)
		{
			redirectAttributes.addFlashAttribute("LogId", LogId);	
			redirectAttributes.addFlashAttribute("action", "creat");	
			return "redirect:/createEditBooking";	
		}
		else
		{
			return "redirect:/connexion";
		}
		
	}
	
	@RequestMapping(value="/housingDetails")
	public String requestDetailsHou(Model model, HttpSession session)
	{
		int id = (int)model.asMap().get("LogId");
		Logement Log = logementRepository.findOne(id);
		
		boolean userConnected;
		Utilisateur user = (Utilisateur)session.getAttribute("user");

		if(user != null)
			userConnected = true;
		else
			userConnected = false;
		model.addAttribute("userConnected", userConnected);
		
		model.addAttribute("housing", Log);
		return "housingDetails";
	}
	@RequestMapping(value="/createEditHousing", method=RequestMethod.POST)
	public String requestCreate(Logement logement, RedirectAttributes redirectAttribute, Model model, HttpSession session) throws ScriptException, IOException, NoSuchMethodException
	{
		
		Utilisateur user = (Utilisateur)session.getAttribute("user");
		if(user != null)
		{
			//Récupération de l'user 
			if (currentLog == null){
				AdressePostale adresse = logement.getAdresse();
				logement.setProprietaire(user);
				List<Photo> newPhotoList = new ArrayList<Photo>();
				logement.setPhotoList(newPhotoList);
				
				try
				{
					adresseRepository.save(adresse);
					logementRepository.save(logement);
					
					List<Logement> logList = user.getLogementList();
					logList.add(logement);
					
					user.setLogementList(logList);
			
					userRepository.save(user);
				}
				catch(Exception e)
				{}
				
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
				currentLog.setPrixTTC(logement.getPrixTTC());
				currentLog.setShortDescription(logement.getShortDescription());
				currentLog.setDescription(logement.getDescription());
				currentLog.setTypeLogement(logement.getTypeLogement());
				logementRepository.save(currentLog);
			}
		}
		return "redirect:adminHousing";
	}	
}
