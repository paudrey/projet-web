package demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String requestHome(Model model)
	{
		//model.addAttribute("bookmarkList", bookmarkRepository.findAll());
		return "home";
	}
}
