package eeit163.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eeit163.model.Member;
import eeit163.service.MemberService;
import eeit163.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired
	private MemberService mService;
	@Autowired
	private ProductService pService;

	@Value("classpath:static/image/noimg.png")
	private Resource noimg;

	@PostMapping("/member/signup")
	public String signup(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("level") String level, @RequestParam("gender") String gender,
			@RequestParam("birthday") String birthday,
//				@RequestParam("creationdate") Date creationdate,
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
//			member.setCreationdate(creationdate);
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

	@PostMapping("/member/update")
	public String update(@RequestParam("id") Integer id, @RequestParam("username") String username,
			@RequestParam("gender") String gender, @RequestParam("password") String password,
			@RequestParam("level") String level, @RequestParam("tel") String tel,
			@RequestParam("birthday") String birthday, @RequestParam("email") String email,
			@RequestParam("photo") MultipartFile photo, HttpSession hs) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
			if (tel != null && tel.length() != 0) {
				member.setTel(tel);
			}
			if (gender != null && gender.length() != 0) {
				member.setGender(gender);
			}
			if (birthday != null && birthday.length() != 0) {
				member.setBirthday(df.parse(birthday));
			}
			if (email != null && email.length() != 0) {
				member.setEmail(email);
			}
			if (photo.getSize() != 0L) {
				member.setPhoto(photo.getBytes());
			}
			mService.updateById(member);
			hs.setAttribute("loggedInMember", mService.findByUsername(username));
			return "redirect:/member/profilePage";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/member/profilePage";
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
		return "redirect:/ad/displayImage";
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
		} else if ("disabled".equals(mService.findByUsername(username).getLevel())) {
			model.addAttribute("msg", "帳號已停權");
			return "member/login";
		} else {
			model.addAttribute("msg", str);
			hs.setAttribute("loggedInMember", mService.findByUsername(username));
			return "redirect:/ad/displayImage";
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

	@ResponseBody
	@PostMapping("/member/ajax/photo")
	public String getBase64ById(@RequestParam("id") Integer id) {
		return mService.findById(id).generateBase64Image();
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

	@ResponseBody
	@PostMapping("/member/ajax/searchMember")
	public String searchMember(@RequestParam("username") String username, HttpSession hs) {
		try {
			Member m1 = mService.findById(((Member) hs.getAttribute("loggedInMember")).getId());
			List<String> invite1 = new ArrayList<String>(Arrays.asList(m1.getInvite().split(",")));
			List<String> invitation1 = new ArrayList<String>(Arrays.asList(m1.getInvitation().split(",")));
			List<String> friend1 = new ArrayList<String>(Arrays.asList(m1.getFriend().split(",")));
			Member m = mService.findByUsername(username);
			String str0 = "new";
			if (invite1.contains(String.valueOf(m.getId()))) {
				str0 = "invite";
				System.out.println("邀請" + m.getId() + "中");
			}
			if (invitation1.contains(String.valueOf(m.getId()))) {
				str0 = "invitation";
				System.out.println("受" + m.getId() + "其邀請");
			}
			if (friend1.contains(String.valueOf(m.getId()))) {
				str0 = "friend";
				System.out.println(m.getId() + "朋友");
			}
			String str;
			str = "{\"id\":\"" + m.getId() + "\",\"username\":\"" + m.getUsername() + "\",\"relation\":\"" + str0
					+ "\",\"photo\":\"" + m.generateBase64Image() + "\"}";
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
			List<Member> list = mService.findAllById(Arrays.asList(arr));
			StringBuilder sb = new StringBuilder();
			sb.append("<hr>邀請中<hr>");
			for (Member m : list) {
				sb.append("<span>").append(m.getId()).append("</span>\r\n").append("<span><img id=\"targetPhoto_")
						.append(m.getId()).append("\" style=\"width: 50px\" src=\"data:image/*;base64,")
						.append(m.generateBase64Image()).append("\" /></span>\r\n").append("	<span>")
						.append(m.getUsername()).append("</span>").append("<span id=\"invite_").append(m.getId())
						.append("\"></span>").append("<button onclick=\"unFriend(").append(m.getId())
						.append(")\"><img style='height:25px;' src='/community/image/circle-xmark-solid.png'></button>")
						.append("<hr>");
			}
			return sb.toString();
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
			List<Member> list = mService.findAllById(Arrays.asList(arr));
			StringBuilder sb = new StringBuilder();
			for (Member m : list) {
				sb.append("<span>").append(m.getId()).append("</span>\r\n").append("	<span><img id=\"targetPhoto_")
						.append(m.getId()).append("\" style=\"width: 50px\" src=\"data:image/*;base64,")
						.append(m.generateBase64Image()).append("\" /></span>\r\n").append("	<span>")
						.append(m.getUsername()).append("</span>").append("	<span id=\"invitation_").append(m.getId())
						.append("\"></span>").append("<button onclick=\"beFriend(").append(m.getId())
						.append(")\"><img style='height:25px;' src='/community/image/circle-check-solid.png'></button>")
						.append("<button onclick=\"unFriend(").append(m.getId())
						.append(")\"><img style='height:25px;' src='/community/image/circle-xmark-solid.png'></button>")
						.append("<hr>");
			}
			return sb.toString();
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
			List<Member> list = mService.findAllById(Arrays.asList(arr));
			StringBuilder sb = new StringBuilder();
			for (Member m : list) {
				sb.append(
						"<div style=\"width=370px;display:flex;flex-direction:row;\"><span style='font-size:18px;line-height:50px;'>")
						.append(m.getId()).append("</span>\r\n").append("	<span><img id=\"targetPhoto_")
						.append(m.getId()).append("\" style=\"width: 50px\" src=\"data:image/*;base64,")
						.append(m.generateBase64Image()).append("\" /></span>\r\n")
						.append("<span style='font-size:18px;line-height:50px;' id=\"targetName_").append(m.getId())
						.append("\">").append(m.getUsername()).append("</span>")
						.append("<span style='flex-grow: 1;'></span>").append("<button id=\"readChat_")
						.append(m.getId())
						.append("\" style=\"font-weight:bold;color:white;width:40px;height:40px;background-image:url('/community/image/comment.png');background-size:cover;background-position:center;\" onclick=\"openChat(")
						.append(m.getId()).append(")\"></button><span style='flex-grow: 1;'></span>")
						.append("<button style='width:40px;height:40px;' onclick=\"unFriend(").append(m.getId())
						.append(")\"><img style='height:25px;' src='/community/image/person-circle-minus-solid.png'></button><span style='flex-grow: 1;'></span><span style='flex-grow: 1;'></span>")
						.append("<span id=\"targetOnlineStatus_").append(m.getId())
						.append("\" style=\"color:red\" >offline</span></div><hr>");
			}
			return sb.toString();
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
		if (!friend1.contains(String.valueOf(id))) {
			System.out.println("未加好友");
			if (!invite1.contains(String.valueOf(id))) {
				invitation2.add(String.valueOf(m1.getId()));
				invite1.add(String.valueOf(id));
				System.out.println(m1.getId() + "已發送邀請" + id);
			}
		} else {
			return "";
		}
		if ((invitation1.contains(String.valueOf(id)) && invite1.contains(String.valueOf(id)))
				|| (invitation2.contains(String.valueOf(m1.getId())) && invite2.contains(String.valueOf(m1.getId())))) {
			invitation1.remove(String.valueOf(id));
			invite1.remove(String.valueOf(id));
			friend1.add(String.valueOf(id));
			System.out.println(m1.getId() + "已加" + id);
			invitation2.remove(String.valueOf(m1.getId()));
			invite2.remove(String.valueOf(m1.getId()));
			friend2.add(String.valueOf(m1.getId()));
			System.out.println(id + "已加" + m1.getId());
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

	@ResponseBody
	@PostMapping("/message/friendIdList")
	public String friendIdList(HttpSession hs) {
		Integer id = ((Member) hs.getAttribute("loggedInMember")).getId();
		String[] split = mService.findById(id).getFriend().split(",");
		ArrayList<Integer> list = new ArrayList<Integer>();
		ObjectMapper objectMapper = new ObjectMapper();
		for (int i = 1; i < split.length; i++) {
			list.add(Integer.parseInt(split[i]));
		}
		try {
			return objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	@GetMapping("/member/page")
	public String findMembers(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "group", defaultValue = "1") Integer group,
			@RequestParam(name = "sort", defaultValue = "id") String sort,
			@RequestParam(name = "order", defaultValue = "asc") String order,
			@RequestParam(name = "where", defaultValue = "username") String where,
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

	@ResponseBody
	@PostMapping("/member/ajax/memberRelation")
	public Integer memberRelation(@RequestParam("id") Integer id, HttpSession hs) {
		Member m1 = mService.findById(((Member) hs.getAttribute("loggedInMember")).getId());
		List<String> invite1 = new ArrayList<String>(Arrays.asList(m1.getInvite().split(",")));
		List<String> invitation1 = new ArrayList<String>(Arrays.asList(m1.getInvitation().split(",")));
		List<String> friend1 = new ArrayList<String>(Arrays.asList(m1.getFriend().split(",")));
		Integer x = 0; // 0=已加好友、1=未加好友未邀請、2=未加好友已邀請、3=未加好友已受邀
		if (!friend1.contains(String.valueOf(id))) {
			if (!invite1.contains(String.valueOf(id))) {
				if (!invitation1.contains(String.valueOf(id))) {
					x = 1;
				} else {
					x = 3;
				}
			} else {
				x = 2;
			}
		}
		return x;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@ResponseBody
	@PostMapping("/mall/addToMyLikes")
	public String addToMyLikes(@RequestParam("productId") Integer productId, HttpSession hs) {
		Member member = (Member) hs.getAttribute("loggedInMember");
		Integer mid = member.getId();
		addToWhoLikes(productId, mid);
		String picture = "";
		boolean isliked = false;
		String pid = "" + productId;
		String newStr = "";
		String str = mService.findMyLikes(mid);
		String[] strs = str.split(",");
		if (str.equals("")) {
			str = str + pid;
			picture = "0";
		} else {
			for (String s : strs) {
				if (pid.equals(s)) {
					isliked = true;
				} else {
					if (newStr.equals("")) {
						newStr = s;
					} else {
						newStr += ("," + s);
					}
				}
			}
			if (isliked) {
				str = newStr;
				picture = "1";
			} else {
				str = str + "," + pid;
				picture = "0";
			}
		}
		;
		mService.updateMyLikes(str, mid);
		return picture;
	}

	public void addToWhoLikes(Integer productId, Integer memberId) {
		boolean isliked = false;
		String mid = "" + memberId;
		String newStr = "";
		String str = pService.findWhoLikes(productId);
		String[] strs = str.split(",");
		if (str.equals("")) {
			str = str + mid;
		} else {
			for (String s : strs) {
				if (mid.equals(s)) {
					isliked = true;
				} else {
					if (newStr.equals("")) {
						newStr = s;
					} else {
						newStr += ("," + s);
					}
				}
			}
			if (isliked) {
				str = newStr;
			} else {
				str = str + "," + mid;
			}
		}
		;
		pService.updateWhoLikes(str, productId);
	}

	@ResponseBody
	@PostMapping("/mall/addToMyRecord")
	public void addToMyRecord(@RequestParam("productId") Integer productId, HttpSession hs) {
		Member member = (Member) hs.getAttribute("loggedInMember");
		String str = mService.findMyRecord(member.getId());
		if (str.equals("")) {
			str = str + productId;
		} else {
			str = str + "," + productId;
		}
		;
		mService.updateMyRecord(str, member.getId());
	}

	@GetMapping("/mall/showLike")
	public ResponseEntity<byte[]> showLike(@RequestParam("productId") Integer productId, HttpSession hs) {
		byte[] photo;
		boolean isLiked = false;
		String pid = "" + productId;
		Member member = (Member) hs.getAttribute("loggedInMember");
		String str = mService.findMyLikes(member.getId());
		String[] strs = str.split(",");
		for (String s : strs) {
			if (pid.equals(s)) {
				isLiked = true;
			}
		}
		if (isLiked) {
			photo = Base64.getDecoder().decode(
					"iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAIDSURBVEhLvZaxayJBFMa/3YgiWmqRFBYrWFhZqmghWJkiWNpcKWiRNHYWyV1vCu0kCHdVsBGEVLFUwX9A0goeFtcYiZKzcG+/YVbMuSbGc+8Hj/Vz3vseM7uzO8rT0y8dNqPKq61szaTXa6PVamE4HGK5XCIQCCCdTiOfz+PlxSWzAK/3N+r1OjqdDkajEZxOJ8LhMLLZLBKJC5klYRPGYDDQg8EgG1qG2+3Wa7WayK1Wq0Jb5TE0TRN+prdo0u/3dY/HIxIURdkq2ox4PL7+bdZshlnPMfqumxhLIgZ8Pt+bgkPD9KEv/U807fSm2WxCVVXM53Nj7N9ZLBbCbzqdwvDHyWw2uxmPx9B1Nj8epp/hz6fEu56mHUF/xZiWvlqtDG0PXDbVuElS2oPf74cai8WktIdoNArubsu1PFYIfz7HqVTKMuHQ4H3mlb7rzTiZTPRQKCQGPtrx+wb96M0Qb+HnZwfa7R4ikchR9gt96Gfy5lV/f/9ovEETUh0G6+mzydb35O6uhUwmI9XnYB3r/8byo3V7+x25XE6q/WA+66zY+WW8vq6iWCxK9T6FQkHk72JnE3J5+RXlclkqazh+dfVNKmv2Okg8PPxAqVTaevIqlQrOz79ItZt3Z2JCo0ajAYfDITSv1Ps0IJ86Er2+/kS320UymYTLdSb//Zj/cO4C/gCM43Hr9zSgFAAAAABJRU5ErkJggg==");
		} else {
			photo = Base64.getDecoder().decode(
					"iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAHbSURBVEhL3ZWxS5VRGIc/E1NuDtJi2tAelEsIlkOB7unqYgRFWINN/gFN7TpUay1KtrhYVCjoYCgoBEENBU21iBRhlD3P53lFb/d66/oF0g+ee773nPf9fefce8652X+jhtSGmmEIBuA0HIUP8AzuwUcInYRr0AenYBNewzQ8hG/wm7rhLWxV4SvcBHULjCvlyTvQL1espAeewjEw6QW8BGdzBgbBMbUA57cfsy/gzFehBS7CJdDXsX5YhLz4PWj+CUwqVwfMwe7ZGndCuazXxxx988mNpo4fcMGOKmqFJTD3VYqrqRf0M1f/fPkGjw1q6DhcTW0t+TXqq3+2kYIbBgVKP303jvBRsgetp7YohV/Jl3zefs73epEKPzdB9gRc1jKUH856pY9++uqfXU6BeMiKkD7hqX/+1uepw213HQ4ir5rYvvrufDsn4A048BPGoB5ZFyvQT989aocViKS78Ke/kXnmR60++lVUG8xDJD+ARthPjt+HqLFen33luZmBKJoC/wIqyf5JiFzr4tzVVBM8giiehfK7ytj+yDHfur+SB3UcwsQrO+4sW+MYmwDz69YdCLM1OJfa6HO8EN0Gt3YYi7H9hWoYvoMvsL0C/0RdMAJn8+jwKMt+AVcEkQfK8y7oAAAAAElFTkSuQmCC");
		}
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		// 回傳值 , header, status
		return new ResponseEntity<byte[]>(photo, header, HttpStatus.OK);
	}

	@GetMapping("/mall/showMemberPhoto")
	public ResponseEntity<byte[]> showMemberPhoto(@RequestParam("id") Integer id) {
		byte[] photo = mService.getMemberPhotoById(id);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		// 回傳值 , header, status
		return new ResponseEntity<byte[]>(photo, header, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/mall/findMyFriendList")
	public String findMyFriendList(@RequestParam("productId") Integer productId, HttpSession hs, Model model) {
		Member member = (Member) hs.getAttribute("loggedInMember");
		Member m = mService.findById(member.getId());
		String[] myFriendArray = m.getFriend().split(",");

		int[] arr2 = new int[myFriendArray.length];
		for (int i = 1; i < myFriendArray.length; i++) {
			arr2[i] = Integer.parseInt(myFriendArray[i]);
		}
		List<Member> myFriendList = mService.findMembersByNameArray(arr2);
		String str1 = "";
		for (int i = 0; i < myFriendList.size(); i++) {
			str1 += "<figure>\r\n"
					+ "					<input type=\"checkbox\" style=\"width:50px;height: 80px;position:absolute;margin-left:50px\" value=\""
					+ myFriendList.get(i).getId() + "\">\r\n"
					+ "					<div id=\"share\" style=\"width: 200px; height: 80px; margin: auto;margin-top:20px;border: 1px solid silver;background-color:#F2EFE9;\">\r\n"
					+ "					<div style=\"position:absolute;margin-top:30px;margin-left:30px\">"
					+ myFriendList.get(i).getUsername() + "</div>\r\n" + "					<img src=\""
					+ "data:image/*;base64,"
					+ Base64.getEncoder().encodeToString(mService.getMemberPhotoById(myFriendList.get(i).getId()))
					+ "\"  style=\"width:70px;float:right;margin:5px\">\r\n" + "					</div>\r\n"
					+ "					</figure>";

		}
		if (myFriendList.size() == 0) {
			str1 = "<p style=\"text-align:center;font-size:35px\" >沒有好友</p>";
		}
		;
		String str2 = "<input id=\"shareProductId\" value=\"" + productId + "\" type=\"hidden\"></input>";
		String str3 = "<div style=\"display: flex; flex-direction: row\">\r\n"
				+ "					<button id=\"closeShareButton\" style=\"width:150px;height:49.5px;line-height:49.5px;color:white;font-weight:bold;font-size:20px;\r\n"
				+ "					background-image:url('../image/blackBTN.png');background-size:cover;background-color:transparent;border:none;margin-left:40px\" onclick=\"closeShareProduct()\">取消</button>\r\n"
				+ "					<button id=\"goShare\" style=\"width:150px;height:49.5px;line-height:49.5px;color:white;font-weight:bold;font-size:20px;\r\n"
				+ "					background-image:url('../image/blackBTN.png');background-size:cover;background-color:transparent;border:none;margin-left:10px\" onclick=\"sendShareList()\">分享</button>\r\n"
				+ "					</div>";

		return str1 + str2 + str3;
	}

	@ResponseBody
	@PostMapping("/mall/deleteMyRecord")
	public String deleteMyRecord(HttpSession hs) {
		Member member = (Member) hs.getAttribute("loggedInMember");
		mService.updateMyRecord("", member.getId());
		return "";
	}

}
