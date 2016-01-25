package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Model.Contact;
import Repository.Repository_Contact;


public class Controller_Contact {
	
	@Autowired
	private Repository_Contact contactRepository;
	
	@RequestMapping(value="/contacts", method=RequestMethod.POST)
	public String requestSaveCreate(Contact contact, RedirectAttributes redirectAttribute)
	{
		contactRepository.save(contact);
		return "redirect:home";
	}

}
