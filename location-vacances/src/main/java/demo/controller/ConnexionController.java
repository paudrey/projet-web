package demo.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	private static Login previousLogin;
	private int iteration;
	
	
	@RequestMapping("/connexion")
	public String requestInscription(Model model)
	{
		model.addAttribute("login", new Login());		
		return "connexion";
	}
	
	@RequestMapping(value="/connexion", method=RequestMethod.POST)
	public String requestConnexion(Login login, RedirectAttributes redirectAttribute, HttpServletResponse response)
	{
		if(previousLogin == null || previousLogin.getId() != login.getId()) {
			iteration = 0;
		}
		List<Login> loginList = (List<Login>)loginRespository.findAll();
		Login result = loginList.stream()
				.filter(l -> l.getLogin().equals(login.getLogin()) && l.getPassword().equals(login.getPassword()))
				.findFirst()
				.orElse(null);
		
		if(result != null) {
			Cookie cookie = new Cookie("login", String.valueOf(result.getId())); // création du cookie
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
			return "redirect:/adminGeneral";
		}
		else 
		{
			previousLogin = login;
			iteration ++;
			if(iteration == 5)
			{
				//Passage du statut de l'utilisateur en bloqué
			}
			return "connexion";
		}
		
	}
}
