package eeit163.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Cancel;
import eeit163.model.History;
import eeit163.model.Member;
import eeit163.model.MyCart;
import eeit163.model.MyOrder;
import eeit163.model.Product;
import eeit163.service.CancelService;
import eeit163.service.HistoryService;
import eeit163.service.MemberService;
import eeit163.service.MyCartService;
import eeit163.service.MyOrderService;
import eeit163.service.ProductService;
import eeit163.service.QuestionService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyOrderController {
	@Autowired
	private ProductService pService;
	@Autowired
	private MyCartService cService;
	@Autowired
	private MemberService mService;
	@Autowired
	private QuestionService qService;
	@Autowired
	private MyOrderService oService;
	@Autowired
	private HistoryService hService;
	@Autowired
	private CancelService cancelService;
	
	
	@PostMapping("/mall/makeAnOrder")
	public String makeAnOrder(
			@RequestParam("serialId")String serialId,
			@RequestParam("recipientName")String recipientName,
			@RequestParam("recipientTel")String recipientTel,
			@RequestParam("recipientAddress")String recipientAddress,
			@RequestParam("senderId")Integer[] senderId,
			@RequestParam("totalPrice")Integer totalPrice,
			@RequestParam("paymentMethod")String paymentMethod,
			@RequestParam("products")Integer[] products,
			@RequestParam("amount")Integer[] amount,
			Model model, HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		MyOrder order = new MyOrder();
		String str = "";
		order.setSerialId(serialId);//建立訂單內容
		order.setRecipientName(recipientName);
		order.setRecipientTel(recipientTel);
		order.setRecipientAddress(recipientAddress);
		order.setSenderId(senderId[0]);
		order.setBuyerId(member.getId());
		order.setTotalPrice(totalPrice);
		order.setPaymentMethod(paymentMethod);
		order.setOrderDate(new Date());
		double score = member.getBuyScore();
		double times = member.getBuyScoreTimes();	
		if(times==0) {
			times=1;
		}
		double Rating = Math.round((score/times)*10.0)/10.0;
		order.setBuyerRating(Rating);
		if("信用卡".equals(paymentMethod)) {
			order.setPayStatus("已付款");
			order.setOrderStatus("待出貨");
			model.addAttribute("method", "creditCard");
		}
		if("貨到付款".equals(paymentMethod)) {
			order.setOrderStatus("待出貨");
			model.addAttribute("method", "cash");
		}
		if("銀行轉帳".equals(paymentMethod)) {
			model.addAttribute("method", "bank");
		}
		for(int i=0;i<products.length;i++) {
			str+=(products[i]+",");
			Integer productNum = pService.getProductById(products[i]).getQuantity();
			Integer buyNum = amount[i];
			pService.updateQuantity(productNum-buyNum,products[i]);//從Product消除購買數量
			pService.updateSoldById(pService.getProductById(products[i]).getSold()+buyNum,products[i]);//更新銷售數量
			
			if("".equals(pService.getProductById(products[i]).getBuyerId())) {//若BuyerId原本是空字串不能加逗號
				pService.updateBuyerId(""+member.getId(),products[i]);
			}else {
				pService.updateBuyerId(pService.getProductById(products[i]).getBuyerId()+","+member.getId(),products[i]);
			}
		}
		order.setProducts(str);
		if("".equals(member.getPurchaseHistory())) {//若PurchaseHistory原本是空字串不能加逗號
			mService.updatePurchaseHistory(str, member.getId());
		}else {
			mService.updatePurchaseHistory(member.getPurchaseHistory()+","+str, member.getId());
		}
		oService.makeAnOrder(order);
		
		List<MyCart> purchased = cService.findMyPurchased(member.getId(),senderId[0]);//從MyCart複製到HistoryTable
		for(int i=0;i<purchased.size();i++) {
			History history = new History();
			history.setAmount(purchased.get(i).getAmount());
			history.setMemberId(purchased.get(i).getMemberId());
			history.setProductId(purchased.get(i).getMyCartProductId());
			history.setOwnerId(purchased.get(i).getOwnerId());
			history.setPrice(purchased.get(i).getPrice());
			history.setProductName(purchased.get(i).getProductName());
			history.setSerialId(serialId);
			history.setOrderDate(order.getOrderDate());
			hService.insertHistory(history);
		}
		cService.clearMyCart(member.getId(),senderId[0]);//從MyCart消除購買商品
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("serialId", serialId);
		return "/mall/payment";
	}
	
	@ResponseBody
	@PostMapping("/mall/ship")
	public String ship(@RequestParam("orderId")Integer orderId) {
		oService.updateOrderStatusShipped(orderId);
		return "";
	}
	
	@ResponseBody
	@PostMapping("/mall/receive")
	public String receive(@RequestParam("orderId")Integer orderId) {
		MyOrder order = oService.findOrderById(orderId);
		if("貨到付款".equals(order.getPaymentMethod())) {
			oService.updatePayStatus(orderId);
		}
		oService.updateOrderStatusReceived(orderId);
		return "";
	}
	
	
	
	@ResponseBody
	@PostMapping("/mall/AdminCheckPay")
	public String AdminCheckPay(@RequestParam("orderId")Integer orderId) {
		oService.updatePayStatus(orderId);
		return "";
	}
	
	@ResponseBody
	@PostMapping("/mall/AdminCheckReceived")
	public String AdminCheckReceived(@RequestParam("orderId")Integer orderId) {
		oService.updateOrderStatus(orderId);
		return "";
	}
	
	@ResponseBody
	@PostMapping("/mall/apply")//申請取消
	public String apply(@RequestParam("serialId")String serialId) {
		oService.updateCancellationApply(serialId);
		return "";
	}
	
	@ResponseBody
	@PostMapping("/mall/agree")//同意取消
	public String agree(@RequestParam("serialId")String serialId) {
		oService.updateOrderStatusCancelled(new Date(), serialId);
		
		List<History> historyList = hService.findHistoryBySerialId(serialId);
		for(int i=0;i<historyList.size();i++) {
			History h = historyList.get(i);
			Cancel cancel = new Cancel();
			cancel.setAmount(h.getAmount());
			cancel.setCancelledDate(new Date());
			cancel.setMemberId(h.getMemberId());
			cancel.setOrderDate(h.getOrderDate());
			cancel.setOwnerId(h.getOwnerId());
			cancel.setPrice(h.getPrice());
			cancel.setProductId(h.getProductId());
			cancel.setProductName(h.getProductName());
			cancel.setSerialId(h.getSerialId());
			cancelService.addCancelledOrder(cancel);
			Product p = pService.findProductById(h.getProductId());
			pService.updateSoldById(p.getSold()-h.getAmount(), h.getProductId());//銷售數量恢復
			pService.updateProductQuantityById(p.getQuantity()+h.getAmount(), h.getProductId());//商品數量恢復
			
			
		}
		hService.deleteCancelledHistory(serialId);
		return "";
	}

}
