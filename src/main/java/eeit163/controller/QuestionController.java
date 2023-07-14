package eeit163.controller;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Member;
import eeit163.model.Question;
import eeit163.service.MemberService;
import eeit163.service.ProductService;
import eeit163.service.QuestionService;
import jakarta.servlet.http.HttpSession;

@Controller
public class QuestionController {
	@Autowired
	private QuestionService qService;
	
	@Autowired
	private MemberService mService;
	@Autowired
	private ProductService pService;
	
	@ResponseBody
	@PostMapping("/mall/productQuestion")
	public String productQuestion(
			@RequestParam("content") String content,
			@RequestParam("productId")Integer productId,
			HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		Question question = new Question();
		question.setContent(content);
		question.setOwnerId(pService.findProductById(productId).getOwnerId());
		question.setProductId(productId);
		question.setAskerId(member.getId());
		question.setAskDate(new Date());
		qService.addQuestion(question);
		 
		return "<div style=\"display:flex;flex-direction:row\" >\r\n"
				+ "				<div class=\"QuestionCard\" style=\"width: 40rem;height:8rem;overflow:hidden;border:1px solid grey;background-color:white\">\r\n"
				+ "					<div style=\"display:flex;flex-direction:row\">\r\n"
				+ "						<div style=\"width: 4rem; height: 4rem; overflow: hidden\">\r\n"
				+ "							<img src=\"../image/question.png\" style=\"width: 4rem; height: 4rem;\">\r\n"
				+ "						</div>\r\n"
				+ "						<div style=\"display:flex;flex-direction:column\">\r\n"
				+ "							<div class=\"card-body\" style=\"padding:20px\" >\r\n"
				+ "								<p class=\"card-text\">問題:"+question.getContent()+"</p>\r\n"
				+ "								<small class=\"text-muted\">提問者:<img src=\""+"data:image/*;base64,"+Base64.getEncoder().encodeToString(mService.getMemberPhotoById(question.getAskerId()))+"\"\r\n"
				+ "								style=\"width: 2rem; height: 2rem;\">發問時間:"+question.getAskDate()+"</small>\r\n"
				+ "							</div>\r\n"
				+ "						</div>\r\n"
				+ "					</div>\r\n"
				+ "				</div>";
	}
	
	@ResponseBody
	@PostMapping("/mall/productAnswer")//賣家回答問與答
	public String productAnswer(@RequestParam("questionId")Integer questionId,
			@RequestParam("answer")String answer) {
		qService.answerByQuestionId(answer, new Date(),questionId);
		return "";
	}
	
	

}
