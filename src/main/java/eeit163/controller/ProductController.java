package eeit163.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
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

import eeit163.model.Comment;
import eeit163.model.History;
import eeit163.model.Member;
import eeit163.model.MyCart;
import eeit163.model.MyOrder;
import eeit163.model.Product;
import eeit163.model.Question;
import eeit163.service.CancelService;
import eeit163.service.CommentService;
import eeit163.service.HistoryService;
import eeit163.service.MemberService;
import eeit163.service.MyCartService;
import eeit163.service.MyOrderService;
import eeit163.service.ProductService;
import eeit163.service.QuestionService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
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
	private CommentService commentService;

	
	@GetMapping("/mall/shopPage")//商城
	public String showProducts(Model model, HttpSession hs) {
		List<Product> productList = pService.getAllProducts("noLimit");
		model.addAttribute("productList", productList);
		Member member = (Member)hs.getAttribute("loggedInMember");
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		return "mall/shop";
	}
	
	@GetMapping("/mall/shopPageNav")//商城nav分類
	public String shopPageNav(Model model, HttpSession hs,
			@RequestParam("detail")String detail, @RequestParam("category")String category) {
		List<Product> productList = pService.getAllProductsByCategoryAndDetail(detail,category);
		System.out.println(productList.get(0).getProductName());
		Member member = (Member)hs.getAttribute("loggedInMember");
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		model.addAttribute("productList", productList);
		return "mall/shop";
	}
	
	
	
/////////////////////限時商品-開始//////////////
	@GetMapping("/mall/limitPage")
	public String limit(Model model, HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		return "mall/limit";
	}
