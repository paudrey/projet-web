package demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.repository.TypeLogementRepository;

@Controller
public class HomeController {

	@Autowired
	TypeLogementRepository typeLogRepository;
	
	@RequestMapping("/home")
	public String requestHome(Model model)
	{	    
		model.addAttribute("typeLogList", typeLogRepository.findAll());
		return "home";
	}
}
