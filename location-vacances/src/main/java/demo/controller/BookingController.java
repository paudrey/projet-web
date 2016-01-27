package demo.controller;

import java.io.IOException;
import java.util.List;

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

import demo.model.AdressePostale;
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
	
	@RequestMapping(value="/createEditBooking")
	public String requestDetailsHou(Model model)
	{
		int id = (int)model.asMap().get("LogId");
		currentLog = logementRepository.findOne(id);
		model.addAttribute("booking", new Reservation());
		return "createEditBooking";
	}
	
	@RequestMapping(value="/createEditBooking", method=RequestMethod.POST)
	public String requestCreate(Reservation reservation,@CookieValue(value="login") String idLogin, RedirectAttributes redirectAttribute) throws ScriptException, IOException, NoSuchMethodException
	{
		int userId = Integer.valueOf(idLogin);
		Utilisateur user = userRepository.findOne(userId);
		reservation.setLocataire(user);
		reservation.setLogement(currentLog);
		reservation.setStatut("En attente validation");
		return "redirect:adminHousing";
	}
}
