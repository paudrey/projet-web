package demo.controller;

import java.util.Arrays;
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
import org.apache.commons.codec.digest.DigestUtils;

import demo.enums.UserStatus;
import demo.model.Login;
import demo.model.Utilisateur;
import demo.repository.LoginRepository;
import demo.repository.UtilisateurRepository;

@Controller
public class ConnexionController {
	@Autowired
	private LoginRepository loginRespository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	private static Login previousLogin;
	private int iteration;
	
	
	@RequestMapping("/connexion")
	public String requestInscription(Model model)
	{
		model.addAttribute("login", new Login());		
		return "connexion";
	}
	
	@RequestMapping(value="/connexion", method=RequestMethod.POST)
	public String requestConnexion(Login login, RedirectAttributes redirectAttribute, HttpServletRequest request, HttpServletResponse response)
	{
		String pwdHashed = DigestUtils.sha512Hex(login.getPassword());
		if(previousLogin == null || previousLogin.getId() != login.getId()) {
			iteration = 0;
		}

		List<Login> loginList = (List<Login>)loginRespository.findAll();
		Login loginToTest = loginList.stream()
				.filter(l -> l.getLogin().equals(login.getLogin()))
				.findFirst()
				.orElse(null);
		
		if(loginToTest != null)
		{
			Utilisateur userToTest = loginToTest.getUser();
			
			if(loginToTest.getPassword().equals(pwdHashed) && userToTest.getCurrentUserStatus() != UserStatus.BLOCKED)
			{
				Cookie cookie;
				try{
					cookie = Arrays.asList(request.getCookies())
							.stream()
							.filter(c -> c.getName().equals("login"))
							.findFirst()
							.orElse(null);
				}
				catch(Exception exception){
					cookie = null;
				}
				
				if(cookie == null)
				{
					cookie = new Cookie("login", String.valueOf(loginToTest.getId())); // création du cookie
				}
				else
				{
					cookie.setValue(String.valueOf(loginToTest.getId()));					
				}
				cookie.setMaxAge(3600);
				response.addCookie(cookie);
				return "redirect:/adminGeneral";
			}
			else{
				iteration ++;
				previousLogin = login;
				if(iteration == 5)
				{
					userToTest.setCurrentUserStatus(UserStatus.BLOCKED);
					utilisateurRepository.save(userToTest);
				}
				return "connexion";
			}	
		}
		else
		{
			return "connexion";
		}
	}
}
