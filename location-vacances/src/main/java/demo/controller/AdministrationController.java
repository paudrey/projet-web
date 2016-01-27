package demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.Utilisateur;
import demo.repository.LogementRepository;
import demo.repository.UtilisateurRepository;

@Controller
public class AdministrationController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private LogementRepository logementRepository;
	
	@RequestMapping("/adminGeneral")
	public String requestAdminGeneral(@CookieValue(value="login") String idLogin, Model model)
	{
		int userId = Integer.valueOf(idLogin);
		Utilisateur user = utilisateurRepository.findOne(userId);
		model.addAttribute("user", user);
		return "/adminGeneral";
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
	public String requestAdminHousing(Model model)
	{
		model.addAttribute("housingList", logementRepository.findAll());
		return "/adminHousing";
	}
	
	@RequestMapping("/viewHousing/{id}")
	public String requestViewHousingDetails(@PathVariable("id") Integer LogId, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("LogId", LogId);				
		return "redirect:/createEditHousing";
	}
	
	@RequestMapping("/deleteHousing/{id}")
	public String requestDeleteHousing(@PathVariable("id") Integer LogId)
	{
		logementRepository.delete(LogId);		
		return "redirect:/adminHousing";
	}
	
	/*@RequestMapping("/view/{id}")
	public String editProductRequest(@PathVariable("id") Integer userId, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("userId", userId);				
		return "redirect:/suscribersViewData";
	}*/
}
