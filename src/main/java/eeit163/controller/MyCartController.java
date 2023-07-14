package eeit163.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.model.Member;
import eeit163.model.MyCart;
import eeit163.model.Product;
import eeit163.service.MyCartService;
import eeit163.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyCartController {
	@Autowired
	private MyCartService cService;
	@Autowired
	private ProductService pService;
	
	@ResponseBody
	@PostMapping("/mall/addToMyCart")
	public String addToMyCart(@RequestParam("myCartProductId")Integer myCartProductId,
			@RequestParam("amount")Integer amount,HttpSession hs,Model model) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		List<MyCart> cartList = cService.findMyCart(member.getId());
		Integer num = 0;
		for(int i=0;i<cartList.size();i++) {
			num+=cartList.get(i).getAmount();
		}
		if(cService.findAmount(member.getId(), myCartProductId)==null) {
			MyCart myCart = new MyCart();
			Product p = pService.findProductById(myCartProductId);
			myCart.setAmount(amount);
			myCart.setMemberId(member.getId());
			myCart.setMyCartProductId(myCartProductId);
			myCart.setProductName(p.getProductName());
			myCart.setPrice(p.getPrice());
			myCart.setOwnerId(p.getOwnerId());
			if("259200".equals(p.getTimeLimit())) {
				myCart.setType("特賣");
			}
			if("1209600".equals(p.getTimeLimit())) {
				myCart.setType("團購");
			}
			
			
			
			cService.addToMyCart(myCart);
		}else {
			MyCart myCart = cService.findAmount(member.getId(), myCartProductId);
			cService.updateAmount(myCart.getAmount()+amount,member.getId(),myCartProductId);
		}
		model.addAttribute("num", num+amount);
		String cartNum = num+amount+"";
		return cartNum;
	}
	
	@GetMapping("/mall/myCartPage")//購物車
	public String myCart(HttpSession hs,Model model) {	
		Member member = (Member)hs.getAttribute("loggedInMember");
		List<MyCart> myCartList = cService.findMyCart(member.getId());
		ArrayList<Integer> ownerIdList = new ArrayList<Integer>();
		for(int i=0;i<myCartList.size();i++) {
				if(!ownerIdList.contains(myCartList.get(i).getOwnerId())) {
					ownerIdList.add(myCartList.get(i).getOwnerId());
				}
		}
		model.addAttribute("myCartList", myCartList);
		model.addAttribute("ownerIdList", ownerIdList);
		return "mall/myCart";
	}
	
	
	@PostMapping("/mall/checkoutPage")//購物車按下結帳
	public String checkout(@RequestParam("checkout")String[] cartId,Model model) {	
		Integer[] arr2 = Stream.of(cartId).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
		List<MyCart> checkoutList = cService.findCheckoutList(arr2);
		Integer totalPrice = 0;
		String type = "";
		for(int i=0;i<checkoutList.size();i++) {
			totalPrice += (checkoutList.get(i).getAmount())*(checkoutList.get(i).getPrice());
			if(checkoutList.get(i).getType().equals("團購")) {type="團購";};
		}
		//由年月日時分秒+3位隨機數產生流水號
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    	String dateString = formatter.format(currentTime);
    	int x=(int)(Math.random()*900)+100;
    	String serialId = dateString + x;
		model.addAttribute("serialId", serialId);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("checkoutList", checkoutList);
		model.addAttribute("type", type);
		return "mall/checkout";
	}



	
	@ResponseBody
	@PostMapping("/mall/deleteCartProduct")//刪除購物車商品
	public void deleteCartProduct(@RequestParam("cartId")Integer cartId) {	
		 cService.deleteCartProduct(cartId);
	}
	
	@ResponseBody
	@PostMapping("/mall/updateCartNum")//購物車數量
	public String updateCartNum(@RequestParam("amount")Integer amount, @RequestParam("cartId")Integer cartId) {	
		Product p = pService.findProductById(cService.findMyCartByCartId(cartId).getMyCartProductId());
		if(p.getQuantity()<amount) {
			return "商品數量不足，請重新輸入";
		}else {
			cService.updateCartNum(amount, cartId);
			return "";
		}
	}

}
