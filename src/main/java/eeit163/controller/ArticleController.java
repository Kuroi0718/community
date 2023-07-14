package eeit163.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Article;
import eeit163.model.ForumLikes;
import eeit163.model.Member;
import eeit163.model.Reply;
import eeit163.service.ArticleService;
import eeit163.service.ForumLikesService;
import eeit163.service.MemberService;
import eeit163.service.ReplyService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService aService;

	@Autowired
	private ReplyService rService;

	@Autowired
	private MemberService mService;

	@Autowired
	private ForumLikesService flService;

	@PostMapping("/article/add")
	public String addArticle(@RequestParam("articleTitle") String title, @RequestParam("articleContent") String content,
			HttpSession hs) {
		Article article = new Article();
		Integer authorId = ((Member) hs.getAttribute("loggedInMember")).getId();
		article.setTitle(title);
		article.setContent(content);
		article.setAuthorId(authorId);
		article.setAuthorName(((Member) hs.getAttribute("loggedInMember")).getUsername());
		article.setLikes(0);
		aService.insertArticle(article);
		return "redirect:/article/page";
	}

	@PostMapping("/article/delete")
	public String deleteArticle(@RequestParam("id") Integer id) {
		aService.deleteById(id);
		return "redirect:/article/page";
	}

	@ResponseBody
	@PostMapping("/article/update")
	public String update(@RequestParam("articleId") Integer id, @RequestParam("articleTitleEdit") String title,
			@RequestParam("articleContentEdit") String content, HttpSession hs,
			@RequestParam("articleLikes") Integer likes) {
		System.out.println(content);
		Article article = aService.findById(id);
		Integer authorId = ((Member) hs.getAttribute("loggedInMember")).getId();
		String authorName = ((Member) hs.getAttribute("loggedInMember")).getUsername();
		if (title != null && title.length() != 0) {
			article.setTitle(title);
		}
		if (content != null && content.length() != 0) {
			article.setContent(content);
		}
		if (authorName != null && authorName.length() != 0) {
			article.setAuthorName(authorName);
		}
		if (authorId != null) {
			article.setAuthorId(authorId);
		}
		if (likes != null) {
			article.setLikes(likes);
		}
		article.setPostDate(new Date());
		aService.updateById(article);
		return article.getPostDateString();
	}

	@GetMapping("/article/page")
	public String findArticles(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "group", defaultValue = "1") Integer group,
			@RequestParam(name = "sort", defaultValue = "id") String sort,
			@RequestParam(name = "order", defaultValue = "desc") String order,
			@RequestParam(name = "where", defaultValue = "title") String where,
			@RequestParam(name = "search", defaultValue = "") String search, Model model) {
		Page<Article> page = aService.findAllByPage(pageNumber, sort, order, where, search);
		model.addAttribute("search", search);
		model.addAttribute("where", where);
		model.addAttribute("order", order);
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("p", pageNumber);
		model.addAttribute("group", group);
		return "member/Forum";
	}

	@GetMapping("/article/showArticle")
	public String findArticles(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "group", defaultValue = "1") Integer group, @RequestParam("id") Integer id,
			Model model) {
		Article article = aService.findById(id);
		Member author = mService.findById(article.getAuthorId());
		List<Reply> list = rService.findReplyByArticleId(id);
		model.addAttribute("replyList", list);
		model.addAttribute("article", article);
		model.addAttribute("author", author);
		return "member/showArticle";
	}

	@ResponseBody
	@GetMapping("/article/showRepliesMembers")
	public String findMembersOfReplies(@RequestParam("id") Integer id) {
		List<Reply> list0 = rService.findReplyByArticleId(id);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < list0.size(); i++) {
			sb.append("{");
			sb.append("\"id\": ").append(list0.get(i).getId()).append(",");
			sb.append("\"mid\": ").append(list0.get(i).getAuthorId()).append(",");
			sb.append("\"username\": \"").append(mService.findById(list0.get(i).getAuthorId()).getUsername())
					.append("\"");
			sb.append("}");
			if (i < list0.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@ResponseBody
	@GetMapping("/article/showRepliesPhoto")
	public String findPhotoOfReplies(@RequestParam("id") Integer id) {
		List<Reply> list0 = rService.findReplyByArticleId(id);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < list0.size(); i++) {
			sb.append("{");
			sb.append("\"id\": ").append(list0.get(i).getId()).append(",");
			sb.append("\"src\": \"").append(mService.findById(list0.get(i).getAuthorId()).generateBase64Image())
					.append("\"");
			sb.append("}");
			if (i < list0.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@ResponseBody
	@GetMapping("/article/showLikes")
	public Integer showArticleLikes(@RequestParam("articleId") Integer id) {
		return flService.showLikes(id, "article");
	}

	@ResponseBody
	@GetMapping("/reply/showLikes")
	public Integer showReplyLikes(@RequestParam("replyId") Integer id) {
		return flService.showLikes(id, "reply");
	}

	@ResponseBody
	@PostMapping("/article/toggleLikes")
	public void toggleArticleLikes(@RequestParam("articleId") Integer articleId, HttpSession hs) {
		Integer memberId = ((Member) hs.getAttribute("loggedInMember")).getId();
		if (0 == flService.checkLikes(articleId, memberId, "article")) {
			ForumLikes like = new ForumLikes();
			like.setPostId(articleId);
			like.setMemberId(memberId);
			like.setType("article");
			flService.insertForumLikes(like);
			Article article = aService.findById(articleId);
			Integer l = article.getLikes();
			article.setLikes(l + 1);
			aService.updateById(article);
		} else {
			flService.deleteById(flService.checkLikes(articleId, memberId, "article"));
			Article article = aService.findById(articleId);
			Integer l = article.getLikes();
			article.setLikes(l - 1);
			aService.updateById(article);
		}
	}

	@ResponseBody
	@PostMapping("/reply/toggleLikes")
	public void toggleReplyLikes(@RequestParam("replyId") Integer replyId, HttpSession hs) {
		Integer memberId = ((Member) hs.getAttribute("loggedInMember")).getId();
		if (0 == flService.checkLikes(replyId, memberId, "reply")) {
			ForumLikes like = new ForumLikes();
			like.setPostId(replyId);
			like.setMemberId(memberId);
			like.setType("reply");
			flService.insertForumLikes(like);
			Reply reply = rService.findById(replyId);
			Integer l = reply.getLikes();
			reply.setLikes(l + 1);
			rService.updateById(reply);
		} else {
			flService.deleteById(flService.checkLikes(replyId, memberId, "reply"));
			Reply reply = rService.findById(replyId);
			Integer l = reply.getLikes();
			reply.setLikes(l - 1);
			rService.updateById(reply);
		}
	}

	@ResponseBody
	@PostMapping("/bookmark/toggleLikes")
	public void toggleBookmarkLikes(@RequestParam("articleId") Integer articleId, HttpSession hs) {
		Integer memberId = ((Member) hs.getAttribute("loggedInMember")).getId();
		if (0 == flService.checkBookmark(memberId, articleId)) {
			ForumLikes like = new ForumLikes();
			like.setPostId(articleId);
			like.setMemberId(memberId);
			like.setType("bookmark");
			flService.insertForumLikes(like);
		} else {
			flService.deleteById(flService.checkBookmark(memberId, articleId));
		}
	}

	@ResponseBody
	@GetMapping("/article/checkLikes")
	public Integer checkArticleLikes(@RequestParam("articleId") Integer id, HttpSession hs) {
		Integer memberId = ((Member) hs.getAttribute("loggedInMember")).getId();
		return flService.checkLikes(id, memberId, "article");
	}

	@ResponseBody
	@GetMapping("/reply/checkLikes")
	public Integer checkReplyLikes(@RequestParam("replyId") Integer id, HttpSession hs) {
		Integer memberId = ((Member) hs.getAttribute("loggedInMember")).getId();
		return flService.checkLikes(id, memberId, "reply");
	}

	@ResponseBody
	@GetMapping("/bookmark/checkLikes")
	public Integer checkBookmarkLikes(@RequestParam("articleId") Integer id, HttpSession hs) {
		Integer memberId = ((Member) hs.getAttribute("loggedInMember")).getId();
		return flService.checkBookmark(memberId, id);
	}

	@ResponseBody
	@PostMapping("/article/toggleLock")
	public String toggleLock(@RequestParam("articleId") Integer id, HttpSession hs) {
		Article article = aService.findById(id);
		Member m = (Member) hs.getAttribute("loggedInMember");

		article.setPostDate(new Date());
		String str = "";
		if ("admin".equals(m.getLevel())) {
			if (article.getStatus() == 0) {
				article.setStatus(2);
				str = "內容已封鎖";
			} else if (article.getStatus() == 1) {
				str = "已由原作者屏蔽";
			} else if (article.getStatus() == 2) {
				article.setStatus(0);
				str = article.getContent();
			}
		} else {
			if (article.getStatus() == 0) {
				article.setStatus(1);
				str = "已由原作者屏蔽";
			} else if (article.getStatus() == 2) {
				str = "內容已封鎖";
			} else if (article.getStatus() == 1) {
				article.setStatus(0);
				str = article.getContent();
			}
		}
		aService.updateById(article);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"msg\": \"").append(str).append("\",");
		sb.append("\"time\": \"").append(article.getPostDateString()).append("\"");
		sb.append("}");
		return sb.toString();
	}

}
