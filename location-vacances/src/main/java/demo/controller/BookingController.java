package demo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.mail.smtp.SMTPTransport;

import demo.model.AdressePostale;
import demo.model.Contact;
import demo.model.Logement;
import demo.model.Reservation;
import demo.model.Utilisateur;
import demo.repository.LogementRepository;
import demo.repository.ReservationRepository;
import demo.repository.UtilisateurRepository;

@Controller
public class BookingController {

	@Autowired
	private LogementRepository logementRepository;
	
	@Autowired
	private ReservationRepository bookingRespository;
	
	@Autowired
	private UtilisateurRepository userRepository;
	
	Logement currentLog;
	Reservation currentRes;
	
	@RequestMapping(value="/createEditBooking")
	public String requestBooking(Model model)
	{
		int id = (int)model.asMap().get("LogId");
		String action = (String)model.asMap().get("action");
		if (action == "mod")
		{
			currentRes = bookingRespository.findOne(id);
			model.addAttribute("booking", currentRes);
		}
		else
			model.addAttribute("booking", new Reservation());
		currentLog = logementRepository.findOne(id);
		
		return "createEditBooking";
	}
	
	@RequestMapping(value="/createEditBooking", method=RequestMethod.POST)
	public String requestCreate(Reservation reservation,@CookieValue(value="login") String idLogin, RedirectAttributes redirectAttribute) throws ScriptException, IOException, NoSuchMethodException
	{
		if (currentRes == null)
		{
			int userId = Integer.valueOf(idLogin);
			Utilisateur locataire = userRepository.findOne(userId);
			//reservation.setProrietaire(currentLog.getProprietaire());
			reservation.setLocataire(locataire);
			reservation.setLogement(currentLog);
			reservation.setStatut("En attente validation");
			bookingRespository.save(reservation);
			EnvoieMailLocataire(reservation,"creation");
			EnvoieMailProprietaire(reservation,"creation");
			
		}
		else
		{
			
			currentRes.setDateDebut(reservation.getDateDebut());
			currentRes.setDateFin(reservation.getDateFin());
			currentRes.setStatut("Modification : en attente validation");
			bookingRespository.save(currentRes);
			EnvoieMailLocataire(currentRes,"modification");
			EnvoieMailProprietaire(currentRes,"modification");
		}
		return "redirect:adminBooking";
	}
	
	public boolean EnvoieMailLocataire(Reservation booking,String typeMail) 
	{
		String objet;
		String message;
		
		String log = logementRepository.findOne(booking.getLogement().getId()).getShortDescription();
		String lienVal;
		String emailLocataire = booking.getLocataire().getEmail();
		
		switch(typeMail)
		{
			case "creation":
				objet = "Validation de la réservation";
				lienVal = "localhost:8080/validationBooking/loc/" + booking.getId();
				message = "Vous avez réalisé une réservation pour le logement : " + log + "\n\n Cliquez sur ce lien pour valider votre demande : \n" + lienVal;
			break;
			case "modification":
				objet = "Modification de la demande";
				lienVal = "localhost:8080/validationModifBooking/loc/" + booking.getId();
				message = "Vous avez modifié la réservation pour le logement : " + log + "\n\n Cliquez sur ce lien pour valider votre demande : \n" + lienVal;
			break;
			default:
				objet = "";
				lienVal = "";
				message = "";
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
	        InternetAddress.parse(emailLocataire, false));
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
	
	public boolean EnvoieMailProprietaire(Reservation booking, String typeMail) 
	{
		String objet;
		String message;
		
		String log = logementRepository.findOne(booking.getLogement().getId()).getShortDescription();
		String lienVal;
		String emailLocataire = booking.getLocataire().getEmail();
		
		switch(typeMail)
		{
			case "creation":
				objet = "Validation de la réservation";
				lienVal = "localhost:8080/validationBooking/prop/" + booking.getId();
				message = "Une réservation a été faite pour le logement : " + log + "\n\n Cliquez sur ce lien pour valider votre demande : \n" + lienVal;
			break;
			case "modification":
				objet = "Modification de la demande";
				lienVal = "localhost:8080/validationModifBooking/prop/" + booking.getId();
				message = "Une modification a été faite pour la réservation pour le logement : " + log + "\n\n Cliquez sur ce lien pour valider votre demande : \n" + lienVal;
			break;
			default:
				objet = "";
				lienVal = "";
				message = "";
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
	        InternetAddress.parse(emailLocataire, false));
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
