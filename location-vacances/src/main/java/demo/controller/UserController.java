package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.AdressePostale;
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
	
	@RequestMapping("/inscription")
	public String requestInscription(Model model)
	{
		//model.addAttribute("paysList", paysRepository.findAll());
		//model.addAttribute("adresse", new AdressePostale());
		//model.addAttribute("login", new Login());
		model.addAttribute("user", new Utilisateur());
		
		return "inscription";
	}
	
	@RequestMapping(value="/inscription", method=RequestMethod.POST)
	public String requestSaveCreate(Utilisateur user, RedirectAttributes redirectAttribute)
	{
		String[] tabLogin = user.getEmail().split("@");
		
		Login login = user.getLogin();
		login.setId(user.getId());
		login.setLogin(tabLogin[0]);
		AdressePostale adresse = user.getAdresse();
		
		loginRespository.save(login);
		adresseRepository.save(adresse);
		userRepository.save(user);	
		
		return "redirect:adminUsers";
	}
	
	@RequestMapping("/suscribersViewData")
	public String requestView(Model model)
	{
		int id = (int)model.asMap().get("userId");
		Utilisateur user = userRepository.findOne(id);		
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