//------------------------------------------
	@GetMapping("/mall/flashPage")//特賣
	public String flash(Model model, HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		List<Product> flashList = pService.getAllProducts("259200");
		model.addAttribute("flashList", flashList);
		return "mall/flash";
	}
	@ResponseBody
	@PostMapping("/mall/flashGetTime")//特賣
	public String flashGetTime() {
		List<Product> flashList = pService.getAllProducts("259200");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(flashList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	@ResponseBody
	@PostMapping("/mall/flashGetTime2")//特賣
	public String flashGetTime2() {
		List<Product> flashList = pService.getAllProducts("259200");
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<flashList.size();i++) {
			list.add(flashList.get(i).getExpirationDateString());
		}
		try {
			return objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	@GetMapping("/mall/bidPage")//競標
	public String bid(Model model, HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		List<Product> bidList = pService.getAllProducts("604800");
		model.addAttribute("bidList", bidList);
		return "mall/bid";
	}
	
	@ResponseBody
	@PostMapping("/mall/bidGetTime")//競標
	public String bidGetTime() {
		List<Product> bidList = pService.getAllProducts("604800");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(bidList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	@ResponseBody
	@PostMapping("/mall/bidGetTime2")//競標
	public String bidGetTime2() {
		List<Product> bidList = pService.getAllProducts("604800");
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<bidList.size();i++) {
			list.add(bidList.get(i).getExpirationDateString());
		}
		try {
			return objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	@GetMapping("/mall/groupPage")//團購
	public String group(Model model, HttpSession hs) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		
		List<Product> groupList = pService.getAllProducts("1209600");		
		model.addAttribute("groupList", groupList);
	
		
		return "mall/group";
	}
	
	
	
	@ResponseBody
	@PostMapping("/mall/groupGetTime")//團購
	public String groupGetTime() {
		List<Product> groupList = pService.getAllProducts("1209600");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(groupList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	@ResponseBody
	@PostMapping("/mall/groupGetTime2")//團購
	public String groupGetTime2() {
		List<Product> groupList = pService.getAllProducts("1209600");
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<groupList.size();i++) {
			list.add(groupList.get(i).getExpirationDateString());
		}
		try {
			return objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
		
	}
/////////////////////限時商品-結束//////////////
	
/////////////////////個人賣場-開始//////////////
	@GetMapping("/mall/myShopPage")
	public String myShop(HttpSession hs,@RequestParam("id") Integer ownertId,Model model) {
		List<Product> myList = pService.getAllMyProducts(ownertId);
		List<Product> myDelistedList = pService.getAllMyDelistedProducts(ownertId);
		model.addAttribute("myList", myList);
		model.addAttribute("myDelistedList", myDelistedList);
		Member member = (Member)hs.getAttribute("loggedInMember");
		List<Question> unansweredList = qService.findUnansweredQuestions(member.getId());
		model.addAttribute("unansweredList", unansweredList);
		
		
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		
		//////賣家管理訂單/////
		List<MyOrder> toPayList = oService.findToPayList(member.getId());
		model.addAttribute("toPayList", toPayList);
		List<MyOrder> toShipList = oService.findToShipList(member.getId());
		model.addAttribute("toShipList", toShipList);
		List<MyOrder> shippedList = oService.findShippedList(member.getId());
		model.addAttribute("shippedList", shippedList);
		List<MyOrder> receivedList = oService.findReceivedList(member.getId());
		model.addAttribute("receivedList", receivedList);
		List<MyOrder> completedList = oService.findCompletedList(member.getId());
		model.addAttribute("completedList", completedList);
		List<MyOrder> cancelledList = oService.findCancelledList(member.getId());
		model.addAttribute("cancelledList", cancelledList);
		//////賣家管理訂單/////
		
		//賣場評價
		List<Comment> myShopCommentList = commentService.findMyComments(member.getId(),"賣家評價");//author是我的評價&&是賣家評價
		model.addAttribute("myShopCommentList", myShopCommentList);
		List<Comment> shopCommentList = commentService.findComments(member.getId(),"買家評價");//ratingTarget是我的評價&&是買家評價
		model.addAttribute("shopCommentList", shopCommentList);
		double score = member.getSellScore();
		double times = member.getSellScoreTimes();	
		double rating = Math.round((score/times)*10.0)/10.0;
		model.addAttribute("rating", rating);
		
		
		
		
		return "mall/myShop";
	}
//------------------------------------------
	@GetMapping("/mall/publishPage")//賣東西
	public String publish() {
		return "mall/publish";
	}
	@GetMapping("/mall/analysisPage")//客群分析
	public String analysis() {
		return "mall/analysis";
	}
	@GetMapping("/mall/ratingPage")//賣場評價
	public String rating() {
		return "mall/rating";
	}
	@GetMapping("/mall/qAndAPage")//問與答
	public String qAndA() {
		return "mall/qAndA";
	}
	@GetMapping("/mall/orderPage")//賣場訂單
	public String order() {
		return "mall/order";
	}
/////////////////////個人賣場-結束//////////////
	
/////////////////////我的-開始/////////////////
	@GetMapping("/mall/mePage")
	public String me(HttpSession hs,Model model) {
		Member member = (Member)hs.getAttribute("loggedInMember");
		if(member!=null) {
			List<MyCart> cartList = cService.findMyCart(member.getId());
			Integer num = 0;
			for(int i=0;i<cartList.size();i++) {
				num+=cartList.get(i).getAmount();
			}
			model.addAttribute("num", num);
		}
		
		List<Product> winlist = pService.findWinning(member.getId(),new Date());//************
		//我是winner && expirationDate!=null && expirationDate<=現在
		//win list要加入購物車然後setExpirationDate=""
		for(int i=0;i<winlist.size();i++) {
			MyCart myCart = new MyCart();
			myCart.setAmount(1);
			myCart.setMemberId(member.getId());
			myCart.setMyCartProductId(winlist.get(i).getProductId());
			myCart.setProductName(winlist.get(i).getProductName());
			myCart.setPrice(winlist.get(i).getLatestPrice());
			myCart.setOwnerId(winlist.get(i).getOwnerId());
			myCart.setType("競標");
			cService.addToMyCart(myCart);
			pService.updateExpirationDateEmptyString(winlist.get(i).getProductId());
		}
		
		
		List<MyCart> myCartList = cService.findMyCart(member.getId());
		model.addAttribute("myCartList", myCartList);
		///
		Integer[] arr2;
		if("".equals(mService.findById(member.getId()).getMyLikes())) {
			arr2 = new Integer[0];
		}else {
			String[] myLikes = mService.findById(member.getId()).getMyLikes().split(",");
			arr2 = Stream.of(myLikes).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
		}
		
		List<Product> likeList = pService.findMyLikes(arr2);
		model.addAttribute("likeList", likeList);
		///
		Integer[] arr3;
		if("".equals(mService.findById(member.getId()).getRecord())) {
			arr3 = new Integer[0];
		}else {
			String[] myRecord = mService.findById(member.getId()).getRecord().split(",");
			arr3 = Stream.of(myRecord).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
		}
		
		List<Product> recordList = pService.findMyRecord(arr3);
		model.addAttribute("recordList", recordList);
		
		ArrayList<Integer> ownerIdList = new ArrayList<Integer>();
		for(int i=0;i<myCartList.size();i++) {
				if(!ownerIdList.contains(myCartList.get(i).getOwnerId())) {
					ownerIdList.add(myCartList.get(i).getOwnerId());
				}
		}
		model.addAttribute("ownerIdList", ownerIdList);
		
		//////買家查詢訂單/////
		List<MyOrder> buyerToPayList = oService.findBuyerToPayList(member.getId());
		model.addAttribute("buyerToPayList", buyerToPayList);
		List<MyOrder> buyerToShipList = oService.findBuyerToShipList(member.getId());
		model.addAttribute("buyerToShipList", buyerToShipList);
		List<MyOrder> buyerShippedList = oService.findBuyerShippedList(member.getId());
		model.addAttribute("buyerShippedList", buyerShippedList);
		List<MyOrder> buyerReceivedList = oService.findBuyerReceivedList(member.getId());
		model.addAttribute("buyerReceivedList", buyerReceivedList);
		List<MyOrder> buyerCompletedList = oService.findBuyerCompletedList(member.getId());
		model.addAttribute("buyerCompletedList", buyerCompletedList);
		List<MyOrder> buyerCancelledList = oService.findBuyerCancelledList(member.getId());
		model.addAttribute("buyerCancelledList", buyerCancelledList);
		//////買家查詢訂單/////
		
		
		
		//我的評價
		List<Comment> myCommentList = commentService.findMyComments(member.getId(),"買家評價");//author是我的評價&&是買家評價
		model.addAttribute("myCommentList", myCommentList);
		List<Comment> commentList = commentService.findComments(member.getId(),"賣家評價");//ratingTarget是我的評價&&是賣家評價
		model.addAttribute("commentList", commentList);
		double score = member.getBuyScore();
		double times = member.getBuyScoreTimes();	
		double myRating = Math.round((score/times)*10.0)/10.0;
		model.addAttribute("myRating", myRating);
		
		List<MyCart> carList = cService.findMyCart(member.getId());
		List<Product> cartProductQuantityList;
		if(carList.size()==0) {
			cartProductQuantityList = new ArrayList<Product>(0);
		}else {
		String cartStr = "";
		for(int i=0;i<carList.size();i++) {
			cartStr += (carList.get(i).getMyCartProductId()+",");
		}
		String[] cartId= cartStr.split(",");
		Integer[] arrCartId = Stream.of(cartId).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
		cartProductQuantityList = pService.findProductsByIdArray(arrCartId);
		}
		model.addAttribute("cartProductQuantityList", cartProductQuantityList);
		
		model.addAttribute("now", new Date());
		
		return "mall/me";
	}
	
	
//------------------------------------------
	@GetMapping("/mall/myOrderPage")//訂單查詢
	public String myOrder() {
		return "mall/myOrder";
	}
	@GetMapping("/mall/myRatingPage")//我的評價
	public String myRating() {
		return "mall/myRating";
	}
	@GetMapping("/mall/myLikesPage")//喜愛商品
	public String myLikes() {
		return "mall/myLikes";
	}
	@GetMapping("/mall/myRecordPage")//最近瀏覽
	public String myRecord() {
		return "mall/myRecord";
	}
	
/////////////////////我的-結束/////////////////
	
	
	

	
	@ResponseBody
	@PostMapping("/mall/addProduct")
	public String addProduct(
			@RequestParam("productPhoto") MultipartFile productPhoto,
			@RequestParam("productName") String productName,
			@RequestParam("category") String category,
			@RequestParam("detail") String detail,
			@RequestParam("price") Integer price,
			@RequestParam("quantity") Integer quantity,
			@RequestParam("status") Integer status,
			@RequestParam("info") String info,
			@RequestParam("bargain") Integer bargain,
			@RequestParam("timeLimit") String timeLimit,
			HttpSession hs) {
		try {
			Member member = (Member)hs.getAttribute("loggedInMember");
			Product product = new Product();
			Date date = new Date();
			product.setProductPhoto(productPhoto.getBytes());
			product.setProductName(productName);
			product.setCategory(category);
			product.setDetail(detail);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setStatus(status);
			product.setInfo(info);
			product.setBargain(bargain);
			product.setTimeLimit(timeLimit);
			product.setOwnerId(member.getId());
			product.setReleaseDate(date);
			product.setLatestPrice(price);
			Calendar calendar = Calendar.getInstance();
			if(timeLimit.equals("259200")) {
				calendar.add(Calendar.DAY_OF_MONTH, 3);
				product.setExpirationDate(calendar.getTime());
			};
			if(timeLimit.equals("604800")) {
				calendar.add(Calendar.DAY_OF_MONTH, 7);
				product.setExpirationDate(calendar.getTime());
			};
			if(timeLimit.equals("1209600")) {
				calendar.add(Calendar.DAY_OF_MONTH, 14);
				product.setExpirationDate(calendar.getTime());
			};
			pService.addProduct(product);
			return "上架成功";
		}catch(IOException e) {
			e.printStackTrace();
			return "上架失敗";
		}
		}
		
		@GetMapping("/mall/showPhoto")
		public ResponseEntity<byte[]> showPhoto(@RequestParam("productId") Integer productId) {
			byte[] photo = pService.getProductPhotoById(productId);

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.IMAGE_PNG);
			// 回傳值 , header, status
			return new ResponseEntity<byte[]>(photo, header, HttpStatus.OK);
			
		}
		
		@ResponseBody
		@PostMapping("/mall/categoryDetail")
		public String showDetail(@RequestParam("category") String category) {
			String str = "";
			if("服飾".equals(category)) {str="<option value=\"\" selected></option>\r\n"
					+ "			<option>女裝</option>\r\n"
					+ "			<option>機能服/運動服/配件</option>\r\n"
					+ "			<option>內衣/睡衣/塑身衣</option>\r\n"
					+ "			<option>襪子</option>\r\n"
					+ "			<option>童裝配件/孕期服飾</option>\r\n"
					+ "			<option>男裝</option>\r\n"
					+ "			<option>其他</option>\r\n";};
			if("鞋包配件".equals(category)) {str="<option value=\"\" selected></option>\r\n"
					+ "			<option>鞋子</option>\r\n"
					+ "			<option>流行包</option>\r\n"
					+ "			<option>國際精品/飾品/配件</option>\r\n"
					+ "			<option>眼鏡</option>\r\n"
					+ "			<option>髮飾/穿搭配件</option>\r\n"
					+ "			<option>其他</option>\r\n";};
			if("休閒生活".equals(category)) {str="<option value=\"\" selected></option>\r\n"
					+ "			<option>旅行用品專區</option>\r\n"
					+ "			<option>登山健行</option>\r\n"
					+ "			<option>戶外休閒</option>\r\n"
					+ "			<option>瑜珈用品</option>\r\n"
					+ "			<option>運動健身</option>\r\n"
					+ "			<option>汽車用品</option>\r\n"
					+ "			<option>機車/自行車</option>\r\n"
					+ "			<option>其他</option>\r\n";};
			if("3C".equals(category)) {str="<option value=\"\" selected></option>\r\n"
					+ "			<option>喇叭/揚聲器/耳機</option>\r\n"
					+ "			<option>儲存/隨身碟/記憶卡</option>\r\n"
					+ "			<option>手機/手機周邊配件</option>\r\n"
					+ "			<option>相機/攝影機</option>\r\n"
					+ "			<option>繪圖/手寫板</option>\r\n"
					+ "			<option>滑鼠鍵盤</option>\r\n"
					+ "			<option>筆電/桌機/平板電腦</option>\r\n"
					+ "			<option>其他</option>\r\n";};
			if("寵物生活".equals(category)) {str="<option value=\"\" selected></option>\r\n"
					+ "			<option>狗/貓主食</option>\r\n"
					+ "			<option>狗/貓零食</option>\r\n"
					+ "			<option>寵物居家用品</option>\r\n"
					+ "			<option>寵物外出用品</option>\r\n"
					+ "			<option>寵物營養保健品</option>\r\n"
					+ "			<option>其他</option>\r\n";};
					
			if("其他".equals(category)) {str="";};
			 
			return str;
			
//			<select style="width: 7vw; height: 3vh">
//			<option></option>
//			<option></option>
//			<option></option>
//			<option></option>
//			<option></option>
//			<option></option>
//			</select>
		}
		
		
		
//未完成	@ResponseBody
		@PostMapping("/mall/typeDetail")
		public String typeDetail(@RequestParam("type") String type) {
			String str = "";
			if("noLimit".equals(type)) {str="";};
			if("259200".equals(type)) {str="特賣商品不可議價";};
			if("604800".equals(type)) {str="競標商品不可議價";};
			if("1209600".equals(type)) {str="團購商品不可議價";};
			 
			return str;
		}
		
		
		@GetMapping("/mall/productPage")//單頁商品頁
		public String product(@RequestParam("productId") Integer productId, Model model) {
			Product product = pService.findProductById(productId);
			List<Question> questionList = qService.findProductQuestions(productId);
			model.addAttribute("questionList", questionList);
			model.addAttribute("product", product);
			
			
			
			String serialId = "";
			List<History> historyList = hService.findHistoryByProductId(productId);
			for(int i=0;i<historyList.size();i++) {
				serialId+=(historyList.get(i).getSerialId()+",");
			}
			String[] serialIdArray = serialId.split(",");
			List<Comment> commentList = commentService.findCommentsBySerialIdArray(serialIdArray);
			model.addAttribute("commentList", commentList);
	
			return "mall/product";
		}
		
		@ResponseBody
		@PostMapping("/mall/preEdit")
		public String preEdit(@RequestParam("productId") Integer productId, Model model) {
			Product editProduct = pService.findProductById(productId);
			model.addAttribute("editProduct", editProduct);
			return "";
		}
		
		@ResponseBody//*********************************
		@PostMapping("/mall/editMyProduct")//編輯我的商品
		public String editMyProduct(
				@RequestParam("productId") String pid,
				@RequestParam(value="productPhoto", required=false) MultipartFile productPhoto,
				@RequestParam(value="productName", required=false) String productName,
				@RequestParam(value="category", required=false) String category,
				@RequestParam(value="price", required=false) Integer price,
				@RequestParam(value="quantity", required=false) Integer quantity,
				@RequestParam(value="status", required=false) Integer status,
				@RequestParam(value="info", required=false) String info,
				@RequestParam(value="bargain", required=false) Integer bargain,
				@RequestParam(value="timeLimit", required=false) String timeLimit,
				HttpSession hs) {
			Integer productId = Integer.parseInt(pid);
			Product p = pService.findProductById(productId);
			try{
				if(productPhoto.getSize() != 0) {
					pService.updateProductPhoto(productPhoto.getBytes(), productId);
				}else{
					pService.updateProductPhoto(p.getProductPhoto(), productId);
				};
				if(productName != null) {
					pService.updateProductName(productName, productId);
				};
				if(category != null) {
					pService.updateCategory(category, productId);
				};
				if(price != null) {
					pService.updatePrice(price, productId);
				};
				if(quantity != null) {
					pService.updateQuantity(quantity, productId);
				};
				if(status != null) {
					pService.updateStatus(status, productId);
				};
				if(info != null) {
					pService.updateInfo(info, productId);
				};
				if(bargain != null) {
					pService.updateBargain(bargain, productId);
				};
				if(timeLimit != null) {
					pService.updateTimeLimit(timeLimit, productId);
				};
				
			}catch(IOException e) {
				e.printStackTrace();
				return "";
			}
			
			return "";
		}
		
		
		
		@ResponseBody
		@PostMapping("/mall/deleteMyProduct")//刪除商品
		public String deleteMyProduct(@RequestParam("productId")Integer productId) {	
			 pService.deleteMyProduct(productId);
			 return "";
		}
		
		@ResponseBody
		@PostMapping("/mall/delistMyProduct")//下架我的商品
		public String delistMyProduct(@RequestParam("productId")Integer productId) {
			cService.deleteCartProductByProductId(productId);
			 pService.delistMyProduct(productId);
			 return "";
		}
		
//		@ResponseBody
//		@PostMapping("/mall/getPublished")
//		public String getPublished(HttpSession hs, Model model) {
//			Member member = (Member)hs.getAttribute("loggedInMember");
//			List<Product> myList = pService.getAllMyProducts(member.getId());
//			model.addAttribute("myList", myList);
//			 return "";
//		}
		
		@ResponseBody
		@PostMapping("/mall/publishMyProduct")//上架我的商品
		public String publishMyProduct(@RequestParam("productId")Integer productId) {
			pService.updateProductReleaseDate(new Date(), productId);
			String str = pService.findProductById(productId).getTimeLimit();
			Calendar calendar = Calendar.getInstance();
			if(str.equals("259200")) {
				calendar.add(Calendar.DAY_OF_MONTH, 3);
				pService.updateProductExpirationDate(calendar.getTime(), productId);
			};
			if(str.equals("604800")) {
				calendar.add(Calendar.DAY_OF_MONTH, 7);
				pService.updateProductExpirationDate(calendar.getTime(), productId);
			};
			if(str.equals("1209600")) {
				calendar.add(Calendar.DAY_OF_MONTH, 14);
				pService.updateProductExpirationDate(calendar.getTime(), productId);
			};
			 pService.publishMyProduct(productId);
			 return "";
		}
		
	/*	@ResponseBody
		@PostMapping("/mall/time")
		public String countdown(@RequestParam("productId") Integer productId) {
			Product product = pService.findProductById(productId);
			Date exp = product.getExpirationDate();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String dateToStr = dateFormat.format(exp);
			
			return dateToStr;
		}
		*/
		
		@PostMapping("/mall/searchProduct")
		public String searchProduct(
				@RequestParam(value="category", required=false) String category,
				@RequestParam(value="detail", required=false) String detail,
				@RequestParam(value="price1", required=false) Integer price1,
				@RequestParam(value="price2", required=false) Integer price2,
				@RequestParam(value="status", required=false) Integer status,
				@RequestParam(value="bargain", required=false) Integer bargain,
				Model model, HttpSession hs) {
			String str = "";		
			String result1 = "";
			String result2 = "";
			String result3 = "";
			String result4 = "";
			String[] strs;
			String[] strs2;
			String[] strs3;
			String[] strs4;
			String[] intersection;
			if(!"".equals(category)) {
				List<Integer> searchCategoryList = pService.searchCategory(category);
				for(int i=0;i<searchCategoryList.size();i++) {
					str+=(","+searchCategoryList.get(i));
				}
				strs = str.split(",");
			}else{
				List<Integer> allProductIdList = pService.findAllProductId();
				for(int i=0;i<allProductIdList.size();i++) {
					str+=(","+allProductIdList.get(i));
				}
				strs = str.split(",");
			};
			
			
			if(!"".equals(detail)) {
				List<Integer> searchDetailList = pService.searchDetail(detail);
				for(int i=0;i<strs.length;i++) {
					for(int j=0;j<searchDetailList.size();j++) {
						if(strs[i].equals(""+searchDetailList.get(j))) {
							result1+=(","+searchDetailList.get(j));
						}
					}
				}
				strs2 = result1.split(",");	
			}else{
				strs2 = strs;
			};
			
			if(price1!=null && price2!=null) {
				List<Integer> searchPriceList = pService.searchPrice(price1, price2);
				for(int i=0;i<strs2.length;i++) {
					for(int j=0;j<searchPriceList.size();j++) {
						if(strs2[i].equals(""+searchPriceList.get(j))) {
							result2+=(","+searchPriceList.get(j));
						}
					}
				}
				strs3 = result2.split(",");	
			}else{
				strs3 = strs2;
			};
			
			if(status!=null) {
				List<Integer> searchStatusList = pService.searchStatus(status);
				for(int i=0;i<strs3.length;i++) {
					for(int j=0;j<searchStatusList.size();j++) {
						if(strs3[i].equals(""+searchStatusList.get(j))) {
							result3+=(","+searchStatusList.get(j));
						}
					}
				}
				strs4 = result3.split(",");
			}else{
				strs4 = strs3;
			};
			
			if(bargain!=null) {
				List<Integer> searchBargainList = pService.searchBargain(bargain);
				for(int i=0;i<strs4.length;i++) {
					for(int j=0;j<searchBargainList.size();j++) {
						if(strs4[i].equals(""+searchBargainList.get(j))) {
							result4+=(","+searchBargainList.get(j));
						}
					}
				}
				intersection = result4.split(",");
			}else{
				intersection = strs4;
			};
			
			List<Product> searchNoLimitList = pService.searchNoLimit(intersection);//productId array
			model.addAttribute("searchNoLimitList",searchNoLimitList);
			//***********************************************************
			Member member = (Member)hs.getAttribute("loggedInMember");
			if(member!=null) {
				List<MyCart> cartList = cService.findMyCart(member.getId());
				Integer num = 0;
				for(int i=0;i<cartList.size();i++) {
					num+=cartList.get(i).getAmount();
				}
				model.addAttribute("num", num);
			}
			return "mall/shop";
		}
		
		
		
//		按讚&紀錄
//		Member member = (Member)hs.getAttribute("loggedInMember");
//		String myCart = mService.findMyCart(member.getId());
//		String[] myCartArray= myCart.split(",");
//        Integer[] arr2 = Stream.of(myCartArray).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
//		List<Product> myCartList = pService.findMyCartProducts(arr2);
//		model.addAttribute("myCartList", myCartList);	
		
		
		@ResponseBody
		@PostMapping("/mall/biddingProduct")//直接出價nonAuto
		public String biddingProduct(@RequestParam("productId")Integer productId,
				@RequestParam("buyPrice")Integer buyPrice, HttpSession hs) {
			Product p = pService.findProductById(productId);
			Member member = (Member)hs.getAttribute("loggedInMember");
			
			
			if("nonAuto".equals(p.getWinnerType())||"".equals(p.getWinnerType())) {//both nonAuto or you are first
				pService.updateLatestPrice(buyPrice, productId);
				pService.updateWinner(member.getId(), productId);
				pService.updateWinnerTypeMaxPlus("nonAuto",0,0,productId);
				return ""+buyPrice;//latest price
			};
			if("auto".equals(p.getWinnerType())) {//winner is auto
				if(buyPrice>p.getWinnerMax()) {
					pService.updateLatestPrice(buyPrice, productId);
					pService.updateWinner(member.getId(), productId);
					pService.updateWinnerTypeMaxPlus("nonAuto",0,0,productId);
					return ""+buyPrice;//latest price
				}else {
					if(buyPrice+p.getWinnerPlus()<=p.getWinnerMax()) {
						pService.updateLatestPrice(buyPrice+p.getWinnerPlus(), productId);
						return ""+(buyPrice+p.getWinnerPlus());//latest price
					}else {
						pService.updateLatestPrice(p.getWinnerMax(), productId);
						return ""+p.getWinnerMax();//latest price
					}
				}
			}
			return "";
		}
		
		
		@ResponseBody
		@PostMapping("/mall/biddingProductAuto")//自動出價auto
		public String biddingProductAuto(@RequestParam("productId")Integer productId,
				@RequestParam("max")Integer max,@RequestParam("plus")Integer plus, HttpSession hs) {
			Product p = pService.findProductById(productId);
			Member member = (Member)hs.getAttribute("loggedInMember");

			if("".equals(p.getWinnerType())) {
				
				pService.updateWinner(member.getId(), productId);
				pService.updateWinnerTypeMaxPlus("auto",max,plus,productId);
				return ""+(p.getLatestPrice()+plus);//latest price
			};
			
			if("nonAuto".equals(p.getWinnerType())) {
				pService.updateLatestPrice(p.getLatestPrice()+plus, productId);
				pService.updateWinner(member.getId(), productId);
				pService.updateWinnerTypeMaxPlus("auto",max,plus,productId);
				return ""+(p.getLatestPrice()+plus);//latest price
			};
			if("auto".equals(p.getWinnerType())) {//both auto
				if(max>p.getWinnerMax()) {
					if(p.getWinnerMax()+plus>max) {
						pService.updateLatestPrice(max, productId);
						pService.updateWinner(member.getId(), productId);
						pService.updateWinnerTypeMaxPlus("auto",max,plus,productId);
						return ""+max;//latest price
					}else {
						pService.updateLatestPrice(p.getWinnerMax()+plus, productId);
						pService.updateWinner(member.getId(), productId);
						pService.updateWinnerTypeMaxPlus("auto",max,plus,productId);
						return ""+(p.getWinnerMax()+plus);//latest price
					}
				}else {
					if(max+p.getWinnerPlus()>p.getWinnerMax()) {
						pService.updateLatestPrice(p.getWinnerMax(), productId);
						return ""+p.getWinnerMax();//latest price
					}else {
						pService.updateLatestPrice(max+p.getWinnerPlus(), productId);
						return ""+(max+p.getWinnerPlus());//latest price
					}
				}
			}
			return "";
		}
		
		@ResponseBody
		@PostMapping("/mall/numberTest")
		public String numberTest(@RequestParam("inputId")Integer inputId,
				@RequestParam("inputNumber")Integer inputNumber) {
			pService.updateProductQuantityById(inputNumber, inputId);
			return "";
		}
		
		@ResponseBody
		@PostMapping("/mall/timeLimitTest")
		public String timeLimitTest(@RequestParam("expId")Integer inputId) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			pService.updateProductExpirationDate(calendar.getTime(),inputId);
			return "";
		}
		
		@ResponseBody
		@PostMapping("/mall/banAndDelist")//管理員下架禁賣商品
		public String banAndDelist(@RequestParam("productId")Integer productId) {
			cService.deleteCartProductByProductId(productId);
			 pService.banAndDelistMyProduct(productId);
			 
			 return "";
		}
		
//		@ResponseBody
//		@PostMapping("/mall/NumberOfMyCartButtons")//
//		public String NumberOfMyCartButtons(HttpSession hs) {
//			Member member = (Member)hs.getAttribute("loggedInMember");
//			List<MyCart> myCartList = cService.findMyCart(member.getId());
//			ArrayList<Integer> ownerIdList = new ArrayList<Integer>();
//			for(int i=0;i<myCartList.size();i++) {
//					if(!ownerIdList.contains(myCartList.get(i).getOwnerId())) {
//						ownerIdList.add(myCartList.get(i).getOwnerId());
//					}
//			}
//			ObjectMapper objectMapper = new ObjectMapper();
//			try {
//				return objectMapper.writeValueAsString(ownerIdList);
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//				return "";
//			}
//			
//		}
		

	

}