package demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.AdressePostale;
import demo.model.Format_pays;
import demo.model.Login;
import demo.model.Utilisateur;
import demo.repository.AdressePostaleRepository;
import demo.repository.LoginRepository;
import demo.repository.PaysRepository;
import demo.repository.UtilisateurRepository;

@Controller
public class UserController {

	@Autowired
	private UtilisateurRepository userRepository;
	@Autowired
	private LoginRepository loginRespository;
	@Autowired
	private AdressePostaleRepository adresseRepository;
	@Autowired
	private PaysRepository paysRepository;
	
	private Utilisateur user;
	List<Format_pays> countryList = new ArrayList<Format_pays>();
	
	/* Specific user */
	
	@RequestMapping("/inscription")
	public String requestInscription(Model model)
	{
		countryList.clear();
		countryList.addAll((List<Format_pays>)paysRepository.findAll());
		model.addAttribute("login", new Login());
		model.addAttribute("countryList", countryList);
		
		return "inscription";
	}
	
	@RequestMapping(value="/inscription", method=RequestMethod.POST)
	public String requestSaveCreate(Login login, RedirectAttributes redirectAttribute)
	{
		AdressePostale adresse = login.getUser().getAdresse();		
		adresseRepository.save(adresse);
		userRepository.save(login.getUser());
		
		String[] tabLogin = login.getUser().getEmail().split("@");
		login.setLogin(tabLogin[0]);
		login.setId(login.getUser().getId());
		loginRespository.save(login);
		
		return "redirect:connexion";
	}
	
	@RequestMapping("/editMyData")
	public String requestEditMyData(@CookieValue(value="login") String idLogin, Model model)
	{
		countryList.clear();
		countryList.addAll((List<Format_pays>)paysRepository.findAll());
		int id = Integer.valueOf(idLogin);
		user = userRepository.findOne(id);
		
		model.addAttribute("user", user);
		model.addAttribute("countryList", countryList);
		return "editMyData";
	}
	
	@RequestMapping(value="/editMyData", method=RequestMethod.POST)
	public String requestEditMyData(Utilisateur utilisateur, RedirectAttributes redirectAttribute)
	{
		AdressePostale adresse = utilisateur.getAdresse();		
		
		user.setNom(utilisateur.getNom());
		user.setPrenom(utilisateur.getPrenom());
		user.setDateNaissance(utilisateur.getDateNaissance());
		user.setAdresse(utilisateur.getAdresse());
		user.setPaysId(utilisateur.getPaysId());
		user.setEmail(utilisateur.getEmail());	
		
		adresseRepository.save(user.getAdresse());
		userRepository.save(user);
		
		return "redirect:adminGeneral";
	}
	
	@RequestMapping("/editPassword")
	public String requestChangePassword(Model model)
	{
		model.addAttribute("login", new Login());
		return "editPassword";
	}
	
	@RequestMapping(value="/editPassword", method=RequestMethod.POST)
	public String requestChangePassword(@CookieValue(value="login") String idLogin, Login login, RedirectAttributes redirectAttribute)
	{
		int id = Integer.valueOf(idLogin);
		Login userLogin = loginRespository.findOne(id);
		if(userLogin.getPassword().equals(login.getPassword()))
		{
			return "redirect:editPassword";
		}
		else
		{
			userLogin.setPassword(login.getPassword());
			loginRespository.save(userLogin);
			return "redirect:adminGeneral";
		}		
	}
	
	
	
	/* Specific administration */
	
	@RequestMapping("/suscribersViewData")
	public String requestView(Model model)
	{
		int id = (int)model.asMap().get("userId");
		Utilisateur user = userRepository.findOne(id);
		user.setFormatPays(paysRepository.findOne(user.getPaysId()));
		model.addAttribute("user", user);		
		return "suscribersViewData";
	}
	
	@RequestMapping(value="/suscribersViewData", method=RequestMethod.POST)
	public String requestView(Model model, RedirectAttributes redirectAttribute)
	{		
		return "redirect:adminUsers";
	}
	
	/*
	
	
	private Utilisateur currentUser;
	
	
	@RequestMapping(value="/suscribersMyData", method=RequestMethod.GET)
	public String requestEdit(Model model) 
	{
		int id = (int)model.asMap().get("id");
		currentUser = userRepository.findOne(id);
		model.addAttribute("bookmark", currentUser);

		return "suscribersMyData";
	}
	
	
	
	
	
	@RequestMapping(value="/suscribeMyData", method=RequestMethod.POST)
	public String requestSaveEdit(AdressePostale adresse, Utilisateur user)
	{
		AdressePostale currentAdress = currentUser.getAdresse();
		currentAdress.setAdresse(adresse.getAdresse());
		currentAdress.setCodePostal(adresse.getCodePostal());
		currentAdress.setVille(adresse.getVille());
		currentAdress.setPays(adresse.getPays());
		adresseRepository.save(currentAdress);
		
		currentUser.setNom(user.getNom());
		currentUser.setPrenom(user.getPrenom());
		currentUser.setDateNaissance(user.getDateNaissance());
		currentUser.setEmail(user.getEmail());
		userRepository.save(currentUser);	
		
		return "redirect:home";
	}
	
	@RequestMapping(value="/suscribeMyData", method=RequestMethod.POST)
	public String requestSaveEditPassword(Login login)
	{
		Login currentLogin = currentUser.getLogin();
		currentLogin.setPassword(login.getPassword());
		
		loginRespository.save(currentLogin);
		
		return "redirect:home";
	}*/
}
