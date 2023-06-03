package eeit163.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.service.MailService;

@Controller
public class MailController {
	@Autowired
	private MailService mailService;

	@PostMapping("/mail/valid")
	@ResponseBody
	public String valid(@RequestParam("email") String email) {
		try {
			mailService.prepareAndSend(email, "123456");
			return "信件已發送";
		} catch (Exception e) {
			return "無法送出信件";
		}
	}

}