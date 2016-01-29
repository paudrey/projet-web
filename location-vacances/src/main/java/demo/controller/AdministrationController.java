package demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.Logement;
import demo.model.Utilisateur;
import demo.repository.LogementRepository;
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
	public String requestDeleteHousing(@PathVariable("id") Integer LogId,@CookieValue(value="login") String idLogin)
	{
		int userId = Integer.valueOf(idLogin);
		Utilisateur user = utilisateurRepository.findOne(userId);
		Logement log = logementRepository.findOne(LogId);
		List<Logement> logList = user.getLogementList();
		logList.remove(log);
		logementRepository.delete(LogId);
		return "redirect:/adminHousing";
	}
	
	@RequestMapping("/adminBooking")
	public String requestAdminBooking(Model model)
	{
		model.addAttribute("bookingList", bookingRepository.findAll());
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
	public String requestDeleteBooking(@PathVariable("id") Integer LogId,@CookieValue(value="login") String idLogin)
	{
		/*
		int userId = Integer.valueOf(idLogin);
		Utilisateur user = utilisateurRepository.findOne(userId);
		Logement log = logementRepository.findOne(LogId);
		List<Logement> logList = user.getLogementList();
		logList.remove(log);
		logementRepository.delete(LogId);*/
		return "redirect:/adminBooking";
	}
}
