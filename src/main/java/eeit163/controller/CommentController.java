package eeit163.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Comment;
import eeit163.model.Member;
import eeit163.service.CommentService;
import eeit163.service.MemberService;
import eeit163.service.MyOrderService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private MyOrderService oService;
	@Autowired
	private MemberService mService;
	
	@ResponseBody
	@PostMapping("/mall/rating")
	public String rating(@RequestParam("score")Integer score,
			@RequestParam("ratingTargetId")Integer ratingTargetId,
			@RequestParam("content")String content,
			@RequestParam("serialIdInput")String serialIdInput,HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		Member seller = mService.findById(ratingTargetId);
		Comment c = new Comment();
		c.setAuthorId(member.getId());
		c.setContent(content);
		c.setRatingTargetId(ratingTargetId);;
		c.setScore(score);
		c.setCommentDate(new Date());
		c.setType("買家評價");
		c.setSerialId(serialIdInput);
		seller.setSellScore(seller.getSellScore()+score);
		seller.setSellScoreTimes(seller.getSellScoreTimes()+1);
		commentService.insertComment(c);
		oService.updateBuyerCommentBySerialId(serialIdInput);
		return "";
	}
	
	@ResponseBody
	@PostMapping("/mall/senderRating")
	public String senderRating(@RequestParam("score")Integer score,
			@RequestParam("ratingTargetId")Integer ratingTargetId,
			@RequestParam("content")String content,
			@RequestParam("serialIdInput")String serialIdInput,HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		Member buyer = mService.findById(ratingTargetId);
		Comment c = new Comment();
		c.setAuthorId(member.getId());
		c.setContent(content);
		c.setRatingTargetId(ratingTargetId);;
		c.setScore(score);
		c.setCommentDate(new Date());
		c.setType("賣家評價");
		c.setSerialId(serialIdInput);
		buyer.setBuyScore(buyer.getBuyScore()+score);
		buyer.setBuyScoreTimes(buyer.getBuyScoreTimes()+1);
		commentService.insertComment(c);
		oService.updateSenderCommentBySerialId(serialIdInput);
		return "";
	}
	
	
	
	
	
	
}
