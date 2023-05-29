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
	@GetMapping("/member/loginPage")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("/member/profilePage")
	public String profile() {
		return "member/profile";
	}

}