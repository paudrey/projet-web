package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Repository.Repository_Login;

public class Controller_Connexion {
	
	@Autowired
	private Repository_Login loginRespository;
	
	@RequestMapping("/connexion")
	public String requestConnexion(@PathVariable("id") Integer userId, RedirectAttributes redirectAttribute)
	{
		redirectAttribute.addFlashAttribute("id", userId);
		return "redirect:/suscribersHome";
	}
	
	

}
