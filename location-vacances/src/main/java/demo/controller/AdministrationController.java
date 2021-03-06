package demo.controller;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.mail.smtp.SMTPTransport;

import demo.enums.UserRole;
import demo.model.Logement;
import demo.model.Photo;
import demo.model.Reservation;
import demo.model.Utilisateur;
import demo.repository.LogementRepository;
import demo.repository.PhotoRepository;
import demo.repository.ReservationRepository;
import demo.repository.UtilisateurRepository;

@Controller
public class AdministrationController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private LogementRepository logementRepository;
	
	@Autowired
	private ReservationRepository bookingRepository;
	
	@Autowired
	private PhotoRepository photoRepository;
	
	static boolean admin = false;
	
	@RequestMapping("/adminGeneral")
	public String requestAdminGeneral(Model model, HttpSession session)
	{
		Utilisateur user = (Utilisateur)session.getAttribute("user");
		if(user != null)
		{
			if(user.getCurrentUserRole() == UserRole.ADMIN)
				admin = true;
			model.addAttribute("user", user);
			model.addAttribute("admin", admin);
			return "/adminGeneral";
		}
		else
		{
			return "/home";
		}
	}
	
	@RequestMapping("/adminGeneral/deconnect")
	public String requestForDeconnexion(Model model, HttpSession session)
	{
		session.setAttribute("user", null);
		admin = false;
		
		return "redirect:/home";
	}
	
	@RequestMapping("/adminUsers")
	public String requestAdminUser(Model model)
	{
		model.addAttribute("userList", utilisateurRepository.findAll());
		return "/adminUsers";
	}
	
	@RequestMapping("/view/{id}")
	public String requestViewDetails(@PathVariable("id") Integer userId, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("userId", userId);				
		return "redirect:/suscribersViewData";
	}
	
	@RequestMapping("/adminHousing")
	public String requestAdminHousing(Model model,HttpSession session)
	{
		Utilisateur user = (Utilisateur)session.getAttribute("user");
		if(user != null)
		{
			if(user.getCurrentUserRole()== UserRole.ADMIN)
			{
				model.addAttribute("admin", true);
				model.addAttribute("housingList", logementRepository.findAll());
			}				
			else
			{
				List<Logement> listLog = (List<Logement>) logementRepository.findAll();
				List<Logement> list = listLog.stream()
						.filter(l -> l.getProprietaire().getId() == user.getId())
						.collect(Collectors.toList());
				model.addAttribute("housingList", list);
			}		
		}	
		return "/adminHousing";
	}
	
	@RequestMapping("/viewHousing/{id}")
	public String requestViewHousingDetails(@PathVariable("id") Integer LogId, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("LogId", LogId);				
		return "redirect:/createEditHousing";
	}
	
	@RequestMapping("/deleteHousing/{id}")
	public String requestDeleteHousing(@PathVariable("id") Integer LogId,HttpSession session)
	{
		Utilisateur user = (Utilisateur)session.getAttribute("user");
		if(user != null)
		{
			Logement log = logementRepository.findOne(LogId);
			
			List<Logement> logList = user.getLogementList();
			logList.remove(log);
			logementRepository.delete(LogId);
			return "redirect:/adminHousing";
		}
		return "redirect:/home";
	}
	
	@RequestMapping("/adminBooking")
	public String requestAdminBooking(Model model, HttpSession session)
	{
		Utilisateur user = (Utilisateur)session.getAttribute("user");
		if(user != null)
		{
			if(user.getCurrentUserRole()== UserRole.ADMIN)
			{
				model.addAttribute("admin", true);
				model.addAttribute("bookingList", bookingRepository.findAll());
			}
				
			else
			{
				List<Reservation> listRes = (List<Reservation>) bookingRepository.findAll();
				List<Reservation> list = listRes.stream()
						.filter(r -> r.getLocataire().getId() == user.getId() || r.getLogement().getProprietaire().getId()==user.getId())
						.collect(Collectors.toList());
				model.addAttribute("bookingList", list);
			}
		}
		return "/adminBooking";
	}
	
	@RequestMapping("/viewBooking/{action}/{id}")
	public String requestViewBookingDetails(@PathVariable("id") Integer LogId, @PathVariable("id") String action, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("LogId", LogId);	
		redirectAttributes.addFlashAttribute("action", "mod");
		return "redirect:/createEditBooking";
	}
	
	@RequestMapping("/deleteBooking/{id}")
	public String requestDeleteBooking(@PathVariable("id") Integer LogId,HttpSession session)
	{
		Utilisateur user = (Utilisateur)session.getAttribute("user");
		if(user != null)
		{
			Reservation booking = bookingRepository.findOne(LogId);
			booking.setStatut("En attente d'annulation");
			bookingRepository.save(booking);
			EnvoieMail(booking,"loc");
			EnvoieMail(booking,"prop");
			return "redirect:/adminBooking";
		}
		return "redirect:/home";
	}
	
	public boolean EnvoieMail(Reservation booking,String typeMail) 
	{
		String objet;
		String message;
		
		String log = logementRepository.findOne(booking.getLogement().getId()).getShortDescription();
		String lienVal;
		String email;
		
		switch(typeMail)
		{
			case "loc":
				objet = "Annulation de la réservation";
				lienVal = "localhost:8080/validationDeleteBooking/loc/" + booking.getId();
				message = "Vous avez réalisé une annulation de la réservation pour le logement : " + log + "\n\n Recopier ce lien dans votre navigateur pour valider votre demande : \n" + lienVal;
				email = booking.getLocataire().getEmail();
			break;
			case "prop":
				objet = "Annulation de la réservation";
				lienVal = "localhost:8080/validationDeleteBooking/prop/" + booking.getId();
				message = "Une annulation de la réservation a été faite pour le logement : " + log + "\n\n Recopier ce lien dans votre navigateur pour valider votre demande : \n" + lienVal;
				email = booking.getLogement().getProprietaire().getEmail();
			break;
			default:
				objet = "";
				lienVal = "";
				message = "";
				email = "";
		}
				
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
}
