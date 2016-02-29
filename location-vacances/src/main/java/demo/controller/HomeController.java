package demo.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.Format_pays;
import demo.model.Format_typeLogement;
import demo.model.Logement;
import demo.model.Recherche;
import demo.model.Reservation;
import demo.model.Utilisateur;
import demo.repository.LogementRepository;
import demo.repository.PaysRepository;
import demo.repository.RechercheRepository;
import demo.repository.ReservationRepository;
import demo.repository.TypeLogementRepository;
import demo.repository.UtilisateurRepository;
import demo.workFunctions.UserManager;

@Controller
public class HomeController {

	@Autowired
	TypeLogementRepository typeLogRepository;
	@Autowired
	PaysRepository paysRepository;
	@Autowired
	RechercheRepository rechercheRepository;
	
	@Autowired
	private LogementRepository logementRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	List<Format_pays> countryList = new ArrayList<Format_pays>();
	List<Format_typeLogement> typeLogementList = new ArrayList<Format_typeLogement>();
	
	UserManager userManager = new UserManager();
	Utilisateur user;
	@Autowired
	private UtilisateurRepository userRepository;
	
	@RequestMapping("/home")
	public String requestHome(Model model, HttpSession session)
	{	
		boolean userConnected;
		Utilisateur user = (Utilisateur)session.getAttribute("user");

		if(user != null)
			userConnected = true;
		else
			userConnected = false;
		
		countryList.clear();
		countryList.addAll((List<Format_pays>)paysRepository.findAll());
		typeLogementList.addAll((List<Format_typeLogement>)typeLogRepository.findAll());
		
		model.addAttribute("userConnected", userConnected);
		model.addAttribute("search", new Recherche());
		model.addAttribute("countryList", countryList);
		model.addAttribute("typeLogList", typeLogRepository.findAll());
		return "home";
	}
	
	/***Mise en place de la recherche 
	 * @throws ParseException *******/
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String requestSearch(Recherche recherche, RedirectAttributes redirectAttribute, Model model, HttpSession session) 
	{	  
		List<Logement> listLog = (List<Logement>) logementRepository.findAll();
		List<Logement> listHousing;
		
		
		List <Logement> list = listLog.stream()
				.filter(l-> l.getTypeLogement().equalsIgnoreCase(recherche.getTypeLogement()))
				.collect(Collectors.toList());
		
		listHousing = list;
		
		if(!recherche.getSelectedCountry().equals("Non défini"))
		{
			List <Logement> listFilter = listHousing.stream()
					.filter(l->l.getAdresse().getPays().equalsIgnoreCase(recherche.getSelectedCountry()))
					.collect(Collectors.toList());
			listHousing = listFilter;
		}
		if(!recherche.getVille().equals(""))
		{
			List <Logement> listFilter = listHousing.stream()
					.filter(l-> l.getAdresse().getVille().equalsIgnoreCase(recherche.getVille()))
					.collect(Collectors.toList());
			listHousing = listFilter;
		}
		
		List<Reservation> reservationList = (List<Reservation>) reservationRepository.findAll();
		List<Logement> logList = new ArrayList<Logement>();
		if(recherche.getDateDebut()!= "" && recherche.getDateFin()!="")
		{
			for(Logement log : listHousing)
			{
				//List des réservations pour le logement
				List<Reservation> bookingList = reservationList.stream()
						.filter(r -> r.getLogement().getId() == log.getId())
						.collect(Collectors.toList());
				
				//List des réservations possible à la date donnée pour le logement
				List<Reservation> listBooking = bookingList.stream()
						.filter(r -> (CompareDateBefore(recherche.getDateDebut(), r.getDateDebut()) == true && CompareDateBefore(recherche.getDateFin(), r.getDateDebut()) == true)
						    		|| (CompareDateAfter(recherche.getDateDebut(), r.getDateFin()) == true && CompareDateAfter(recherche.getDateFin(), r.getDateFin()) == true))
						.collect(Collectors.toList());
				
				//Ajout du logement dans la liste si réseravation possible
				if (listBooking.size()>0)
				{
					logList.add(log);
				}	
			}
			listHousing = logList;
		}
		boolean userConnected;
		Utilisateur user = (Utilisateur)session.getAttribute("user");

		if(user != null)
			userConnected = true;
		else
			userConnected = false;
		model.addAttribute("userConnected", userConnected);
		
		model.addAttribute("housingList",listHousing);
		//Add flash attributes
		return "/listhousing";
	}
	
	public boolean CompareDateBefore(String Date1, String Date2)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try{
			if(dateFormat.parse(Date1).before(dateFormat.parse(Date2)))
				return true;
			else
				return false;
		}
		catch (Exception e) {
			return false;
		}
	}
	public boolean CompareDateAfter(String Date1, String Date2)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try{
			if(dateFormat.parse(Date1).after(dateFormat.parse(Date2)))
				return true;
			else
				return false;
		}
		catch (Exception e) {
			return false;
		}
	}
	
}
