package eeit163.controller;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import eeit163.model.Member;
import eeit163.service.MemberService;


@Controller
public class MemberController {
	@Autowired
	private MemberService mService;
	
	@PostMapping("/member/signup")
	public String addMsgPost(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("level") String level,
//			@RequestParam("creationdate") Date creationdate,
			@RequestParam("email") String email,
			@RequestParam("photo") MultipartFile photo,
			Model model) {
try {
		Member member = new Member();
		member.setUsername(username);
		member.setPassword(password);
		member.setLevel(level);
		member.setEmail(email);
//		member.setCreationdate(creationdate);
		member.setPhoto(photo.getBytes());

		mService.insertMessage(member);
		model.addAttribute("ok", "上傳成功");
		return "member/signUp";
	} catch (IOException e) {
		e.printStackTrace();
		model.addAttribute("errorMsg", "上傳失敗");
		return "member/signUp";
	}
	}
}
