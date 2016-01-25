package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.repository.LoginRepository;

@Controller
public class ConnexionController {
	@Autowired
	private LoginRepository loginRespository;
	
	@RequestMapping("/connexion")
	public String requestConnexion(@PathVariable("id") Integer userId, RedirectAttributes redirectAttribute)
	{
		redirectAttribute.addFlashAttribute("id", userId);
		return "redirect:/suscribersHome";
	}
}
