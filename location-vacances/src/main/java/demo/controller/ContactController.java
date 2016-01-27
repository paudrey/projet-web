package demo.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.mail.smtp.SMTPTransport;

import demo.model.Contact;
import demo.repository.ContactRepository;

@Controller
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;
	
	@RequestMapping("/contacts")
	public String requestHome(Model model)
	{	    
		model.addAttribute("contact", new Contact());
		return "contacts";
	}
	
	@RequestMapping(value="/contacts", method=RequestMethod.POST)
	public String requestSaveCreate(Contact contact,Model model, RedirectAttributes redirectAttribute) throws AddressException, MessagingException
	{
		contactRepository.save(contact);
		boolean envoi = EnvoieMail(contact);
		if(envoi = true){
			return "redirect:home";
		}
		else
		{
			model.addAttribute("contact", contact);
			return "contacts";
		}
		
	}
	
	public boolean EnvoieMail(Contact contact) 
	{
		try
		{
			Properties props = System.getProperties();
	        props.put("mail.smtps.host","smtp.gmail.com");
	        props.put("mail.smtps.auth","true");
	        Session session = Session.getInstance(props, null);
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress("holidayme.project@gmail.com"));;
	        msg.setRecipients(Message.RecipientType.TO,
	        InternetAddress.parse("holidayme.project@gmail.com", false));
	        msg.setSubject(contact.getObjet() + " contact : " + contact.getEmail());
	        msg.setText("Nom du contact : " + contact.getName()+ "\n" + contact.getMessage());
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
