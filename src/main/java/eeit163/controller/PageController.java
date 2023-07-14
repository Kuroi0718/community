package eeit163.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eeit163.model.Ad;
import eeit163.model.Article;
import eeit163.model.ForumLikes;
import eeit163.model.Member;
import eeit163.service.AdService;
import eeit163.service.ArticleService;
import eeit163.service.ForumLikesService;
import eeit163.service.MemberService;
import eeit163.service.MyOrderService;
import eeit163.service.ProductService;
import eeit163.service.ReplyService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {
	@Autowired
	private MemberService mService;
	@Autowired
	private MyOrderService oService;
	@Autowired
	private ProductService pService;
	@Autowired
	private AdService adService;
	@Autowired
	private ForumLikesService flService;
	@Autowired
	private ArticleService aService;
	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/member/signUpPage")
	public String signup() {
		return "member/signUp";
	}
	
	@GetMapping("/member/forumPage")
	public String forum() {
		return "member/forum";
	}
	
	@GetMapping("/member/loginPage")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("/member/profilePage")
	public String profile(HttpSession hs,Model model) {
		Integer mid = ((Member) hs.getAttribute("loggedInMember")).getId();
		List<ForumLikes> bookmark = flService.showBookmark(mid);
		ArrayList<Article> arrayList = new ArrayList<Article>();
		for(int i=0;i<bookmark.size();i++) {
			arrayList.add(aService.findById(bookmark.get(i).getPostId()));
		}
		List<Article> list = aService.findByAuthorId(mid);
		model.addAttribute("bookmark", arrayList);
		model.addAttribute("myArticle", list);
		return "member/profile";
	}
	
	@GetMapping("/dashboardPage")
	public String dashboard(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "group", defaultValue = "1") Integer group,
			@RequestParam(name = "sort", defaultValue = "id") String sort,
			@RequestParam(name = "order", defaultValue = "asc") String order,
			@RequestParam(name = "where", defaultValue = "username") String where,
			@RequestParam(name = "search", defaultValue = "") String search,Model model,
			@RequestParam(name = "deleteSuccess", defaultValue = "false") boolean deleteSuccess,
            @RequestParam(name = "activeBlock", defaultValue = "Block1") String activeBlock) {
		Page<Member> page = mService.findAllByPage(pageNumber, sort, order, where, search);
		model.addAttribute("blockedArticleList", aService.findByLock());
		model.addAttribute("blockedReplyList", replyService.findByLock());
		List<Ad> adList = adService.listAllAds();
		model.addAttribute("adList", adList);
		model.addAttribute("search", search);
		model.addAttribute("where", where);
		model.addAttribute("order", order);
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("p", pageNumber);
		model.addAttribute("group", group);
		model.addAttribute("TheOrderList",oService.findOrderList());
		model.addAttribute("productList",pService.findAllProductsForAdmin());
		model.addAttribute("adList", adList);
		 model.addAttribute("deleteSuccess", deleteSuccess);
	     model.addAttribute("activeBlock", activeBlock); // 将activeBlock参数传递到页面
		return "dashboard";
	}

}