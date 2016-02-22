package demo.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.Format_pays;
import demo.model.Format_typeLogement;
import demo.model.Logement;
import demo.model.Login;
import demo.model.Recherche;
import demo.repository.LogementRepository;
import demo.repository.PaysRepository;
import demo.repository.RechercheRepository;
import demo.repository.TypeLogementRepository;

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
	
	List<Format_pays> countryList = new ArrayList<Format_pays>();
	List<Format_typeLogement> typeLogementList = new ArrayList<Format_typeLogement>();
	
	@RequestMapping("/home")
	public String requestHome(Model model)
	{	    
		countryList.addAll((List<Format_pays>)paysRepository.findAll());
		typeLogementList.addAll((List<Format_typeLogement>)typeLogRepository.findAll());
		
		model.addAttribute("search", new Recherche());
		model.addAttribute("countryList", countryList);
		model.addAttribute("typeLogList", typeLogRepository.findAll());
		return "home";
	}
	
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String requestSearch(Recherche recherche, RedirectAttributes redirectAttribute, Model model)
	{	    
		Format_typeLogement typLog;
		List<Logement> listLog = (List<Logement>) logementRepository.findAll();
		List <Logement> list = listLog.stream()
				.filter(l-> l.getAdresse().getVille().equalsIgnoreCase(recherche.getVille()))
				.filter(l-> l.getTypeLogement().equalsIgnoreCase(recherche.getTypeLogement()))
				.collect(Collectors.toList());
				
		
		model.addAttribute("housingList",list);
		//Add flash attributes
		return "/listhousing";
	}
	
	
}
