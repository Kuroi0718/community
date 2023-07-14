package eeit163.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Member;
import eeit163.model.Reply;
import eeit163.service.ReplyService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReplyController {
	@Autowired
	private ReplyService rService;
	
	@PostMapping("/reply/add")
	public String addreply(@RequestParam("articleId") Integer articleId, @RequestParam("replyContent") String content, HttpSession hs) {
		Reply reply = new Reply();
		Integer authorId = ((Member) hs.getAttribute("loggedInMember")).getId();
		reply.setArticleId(articleId);
		reply.setContent(content);
		reply.setAuthorId(authorId);
		reply.setLikes(0);
		rService.insertReply(reply);
		String str= "redirect:/article/showArticle?id="+articleId;
		return str;
	}

	@ResponseBody
	@PostMapping("/reply/delete")
	public void deletereply(@RequestParam("id") Integer id) {
		rService.deleteById(id);
	}

	@ResponseBody
	@PostMapping("/reply/update")
	public String update(@RequestParam("replyId") Integer id,@RequestParam("replyContentEdit") String content) {
		Reply reply = rService.findById(id);
		if (content != null && content.length() != 0) {
			reply.setContent(content);
		}
		reply.setReplyDate(new Date());
		rService.updateById(reply);
		return reply.getReplyDateString();
	}
	
	@ResponseBody
	@PostMapping("/reply/toggleLock")
	public String toggleLock(@RequestParam("replyId") Integer id,HttpSession hs) {
		Reply reply = rService.findById(id);
		Member m=(Member) hs.getAttribute("loggedInMember");
		reply.setReplyDate(new Date());
		String str="";
		if("admin".equals(m.getLevel())) {
			if(reply.getStatus()==0) {
				reply.setStatus(2);
				str="內容已封鎖";
			}else if(reply.getStatus()==1) {
				str="已由原作者屏蔽";
			}else if(reply.getStatus()==2) {
				reply.setStatus(0);
				str=reply.getContent();
			}
		}else {
		if(reply.getStatus()==0) {
			reply.setStatus(1);
			str="已由原作者屏蔽";
		}else if(reply.getStatus()==2) {
			str="內容已封鎖";
		}else if(reply.getStatus()==1) {
			reply.setStatus(0);
			str=reply.getContent();
		}}
		rService.updateById(reply);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"msg\": \"").append(str).append("\",");
		sb.append("\"time\": \"").append(reply.getReplyDateString()).append("\"");
		sb.append("}");
		return sb.toString();
	}

}
