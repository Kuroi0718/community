package eeit163.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String signup(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("level") String level,
//			@RequestParam("creationdate") Date creationdate,
			@RequestParam("email") String email, @RequestParam("photo") MultipartFile photo, Model model) {
		try {
			Member member = new Member();
			member.setUsername(username);
			member.setPassword(password);
			member.setLevel(level);
			member.setEmail(email);
//		member.setCreationdate(creationdate);
			member.setPhoto(photo.getBytes());

			mService.insertMember(member);
			model.addAttribute("ok", "註冊成功");
			return "member/signUp";
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "註冊失敗");
			return "member/signUp";
		}
	}

	@ResponseBody
	@PostMapping("/member/update")
	public String update(@RequestParam("id") Integer id, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("level") String level,
//			@RequestParam("creationdate") Date creationdate,
			@RequestParam("email") String email, @RequestParam("photo") MultipartFile photo) {
		try {
			Member member = mService.findById(id);
			if (username != null && username.length() != 0) {
				member.setUsername(username);
			}
			if (password != null && password.length() != 0) {
				member.setPassword(password);
			}
			if (level != null && level.length() != 0) {
				member.setLevel(level);
			}
			if (email != null && email.length() != 0) {
				member.setEmail(email);
			}
//		member.setCreationdate(creationdate);
			if (photo.getSize() != 0L) {
				member.setPhoto(photo.getBytes());
			}
			mService.updateById(member);
			return "修改成功";
		} catch (IOException e) {
			e.printStackTrace();
			return "修改失敗";
		}
	}

	@ResponseBody
	@PostMapping("/member/refreshLogin")
	public String refreshLogin(@RequestParam("id") Integer id, HttpSession hs) {
		try {
			Member member = mService.findById(id);
			hs.setAttribute("loggedInMember", member);
			return member.getUsername();
		} catch (NoSuchElementException ne) {
			ne.printStackTrace();
			return "查無此用戶";
		}
	}

	@GetMapping("/member/logout")
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "index";
	}

	@PostMapping("/member/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model, HttpSession hs) {
		String str = mService.checkLogin(username, password);
		if (str.equals("查無此用戶")) {
			model.addAttribute("msg", str);
			return "member/login";
		} else if (str.equals("密碼錯誤")) {
			model.addAttribute("msg", str);
			return "member/login";
		} else {
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

	@GetMapping("/member/image")
	public ResponseEntity<byte[]> getImage(@RequestParam("id") Integer id) {
		byte[] photo = mService.findPhotoById(id);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.ALL);
		// 回傳值 , header, status
		return new ResponseEntity<byte[]>(photo, header, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/member/ajax/validEmail")
	public String isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		System.out.println(m.matches());
		return m.matches() ? "信箱格式正確 <button type=\"button\" onclick=\"sendEmail()\">驗證</button>" : "信箱格式錯誤";
	}

	@GetMapping("/member/page")
	public String findByPage(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber, Model model) {
		Page<Member> page = mService.findByPage(pageNumber);
		model.addAttribute("page", page.getContent());
		System.out.println(page.getSize());
		return "member/showMembers";
	}

	@ResponseBody
	@PostMapping("/member/ajax/page")
	public Page<Member> addMsgApi(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {

		Page<Member> page = mService.findByPage(pageNumber);

		return page;
	}

	@ResponseBody
	@PostMapping("/member/ajax/changeLevel")
	public String changeLevel(@RequestParam("id") Integer id, @RequestParam("level") String level) {
		try {
			Member m = mService.findById(id);
			m.setLevel(level);
			mService.updateById(m);
			return "yes";
		} catch (Exception e) {
			return "no";
		}

	}

}
