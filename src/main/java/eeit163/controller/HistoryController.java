package eeit163.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eeit163.model.History;
import eeit163.model.Member;
import eeit163.service.HistoryService;
import eeit163.service.MemberService;
import eeit163.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HistoryController {
	@Autowired
	private HistoryService hService;
	@Autowired
	private MemberService mService;
	@Autowired
	private ProductService pService;
	@ResponseBody
	@PostMapping("/mall/ageAnalysis")
	public String ageAnalysis(
			@RequestParam("productAnalysis") String productName,
			@RequestParam("timeAnalysis") String time, HttpSession hs) {
		int zeroten = 0;
		int tentwenty = 0;
		int twentythirty = 0;
		int thirtyfourty = 0;
		int fourtyfifty = 0;
		int fiftysixty = 0;
		int sixtyseventy = 0;
		int overseventy = 0;
		Date now = new Date();
		Member member = (Member)hs.getAttribute("loggedInMember");
		Calendar calendar = Calendar.getInstance();
		List<History> myHistoryList = new ArrayList<>();
		if("不限".equals(productName)) {
			//我賣出的商品List
			if("不限".equals(time)) {myHistoryList = hService.findHistoryByOwnerId(member.getId());}
			
			if("近一週".equals(time)) {
				calendar.add(Calendar.DAY_OF_MONTH, -7);
				myHistoryList = hService.findHistoryByOwnerIdTime(member.getId(),calendar.getTime());
			}
			
			if("近一個月".equals(time)) {
				calendar.add(Calendar.DAY_OF_MONTH, -30);
				myHistoryList = hService.findHistoryByOwnerIdTime(member.getId(),calendar.getTime());
				}
			
			if("近一年".equals(time)) {
				calendar.add(Calendar.DAY_OF_MONTH, -365);
				myHistoryList = hService.findHistoryByOwnerIdTime(member.getId(),calendar.getTime());
				}
			
		}else {
			//我賣出的商品List
			if("不限".equals(time)) {myHistoryList = hService.findHistoryByOwnerIdAndProductName(member.getId(),productName);}
			if("近一週".equals(time)) {
				calendar.add(Calendar.DAY_OF_MONTH, -7);
				myHistoryList = hService.findHistoryByOwnerIdAndProductNameTime(member.getId(),productName,calendar.getTime());
				}
			if("近一個月".equals(time)) {
				calendar.add(Calendar.DAY_OF_MONTH, -30);
				myHistoryList = hService.findHistoryByOwnerIdAndProductNameTime(member.getId(),productName,calendar.getTime());
				}
			if("近一年".equals(time)) {
				calendar.add(Calendar.DAY_OF_MONTH, -365);
				myHistoryList = hService.findHistoryByOwnerIdAndProductNameTime(member.getId(),productName,calendar.getTime());
				}
		}
		for(int i=0;i<myHistoryList.size();i++) {
			long timeDiff = Math.abs(now.getTime()-mService.findById(myHistoryList.get(i).getMemberId()).getBirthday().getTime());
			long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
			if(daysDiff<365*10) {zeroten+=1;}
			if(365*10<=daysDiff&&daysDiff<365*20) {tentwenty+=1;}
			if(365*20<=daysDiff&&daysDiff<365*30) {twentythirty+=1;}
			if(365*30<=daysDiff&&daysDiff<365*40) {thirtyfourty+=1;}
			if(365*40<=daysDiff&&daysDiff<365*50) {fourtyfifty+=1;}
			if(365*50<=daysDiff&&daysDiff<365*60) {fiftysixty+=1;}
			if(365*60<=daysDiff&&daysDiff<365*70) {sixtyseventy+=1;}
			if(365*70<=daysDiff) {overseventy+=1;}
		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(zeroten);list.add(tentwenty);list.add(twentythirty);list.add(thirtyfourty);
		list.add(fourtyfifty);list.add(fiftysixty);list.add(sixtyseventy);list.add(overseventy);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "<div id=\"age1\" >"+zeroten+"</div>\r\n"
			+ "				<div id=\"age2\" >"+tentwenty+"</div>\r\n"
			+ "				<div id=\"age3\" >"+twentythirty+"</div>\r\n"
			+ "				<div id=\"age4\" >"+thirtyfourty+"</div>\r\n"
			+ "				<div id=\"age5\" >"+fourtyfifty+"</div>\r\n"
			+ "				<div id=\"age6\" >"+fiftysixty+"</div>\r\n"
			+ "				<div id=\"age7\" >"+sixtyseventy+"</div>\r\n"
			+ "				<div id=\"age8\" >"+overseventy+"</div>";
		}
	}
	
	@GetMapping("/mall/showCommentPhoto")
	public ResponseEntity<byte[]> showCommentPhoto(@RequestParam("serialId") String serialId) {
		byte[] photo;
		Integer mostExpensiveId = hService.getHistoryIdsBySerialId(serialId);
		if(pService.getProductPhotoById(mostExpensiveId)!=null) {
			photo = pService.getProductPhotoById(mostExpensiveId);
		}else {
			photo = Base64.getDecoder().decode("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");
		}

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		// 回傳值 , header, status
		return new ResponseEntity<byte[]>(photo, header, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping("/mall/showOrder")//查詢購買商品詳情、收件人資訊
	public String showOrder(@RequestParam("serialId")String serialId) {
		List<History> historyList = hService.findHistoryBySerialId(serialId);
		String str1 = "<button style=\"width:80px;height:40px;float:right\" onclick=\"closeOrderList()\">關閉</button><br><div style=\"width:400px;height:250px;margin:auto;\">\r\n"
				+"<table class=\"checkOrderDetail\">\r\n"
				+ "<thead>\r\n"
				+ "<tr>\r\n"
				+ "<th>商品編號</th><th>商品名稱</th><th>價格</th><th>數量</th><th>核對</th>\r\n"
				+ "</tr>\r\n"
				+ "</thead>\r\n"
				+ "<tbody>\r\n"
				;
		String str2 = "";
		String str3 = 
				"</tbody>\r\n"
				+ "</table>"
				+ "</div>"
				+"";
		for(int i=0;i<historyList.size();i++) {
			str2+="<tr>\r\n"+"<td>"+historyList.get(i).getProductId()+"</td><td>"+historyList.get(i).getProductName()+
					"</td><td>"+historyList.get(i).getPrice()+"</td><td>"+historyList.get(i).getAmount()+"</td><td>"+
					"<button id=\""+(serialId+i)+"\" onclick=\"senderCheck(this.id)\" style=\"width:36px;height:34px;border:none;background-color:#F0F0F0\"></button>"+"</td>\r\n"+"</tr>\r\n";
					
		}
		
		return str1+str2+str3;
	}
	
	@ResponseBody
	@PostMapping("/mall/showMyOrder")//查詢購買商品詳情、收件人資訊
	public String showMyOrder(@RequestParam("serialId")String serialId) {
		List<History> historyList = hService.findHistoryBySerialId(serialId);
		String str1 = "<button style=\"width:80px;height:40px;float:right\" onclick=\"closeMyList()\">關閉</button><br><div style=\"width:400px;height:250px;margin:auto;\">\r\n"
				+"<table class=\"checkMyOrderDetail\">\r\n"
				+ "<thead>\r\n"
				+ "<tr>\r\n"
				+ "<th>商品編號</th><th>商品名稱</th><th>價格</th><th>數量</th><th>核對</th>\r\n"
				+ "</tr>\r\n"
				+ "</thead>\r\n"
				+ "<tbody>\r\n"
				;
		String str2 = "";
		String str3 = 
				"</tbody>\r\n"
				+ "</table>"
				+ "</div>"
				+"";
		for(int i=0;i<historyList.size();i++) {
			str2+="<tr>\r\n"+"<td>"+historyList.get(i).getProductId()+"</td><td>"+historyList.get(i).getProductName()+
					"</td><td>"+historyList.get(i).getPrice()+"</td><td>"+historyList.get(i).getAmount()+"</td><td>"+
					"<button id=\""+(serialId+i)+"\" onclick=\"buyerCheck(this.id)\" style=\"width:36px;height:34px;border:none;background-color:#F0F0F0\"></button>"+"</td>\r\n"+"</tr>\r\n";
					
		}
		
		return str1+str2+str3;
	}
	
	@ResponseBody
	@PostMapping("/mall/showRatingPageDetail")//查詢評價商品詳情
	public String showRatingPageDetail(@RequestParam("serialId")String serialId) {
		List<History> historyList = hService.findHistoryBySerialId(serialId);
		String str1 = "<button style=\"width:80px;height:40px;float:right\" onclick=\"closeList()\">關閉</button><br><div style=\"width:400px;height:250px;margin:auto;\">\r\n"
				+"<table class=\"showRatingPageDetail\">\r\n"
				+ "<thead>\r\n"
				+ "<tr>\r\n"
				+ "<th>商品編號</th><th>商品名稱</th><th>價格</th><th>數量</th>\r\n"
				+ "</tr>\r\n"
				+ "</thead>\r\n"
				+ "<tbody>\r\n"
				;
		String str2 = "";
		String str3 = 
				"</tbody>\r\n"
				+ "</table>"
				+ "</div>"
				+"";
		for(int i=0;i<historyList.size();i++) {
			str2+="<tr>\r\n"+"<td>"+historyList.get(i).getProductId()+"</td><td>"+historyList.get(i).getProductName()+
					"</td><td>"+historyList.get(i).getPrice()+"</td><td>"+historyList.get(i).getAmount()+"</td>\r\n"+"</tr>\r\n";
					
		}
		
		return str1+str2+str3;
	}
	
	
	
}
