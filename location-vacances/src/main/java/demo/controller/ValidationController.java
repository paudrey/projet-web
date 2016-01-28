package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.Reservation;
import demo.repository.ReservationRepository;

@Controller
public class ValidationController {

	@Autowired
	private ReservationRepository bookingRespository;
	
	private String typeUtilisateur;
	private Reservation currentReservation;
	
	@RequestMapping("/validationBooking/{type}/{id}")
	public String requestMailBooking(Model model,@PathVariable("id") Integer LogId, @PathVariable("type") String type,RedirectAttributes redirectAttributes)
	{
		
		redirectAttributes.addFlashAttribute("type", type);
		redirectAttributes.addFlashAttribute("LogId", LogId);
		
		return "redirect:/validationBooking";
	}
	
	@RequestMapping(value="/validationBooking")
	public String requestMailBoking(Model model)
	{
		int id = (int)model.asMap().get("LogId");
		typeUtilisateur = (String)model.asMap().get("type");
		System.out.println(typeUtilisateur);
		currentReservation = bookingRespository.findOne(id);
		model.addAttribute("booking", currentReservation);
		return "/validationBooking";
	}
	

	@RequestMapping(value="/validationBooking" , method=RequestMethod.POST)
	public String requestModifValidatiion(Model model)
	{
		if(typeUtilisateur.equals("loc"))
		{
			if (currentReservation.getStatut() == "En attente validation")
			{
				currentReservation.setStatut("Validée par le locataire");
			}
			else
				currentReservation.setStatut("Validée");
		}
		else
		{
			if (currentReservation.getStatut() == "En attente validation")
			{
				currentReservation.setStatut("Validée par le propriètaire");
			}
			else
				currentReservation.setStatut("Validée");
		}
		bookingRespository.save(currentReservation);
		return "redirect:adminBooking";
	}
	
	@RequestMapping("/validationModifBooking/{type}/{id}")
	public String requestModifMailBooking(Model model,@PathVariable("id") Integer LogId, @PathVariable("type") String type,RedirectAttributes redirectAttributes)
	{
		
		redirectAttributes.addFlashAttribute("type", type);
		redirectAttributes.addFlashAttribute("LogId", LogId);
		
		return "redirect:/validationModifBooking";
	}
	
	@RequestMapping(value="/validationModifBooking")
	public String requestModifMailBoking(Model model)
	{
		int id = (int)model.asMap().get("LogId");
		typeUtilisateur = (String)model.asMap().get("type");
		currentReservation = bookingRespository.findOne(id);
		model.addAttribute("booking", currentReservation);
		return "/validationModifBooking";
	}
	
	@RequestMapping(value="/validationModifBooking" , method=RequestMethod.POST)
	public String requestValidatiion(Model model)
	{
		if(typeUtilisateur.equals("loc"))
		{
			if (currentReservation.getStatut() == "Modification : en attente validation")
			{
				currentReservation.setStatut("Validée par le locataire");
			}
			else
				currentReservation.setStatut("Validée");
		}
		else
		{
			if (currentReservation.getStatut() == "Modification : en attente validation")
			{
				currentReservation.setStatut("Validée par le propriètaire");
			}
			else
				currentReservation.setStatut("Validée");
		}
		bookingRespository.save(currentReservation);
		return "redirect:adminBooking";
	}
	
}
