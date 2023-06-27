package eeit163.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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

	@Value("classpath:static/image/noimg.png")
	private Resource noimg;

	@PostMapping("/member/signup")
	public String signup(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("level") String level, @RequestParam("gender") String gender,
			@RequestParam("birthday") String birthday,
//			@RequestParam("creationdate") Date creationdate,
			@RequestParam("email") String email, @RequestParam("tel") String tel,
			@RequestParam("photo") MultipartFile photo, Model model) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Member member = new Member();
			member.setUsername(username);
			member.setPassword(password);
			member.setLevel(level);
			member.setEmail(email);
			member.setTel(tel);
			member.setGender(gender);
			try {
				member.setBirthday(df.parse(birthday));
			} catch (ParseException e) {
				e.printStackTrace();
			}
//		member.setCreationdate(creationdate);
			if (photo.getSize() != 0L) {
				member.setPhoto(photo.getBytes());
			} else {
				member.setPhoto(Files.readAllBytes(noimg.getFile().toPath()));
			}

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

//	@ResponseBody
//	@PostMapping("/member/ajax/page")
//	public Page<Member> addMsgApi(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {
//
//		Page<Member> page = mService.findByPage(pageNumber);
//
//		return page;
//	}

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

	@ResponseBody
	@PostMapping("/member/ajax/searchMember")
	public String searchMember(@RequestParam("username") String username,HttpSession hs) {
		try {
			Member m1 = mService.findById(((Member) hs.getAttribute("loggedInMember")).getId());
			List<String> invite1 = new ArrayList<String>(Arrays.asList(m1.getInvite().split(",")));
			List<String> invitation1 = new ArrayList<String>(Arrays.asList(m1.getInvitation().split(",")));
			List<String> friend1 = new ArrayList<String>(Arrays.asList(m1.getFriend().split(",")));
			Member m = mService.findByUsername(username);
			String str0="new";
			if(invite1.contains(String.valueOf(m.getId()))) {str0="invite";System.out.println("邀請"+m.getId()+"中");}
			if(invitation1.contains(String.valueOf(m.getId()))) {str0="invitation";System.out.println("受"+m.getId()+"其邀請");}
			if(friend1.contains(String.valueOf(m.getId()))) {str0="friend";System.out.println(m.getId()+"朋友");}
			String str;
			str = "{\"id\":\"" + m.getId() + "\",\"username\":\"" + m.getUsername()+ "\",\"relation\":\"" + str0 + "\",\"photo\":\""
					+ m.generateBase64Image() + "\"}";
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	@ResponseBody
	@PostMapping("/member/ajax/invited")
	public String invited(@RequestParam("id") Integer id) {
		try {
			String[] split = mService.findById(id).getInvite().split(",");
			Integer[] arr = new Integer[split.length];
			for (int i = 1; i < split.length; i++) { // 因為第0個是空字串
				arr[i] = Integer.parseInt(split[i]);
			}
			String str = "</br></br><h2>邀請中</h2></br>";
			List<Member> list = mService.findAllById(Arrays.asList(arr));
			for (Member m : list) {
				str += "<span>" + m.getId() + "</span>\r\n"
						+ "	<span><img style=\"width: 50px\" src=\"data:image/*;base64," + m.generateBase64Image()
						+ "\" /></span>\r\n" + "	<span>" + m.getUsername() + "</span>" + "	<span id=\"invite_"
						+ m.getId() + "\"></span>" + "<button onclick=\"unFriend(" + m.getId() + ")\">取消</button>"
						+ "</br>";
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@ResponseBody
	@PostMapping("/member/ajax/invitation")
	public String invitation(@RequestParam("id") Integer id) {
		try {
			String[] split = mService.findById(id).getInvitation().split(",");
			Integer[] arr = new Integer[split.length];
			for (int i = 1; i < split.length; i++) { // 因為第0個是空字串
				arr[i] = Integer.parseInt(split[i]);
			}
			String str = "</br></br><h2>收到的邀請</h2></br>";
			List<Member> list = mService.findAllById(Arrays.asList(arr));
			for (Member m : list) {
				str += "<span>" + m.getId() + "</span>\r\n"
						+ "	<span><img style=\"width: 50px\" src=\"data:image/*;base64," + m.generateBase64Image()
						+ "\" /></span>\r\n" + "	<span>" + m.getUsername() + "</span>" + "	<span id=\"invitation_"
						+ m.getId() + "\"></span>" + "<button onclick=\"beFriend(" + m.getId() + ")\">接受</button>"
						+ "<button onclick=\"unFriend(" + m.getId() + ")\">拒絕</button>" + "</br>";
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@ResponseBody
	@PostMapping("/member/ajax/friendList")
	public String friendList(@RequestParam("id") Integer id) {
		try {
			String[] split = mService.findById(id).getFriend().split(",");
			Integer[] arr = new Integer[split.length];
			for (int i = 1; i < split.length; i++) { // 因為第0個是空字串
				arr[i] = Integer.parseInt(split[i]);
			}
			String str = "</br></br><h2>好友列表</h2></br>";
			List<Member> list = mService.findAllById(Arrays.asList(arr));
			for (Member m : list) {
				str += "<span>" + m.getId() + "</span>\r\n"
						+ "	<span><img style=\"width: 50px\" src=\"data:image/*;base64," + m.generateBase64Image()
						+ "\" /></span>\r\n" + "	<span>" + m.getUsername() + "</span>" + "	<span id=\"friend_"
						+ m.getId() + "\"></span>"+ "<button onclick=\"unFriend(" + m.getId() + ")\">移除</button>" + "</br>";
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@ResponseBody
	@PostMapping("/member/ajax/inviteOrAccept")
	public String inviteOrAccept(@RequestParam("id") Integer id, HttpSession hs) {
		Member m1 = mService.findById(((Member) hs.getAttribute("loggedInMember")).getId());
		Member m2 = mService.findById(id);
		List<String> invite1 = new ArrayList<String>(Arrays.asList(m1.getInvite().split(",")));
		List<String> invitation1 = new ArrayList<String>(Arrays.asList(m1.getInvitation().split(",")));
		List<String> friend1 = new ArrayList<String>(Arrays.asList(m1.getFriend().split(",")));
		List<String> invite2 = new ArrayList<String>(Arrays.asList(m2.getInvite().split(",")));
		List<String> invitation2 = new ArrayList<String>(Arrays.asList(m2.getInvitation().split(",")));
		List<String> friend2 = new ArrayList<String>(Arrays.asList(m2.getFriend().split(",")));
		if(!friend1.contains(String.valueOf(id))) {
			System.out.println("未加好友");
			if (!invite1.contains(String.valueOf(id))) { 
				invitation2.add(String.valueOf(m1.getId()));
				invite1.add(String.valueOf(id));
				System.out.println(m1.getId()+"已發送邀請"+id);
			}
		}else {return "";}
		if((invitation1.contains(String.valueOf(id))&&invite1.contains(String.valueOf(id)))||(invitation2.contains(String.valueOf(m1.getId()))&&invite2.contains(String.valueOf(m1.getId())))) {
			invitation1.remove(String.valueOf(id));
			invite1.remove(String.valueOf(id));
			friend1.add(String.valueOf(id));
			System.out.println(m1.getId()+"已加"+id);
			invitation2.remove(String.valueOf(m1.getId()));
			invite2.remove(String.valueOf(m1.getId()));
			friend2.add(String.valueOf(m1.getId()));
			System.out.println(id+"已加"+m1.getId());
		}
		m1.setInvite(String.join(",", invite1));
		m1.setInvitation(String.join(",", invitation1));
		m1.setFriend(String.join(",", friend1));
		m2.setInvite(String.join(",", invite2));
		m2.setInvitation(String.join(",", invitation2));
		m2.setFriend(String.join(",", friend2));
		mService.updateById(m1);
		mService.updateById(m2);
		return String.valueOf(id);
	}

	@ResponseBody
	@PostMapping("/member/ajax/cancelOrReject")
	public String cancelOrReject(@RequestParam("id") Integer id, HttpSession hs) {
		Member m1 = mService.findById(((Member) hs.getAttribute("loggedInMember")).getId());
		Member m2 = mService.findById(id);
		List<String> invite1 = new ArrayList<String>(Arrays.asList(m1.getInvite().split(",")));
		List<String> invitation1 = new ArrayList<String>(Arrays.asList(m1.getInvitation().split(",")));
		List<String> friend1 = new ArrayList<String>(Arrays.asList(m1.getFriend().split(",")));
		List<String> invite2 = new ArrayList<String>(Arrays.asList(m2.getInvite().split(",")));
		List<String> invitation2 = new ArrayList<String>(Arrays.asList(m2.getInvitation().split(",")));
		List<String> friend2 = new ArrayList<String>(Arrays.asList(m2.getFriend().split(",")));
		invitation1.remove(String.valueOf(id));
		invite1.remove(String.valueOf(id));
		invitation2.remove(String.valueOf(m1.getId()));
		invite2.remove(String.valueOf(m1.getId()));
		friend1.remove(String.valueOf(id));
		friend2.remove(String.valueOf(m1.getId()));
		m1.setInvite(String.join(",", invite1));
		m1.setInvitation(String.join(",", invitation1));
		m1.setFriend(String.join(",", friend1));
		m2.setInvite(String.join(",", invite2));
		m2.setInvitation(String.join(",", invitation2));
		m2.setFriend(String.join(",", friend2));
		mService.updateById(m1);
		mService.updateById(m2);
		return String.valueOf(id);
	}

	@GetMapping("/member/page")
	public String findMembers(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "group", defaultValue = "1") Integer group,
			@RequestParam(name = "sort", defaultValue = "id") String sort,
			@RequestParam(name = "order", defaultValue = "asc") String order,
			@RequestParam(name = "where", defaultValue = "all") String where,
			@RequestParam(name = "search", defaultValue = "") String search, Model model) {
		Page<Member> page = mService.findAllByPage(pageNumber, sort, order, where, search);
		model.addAttribute("search", search);
		model.addAttribute("where", where);
		model.addAttribute("order", order);
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("p", pageNumber);
		model.addAttribute("group", group);
		return "member/showMembers";
	}

}
