package eeit163.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/member/signUpPage")
	public String signup() {
		return "member/signUp";
	}

}