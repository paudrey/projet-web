package demo.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String requestSaveCreate(Contact contact, RedirectAttributes redirectAttribute)
	{
		contactRepository.save(contact);
		EnvoieMail(contact);
		return "redirect:home";
	}
	
	public void EnvoieMail(Contact contact)
	{
	     Properties properties = System.getProperties();

	      // Setup mail server
	    //  properties.setProperty("mail.smtp.host", "localhost");
	     properties.put("mail.smtp.host", "smtp.gmail.com");
	     properties.put("mail.smtp.user", "holidayme.project@gmail.com");
	     properties.put("mail.smtp.password", "HolidayMe2015");
	   //  properties.put("mail.smtp.port", "25"); 
	   //  properties.put("mail.smtp.ssl.enable", "true"); 
	     //properties.put("mail.smtp.auth", "true");

	      // Get the default Session object.
	      //Session session = Session.getDefaultInstance(properties);
	     Session session = Session.getInstance(properties, new SocialAuth());  

	      try{
	    	  
	         Message message = new MimeMessage(session);
	         message.setFrom(new InternetAddress("holidayme.project@gmail.com"));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress("holidayme.project@gmail.com"));
	         message.setSubject(contact.getObjet() + " - contact : " + contact.getEmail());
	         message.setText(contact.getMessage());
	         message.setSentDate(new Date());
	         
	         //Transport.send(message);
	        Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "holidayme.project@gmail.com", "HolidayMe2015");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
	         System.out.println("------------ Message envoyé -----------------");
    	  } catch (MessagingException e) {
    	  e.printStackTrace();
    	  System.out.println("-------- Erreur ---------");
    	  }
	}
	
	class SocialAuth extends Authenticator {  
        @Override  
        protected PasswordAuthentication getPasswordAuthentication() { 
            return new PasswordAuthentication("holidayme.project@gmail.com","HolidayMe2015" );    
        }  
    }  
}
