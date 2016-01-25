package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import demo.model.Contact;
import demo.repository.ContactRepository;

@Controller
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;
	
	@RequestMapping(value="/contacts", method=RequestMethod.POST)
	public String requestSaveCreate(Contact contact, RedirectAttributes redirectAttribute)
	{
		contactRepository.save(contact);
		return "redirect:home";
	}
}
