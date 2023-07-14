package eeit163.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Mail;
import eeit163.service.MailService;

@Controller
public class MailController {
	@Autowired
	private MailService mailService;

	@ResponseBody
	@PostMapping("/mail/valid")
	public String valid(@RequestParam("email") String email) {
		try {
			int a = (int)(Math.random()*10);
			int b = (int)(Math.random()*10);
			int c = (int)(Math.random()*10);
			int d = (int)(Math.random()*10);
			int e = (int)(Math.random()*10);
			int f = (int)(Math.random()*10);
			Mail mail = new Mail();
			mail.setEmail(email);
			mail.setFirst(a);
			mail.setSecond(b);
			mail.setThird(c);
			mail.setForth(d);
			mail.setFifth(e);
			mail.setSixth(f);
			mailService.insertMail(mail);
			mailService.prepareAndSend(email, ""+a+b+c+d+e+f);
			return "信件已發送";
		} catch (Exception e) {
			return "無法送出信件";
		}
	}
	
	@ResponseBody
	@PostMapping("/mail/checkNumbers")
	public String checkNumbers(@RequestParam("email") String email,
			@RequestParam("first") Integer a,
			@RequestParam("second") Integer b,
			@RequestParam("third") Integer c,
			@RequestParam("forth") Integer d,
			@RequestParam("fifth") Integer e,
			@RequestParam("sixth") Integer f) {
			Integer checkEmailByNumbers = mailService.checkEmailByNumbers(a, b, c, d, e, f, email);
			if(checkEmailByNumbers!=0) {
				return "正確";
			}else {
				return "錯誤";
			}
	}

}
