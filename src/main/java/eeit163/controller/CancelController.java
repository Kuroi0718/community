package eeit163.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Cancel;
import eeit163.model.History;
import eeit163.service.CancelService;
import eeit163.service.MemberService;
import eeit163.service.ProductService;

@Controller
public class CancelController {
	@Autowired
	private CancelService cancelService;
	@Autowired
	private MemberService mService;
	@Autowired
	private ProductService pService;
	
	@ResponseBody
	@PostMapping("/mall/showCancelledOrder")
	public String showCancelledOrder(@RequestParam("serialId")String serialId) {
		List<Cancel> cancelList = cancelService.findCancelBySerialId(serialId);
		String str1 = "<button style=\"width:80px;height:40px;float:right\" onclick=\"closeCancelledList()\">關閉</button><br><div style=\"width:400px;height:250px;margin:auto;\">\r\n"
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
		for(int i=0;i<cancelList.size();i++) {
			str2+="<tr>\r\n"+"<td>"+cancelList.get(i).getProductId()+"</td><td>"+cancelList.get(i).getProductName()+
					"</td><td>"+cancelList.get(i).getPrice()+"</td><td>"+cancelList.get(i).getAmount()+"</td><td>"+
					"<button id=\""+(serialId+i)+"\" onclick=\"senderCheck(this.id)\" style=\"width:36px;height:34px;border:none;background-color:#F0F0F0\"></button>"+"</td>\r\n"+"</tr>\r\n";
					
		}
		
		return str1+str2+str3;
	}
	
	@ResponseBody
	@PostMapping("/mall/showMyCancelledOrder")
	public String showMyOrder(@RequestParam("serialId")String serialId) {
		List<Cancel> cancelList = cancelService.findCancelBySerialId(serialId);
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
		for(int i=0;i<cancelList.size();i++) {
			str2+="<tr>\r\n"+"<td>"+cancelList.get(i).getProductId()+"</td><td>"+cancelList.get(i).getProductName()+
					"</td><td>"+cancelList.get(i).getPrice()+"</td><td>"+cancelList.get(i).getAmount()+"</td><td>"+
					"<button id=\""+(serialId+i)+"\" onclick=\"buyerCheck(this.id)\" style=\"width:36px;height:34px;border:none;background-color:#F0F0F0\"></button>"+"</td>\r\n"+"</tr>\r\n";
					
		}
		
		return str1+str2+str3;
	}
	
}
