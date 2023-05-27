package eeit163.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import eeit163.model.Member;
import eeit163.service.MemberService;
import jakarta.servlet.http.HttpSession;


@Controller
public class MemberController {
	@Autowired
	private MemberService mService;
	
	@PostMapping("/member/signup")
	public String signup(@RequestParam("username") String username,
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
	
	@PostMapping("/member/login")
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			Model model,HttpSession hs) {
		String str=mService.checkLogin(username, password);
		if (str.equals("查無此用戶")) {
			model.addAttribute("msg", str);
			return "member/login";
		}else if(str.equals("密碼錯誤")) {
			model.addAttribute("msg", str);
			return "member/login";
		}else {
			model.addAttribute("msg", str);
			hs.setAttribute("loggedInMember", mService.findByUsername(username));
			return "index";
		}
	}
	
	@ResponseBody
	@PostMapping("/member/ajax/checkUsername")
	public String checkUsername(@RequestParam("username") String username) {
		return mService.checkUsername(username);
	}
	
	
}
