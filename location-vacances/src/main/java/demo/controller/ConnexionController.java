package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.Login;
import demo.model.Utilisateur;
import demo.repository.LoginRepository;

@Controller
public class ConnexionController {
	@Autowired
	private LoginRepository loginRespository;
	
	
	@RequestMapping("/connexion")
	public String requestInscription(Model model)
	{
		model.addAttribute("user", new Utilisateur());		
		return "connexion";
	}
	
	@RequestMapping(value="/connexion", method=RequestMethod.POST)
	public String requestConnexion(Login login, RedirectAttributes redirectAttribute)
	{
		List<Login> loginList = (List<Login>)loginRespository.findAll();
		Login result = loginList.stream()
				.filter(user -> user.getLogin().equals(login.getLogin()) && user.getPassword().equals(login.getPassword()))
				.findFirst()
				.get();
		
		if(result != null) {
			return "redirect:/suscribersHome";
		}
		else {
			return "connexion";
		}
		
	}
}
