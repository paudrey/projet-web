package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Model.AdressePostale;
import Model.Login;
import Model.Utilisateur;
import Repository.Repository_AdressePostale;
import Repository.Repository_Login;
import Repository.Repository_Utilisateur;

public class Controller_Users {

	@Autowired
	private Repository_Utilisateur usersRepository;
	@Autowired
	private Repository_Login loginRespository;
	@Autowired
	private Repository_AdressePostale adresseRepository;
	
	
	private Utilisateur currentUser;
	
	
	@RequestMapping(value="/suscribersMyData", method=RequestMethod.GET)
	public String requestEdit(Model model) 
	{
		int id = (int)model.asMap().get("id");
		currentUser = usersRepository.findOne(id);
		model.addAttribute("bookmark", currentUser);

		return "suscribersMyData";
	}
	
	@RequestMapping(value="/inscription", method=RequestMethod.POST)
	public String requestSaveCreate(Login login,AdressePostale adresse,Utilisateur user, RedirectAttributes redirectAttribute)
	{
		loginRespository.save(login);
		adresseRepository.save(adresse);
		usersRepository.save(user);	
		
		return "redirect:home";
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
		usersRepository.save(currentUser);	
		
		return "redirect:home";
	}
	
	@RequestMapping(value="/suscribeMyData", method=RequestMethod.POST)
	public String requestSaveEditPassword(Login login)
	{
		Login currentLogin = currentUser.getLogin();
		currentLogin.setPassword(login.getPassword());
		
		loginRespository.save(currentLogin);
		
		return "redirect:home";
	}
	
	
	
	
	
}
