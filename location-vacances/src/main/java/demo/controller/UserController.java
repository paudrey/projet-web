package demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.mail.smtp.SMTPTransport;

import demo.enums.UserRole;
import demo.enums.UserStatus;
import demo.model.AdressePostale;
import demo.model.Format_pays;
import demo.model.Login;
import demo.model.Reservation;
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
	List<UserStatus> userStatusList = new ArrayList<UserStatus>();
	List<UserRole> userRoleList = new ArrayList<UserRole>();
	
	private Login currentLogin;
	
	boolean adminUser;
	

	
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
		
		login.getUser().setCurrentUserStatus(UserStatus.CONFIRMED);
		login.getUser().setCurrentUserRole(UserRole.USER);
		userRepository.save(login.getUser());	
		
		String[] tabLogin = login.getUser().getEmail().split("@");
		login.setLogin(tabLogin[0]);
		login.setId(login.getUser().getId());
		login.setPassword(DigestUtils.sha512Hex(login.getPassword()));
		loginRespository.save(login);
		
		return "redirect:connexion";
	}
	
	@RequestMapping("/editMyData")
	public String requestEditMyData(Model model, HttpSession session)
	{
		countryList.clear();
		countryList.addAll((List<Format_pays>)paysRepository.findAll());
		user = (Utilisateur)session.getAttribute("user");
		
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
	public String requestChangePassword(@CookieValue(value="login") String idLogin, HttpServletRequest request, Login login, RedirectAttributes redirectAttribute)
	{
		String oldPassword = DigestUtils.sha512Hex(request.getParameter("OldPassword"));
		int id = Integer.valueOf(idLogin);
		Login userLogin = loginRespository.findOne(id);
		if(userLogin.getPassword().equals(oldPassword))
		{
			if(userLogin.getPassword().equals(login.getPassword()))
			{
				return "redirect:editPassword";
			}
			else
			{			
				userLogin.setPassword(DigestUtils.sha512Hex(login.getPassword()));
				loginRespository.save(userLogin);
				return "redirect:adminGeneral";
			}	
		}
		else 
			return "redirect:editPassword";
	}
	
	 @ModelAttribute("OldPassword")
    public String getOldPassword(HttpServletRequest request) 
    {
        return (String) request.getAttribute("OldPassword");
    }
	
	/* Specific administration */
	
	@RequestMapping("/suscribersViewData")
	public String requestView(Model model, HttpSession session, RedirectAttributes redirectAttributes)
	{
		userStatusList.clear();
		userStatusList.add(UserStatus.CONFIRMED);
		userStatusList.add(UserStatus.BLOCKED);
		
		userRoleList.clear();
		userRoleList.add(UserRole.ADMIN);
		userRoleList.add(UserRole.USER);
		
		int id = (int)model.asMap().get("userId");
		Utilisateur user = userRepository.findOne(id);
		user.setFormatPays(paysRepository.findOne(user.getPaysId()));
		
		Utilisateur currentUser = (Utilisateur)session.getAttribute("user");	
		if(currentUser != null && currentUser.getCurrentUserRole() == UserRole.ADMIN){
			adminUser = true;
			session.setAttribute("userToEditId", id);
		}
		else{
			adminUser = false;
		}
		
		model.addAttribute("adminUser", adminUser);
		model.addAttribute("userStatusList", userStatusList);
		model.addAttribute("userRoleList", userRoleList);
		model.addAttribute("user", user);		
		return "suscribersViewData";
	}
	
	@RequestMapping(value="/suscribersViewData", method=RequestMethod.POST)
	public String requestView(Model model, Utilisateur user, HttpSession session)
	{		
		
		Utilisateur currentUser = (Utilisateur)session.getAttribute("user");
		if(currentUser != null && currentUser.getCurrentUserRole() == UserRole.ADMIN)
		{
			Utilisateur userToEdit = userRepository.findOne((int)session.getAttribute("userToEditId"));
			userToEdit.setCurrentUserStatus(user.getCurrentUserStatus());
			userToEdit.setCurrentUserRole(user.getCurrentUserRole());
			userRepository.save(userToEdit);
			session.setAttribute("userToEditId", null);
		}
		return "redirect:adminUsers";
	}
	
	@RequestMapping("/resetPassword")
	public String requestPassword(Model model)
	{	
		model.addAttribute("login", new Login());
		return "resetPassword";
	}
	
	@RequestMapping(value="/resetPassword", method=RequestMethod.POST)
	public String requestResetPassword(Model model, Login login, RedirectAttributes redirectAttribute)
	{	
		List<Login> loginList = (List<Login>)loginRespository.findAll();
		
		Login result = loginList.stream()
				.filter(l -> l.getLogin().equals(login.getLogin()))
				.findFirst()
				.orElse(null);
		
		if(result != null)
		{
			Utilisateur user = userRepository.findOne(result.getId());
			EnvoieMail(user);		
		}
		
		return "redirect:home";
	}
	
	
	public boolean EnvoieMail(Utilisateur user) 
	{
		String objet;
		String message;
		
		//String log = logementRepository.findOne(booking.getLogement().getId()).getShortDescription();
		String lienVal;
		String email = user.getEmail();
		
		objet = "Mot de passe oublié sur Holiday Me";
		lienVal = "localhost:8080/resetPasswdProcess/" + user.getId();
		message = " Cliquez sur ce lien pour changer votre mot de passe: \n" + lienVal;

		
		try 
		{
			Properties props = System.getProperties();
	        props.put("mail.smtps.host","smtp.gmail.com");
	        props.put("mail.smtps.auth","true");
	        Session session = Session.getInstance(props, null);
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress("holidayme.project@gmail.com"));;
	        msg.setRecipients(Message.RecipientType.TO,
	        InternetAddress.parse(email, false));
	        msg.setSubject(objet);
	        msg.setText(message);
	        msg.setSentDate(new Date());
	        SMTPTransport t =
	            (SMTPTransport)session.getTransport("smtps");
	        t.connect("smtp.gmail.com", "holidayme.project@gmail.com", "HolidayMe2015");
	        t.sendMessage(msg, msg.getAllRecipients());
	        System.out.println("Response: " + t.getLastServerResponse());
	        t.close();       
	        return true;
		}
        catch(AddressException e){ 
        	return false;
		}
		catch(MessagingException e){
        	return false;
		}   
	}
	
	@RequestMapping(value="/resetPasswdProcess/{id}")
	public String requestPassword(Model model,@PathVariable("id") Integer LogId,RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("LogId", LogId);
	
		return "redirect:/resetPasswdProcess";
	}
	
	@RequestMapping(value="/resetPasswdProcess")
	public String requestResetPassword(Model model)
	{
		int id = (int)model.asMap().get("LogId");
		currentLogin = loginRespository.findOne(id);
		model.addAttribute("login", new Login());
		return "resetPasswdProcess";
	}
	/*@RequestMapping("/resetPasswordProcess")
	public String requestResetPassword(Model model)
	{
		//int id = (int)model.asMap().get("LogId");
	//	currentLogin = loginRespository.findOne(id);
		model.addAttribute("login", new Login());
		return "resetPasswordProcess";
	}*/
	
	@RequestMapping(value="/resetPasswdProcess" , method=RequestMethod.POST)
	public String requestModifPassword(Model model, Login login)
	{	
		Utilisateur user = currentLogin.getUser();
		if (user.getCurrentUserStatus() == UserStatus.BLOCKED)
		{
			user.setCurrentUserStatus(UserStatus.CONFIRMED);
			userRepository.save(user);
		}
		currentLogin.setPassword(DigestUtils.sha512Hex(login.getPassword()));
		loginRespository.save(currentLogin);
		return "redirect:/connexion";		
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
