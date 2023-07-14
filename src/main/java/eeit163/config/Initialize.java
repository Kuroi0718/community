package eeit163.config;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson2.JSON;

import eeit163.model.Ad;
import eeit163.model.AdRepository;
import eeit163.model.Article;
import eeit163.model.ArticleRepository;
import eeit163.model.Comment;
import eeit163.model.CommentRepository;
import eeit163.model.History;
import eeit163.model.HistoryRepository;
import eeit163.model.Member;
import eeit163.model.MemberRepository;
import eeit163.model.MyOrder;
import eeit163.model.MyOrderRepository;
import eeit163.model.Product;
import eeit163.model.ProductRepository;

@Component
public class Initialize implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	 private ArticleRepository aDAO;
	
	@Autowired
	private MemberRepository mDAO;
	
	@Autowired
	private MyOrderRepository myOrderDAO;
	
	@Autowired
	private CommentRepository commentDAO;
	
	@Autowired
	private HistoryRepository historyDAO;
	
	@Autowired
	private ProductRepository pDAO;

	@Value("classpath:static/json/member.json")
	private Resource memberResource;

	@Value("classpath:static/json/memberPhoto.json")
	private Resource memberPhotoResource;

	@Value("classpath:static/json/product.json")
	private Resource productResource;

	@Value("classpath:static/json/productPhoto.json")
	private Resource productPhotoResource;
	
	@Value("classpath:static/json/memberBirthday.json")
	private Resource memberBirthdayResource;
	
	@Value("classpath:static/json/article.json")
	private Resource articleResource;
	
	@Value("classpath:static/json/myOrder.json")
	private Resource myOrderResource;
	
	@Value("classpath:static/json/history.json")
	private Resource historyResource;
	
	@Value("classpath:static/json/comment.json")
	private Resource commentResource;
	
	@Value("classpath:static/json/commentDate.json")
	private Resource commentDateResource;
	
	@Value("classpath:static/json/historyDate.json")
	private Resource historyDateResource;
	
	@Value("classpath:static/json/myOrderDate.json")
	private Resource myOrderDateResource;
	
	@Autowired
	 private AdRepository adDAO;
	 
	 @Value("classpath:static/json/adContent.json")
	 private Resource adResource;
	 
	 @Value("classpath:static/json/adFile.json")
	 private Resource adFileResource;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		ServletContext servletContext = ((WebApplicationContext) event.getApplicationContext()).getServletContext();
//		servletContext.setAttribute("root", servletContext.getContextPath());
//		servletContext.setAttribute("webName", "Community");
		JSON.configReaderDateFormat("yyyy-MM-dd");
		insertMember();
		insertPruduct();
		insertArticle();
		insertMyOrder();
		insertHistory();
		insertComment();
		insertAd();
//		insertDataIntoDB();

	}

	/**
	 * 預設帳號
	 */

	private void insertMember() {
		if (mDAO.count() != 0) {
			return;
		}
		try {
			String str1 = FileCopyUtils.copyToString(new InputStreamReader(memberResource.getInputStream(), "UTF-8"));
			String str2 = FileCopyUtils
					.copyToString(new InputStreamReader(memberPhotoResource.getInputStream(), "UTF-8"));
			String str3 = FileCopyUtils
					.copyToString(new InputStreamReader(memberBirthdayResource.getInputStream(), "UTF-8"));
			List<Member> list = JSON.parseArray(str1, Member.class);
			List<String> list2 = JSON.parseArray(str2, String.class);
			List<String> list3 = JSON.parseArray(str3, String.class);
			Date date = new Date();
			for (int i = 0; i < list2.size(); i++) {
				list.get(i).setPhoto(Base64.getDecoder().decode(list2.get(i)));
				date=new SimpleDateFormat("yyyy-MM-dd").parse(list3.get(i));
				list.get(i).setBirthday(date);
			}

			mDAO.saveAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertPruduct() {
		if (pDAO.count() != 0) {
			return;
		}
		try {
			String str1 = FileCopyUtils.copyToString(new InputStreamReader(productResource.getInputStream(), "UTF-8"));
			String str2 = FileCopyUtils
					.copyToString(new InputStreamReader(productPhotoResource.getInputStream(), "UTF-8"));
			List<Product> list = JSON.parseArray(str1, Product.class);
			List<String> list2 = JSON.parseArray(str2, String.class);
			for (int i = 0; i < list2.size(); i++) {
				list.get(i).setProductPhoto(Base64.getDecoder().decode(list2.get(i)));
				list.get(i).setReleaseDate(new Date());
			}
			
			for (int i = 125; i < 155; i++) {
				Calendar calendar = Calendar.getInstance();
				if(list.get(i).getTimeLimit().equals("259200")) {
					calendar.add(Calendar.DAY_OF_MONTH, 3);
					list.get(i).setExpirationDate(calendar.getTime());
				};
				if(list.get(i).getTimeLimit().equals("604800")) {
					calendar.add(Calendar.DAY_OF_MONTH, 7);
					list.get(i).setExpirationDate(calendar.getTime());
				};
				if(list.get(i).getTimeLimit().equals("1209600")) {
					calendar.add(Calendar.DAY_OF_MONTH, 14);
					list.get(i).setExpirationDate(calendar.getTime());
				};
			}
			

			pDAO.saveAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertComment() {
		if (commentDAO.count() != 0) {
			return;
		}
		try {
			String str1 = FileCopyUtils.copyToString(new InputStreamReader(commentResource.getInputStream(), "UTF-8"));
			String str3 = FileCopyUtils
					.copyToString(new InputStreamReader(commentDateResource.getInputStream(), "UTF-8"));
			List<Comment> list = JSON.parseArray(str1, Comment.class);
			List<String> list3 = JSON.parseArray(str3, String.class);
			Date date = new Date();
			for (int i = 0; i < list3.size(); i++) {
				date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(list3.get(i));
				list.get(i).setCommentDate(date);
			}

			commentDAO.saveAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertHistory() {
		if (historyDAO.count() != 0) {
			return;
		}
		try {
			String str1 = FileCopyUtils.copyToString(new InputStreamReader(historyResource.getInputStream(), "UTF-8"));
			String str3 = FileCopyUtils
					.copyToString(new InputStreamReader(historyDateResource.getInputStream(), "UTF-8"));
			List<History> list = JSON.parseArray(str1, History.class);
			List<String> list3 = JSON.parseArray(str3, String.class);
			Date date = new Date();
			for (int i = 0; i < list3.size(); i++) {
				date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(list3.get(i));
				list.get(i).setOrderDate(date);
			}

			historyDAO.saveAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertMyOrder() {
		if (myOrderDAO.count() != 0) {
			return;
		}
		try {
			String str1 = FileCopyUtils.copyToString(new InputStreamReader(myOrderResource.getInputStream(), "UTF-8"));
			String str3 = FileCopyUtils
					.copyToString(new InputStreamReader(myOrderDateResource.getInputStream(), "UTF-8"));
			List<MyOrder> list = JSON.parseArray(str1, MyOrder.class);
			List<String> list3 = JSON.parseArray(str3, String.class);
			Date date = new Date();
			for (int i = 0; i < list3.size(); i++) {
				date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(list3.get(i));
				list.get(i).setOrderDate(date);
			}

			myOrderDAO.saveAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertArticle() {
		if (aDAO.count() != 0) {
			return;
		}
		try {
			String str1 = FileCopyUtils.copyToString(new InputStreamReader(articleResource.getInputStream(), "UTF-8"));
			List<Article> list = JSON.parseArray(str1, Article.class);
			aDAO.saveAll(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertAd() {
		  if (adDAO.count() != 0) {
		   return;
		  }
		  try {
		   String str1 = FileCopyUtils.copyToString(new InputStreamReader(adResource.getInputStream(), "UTF-8"));
		   String str2 = FileCopyUtils
		     .copyToString(new InputStreamReader(adFileResource.getInputStream(), "UTF-8"));
		   List<Ad> list = JSON.parseArray(str1, Ad.class);
		   List<String> list2 = JSON.parseArray(str2, String.class);
		   Date date = new Date();
		   for (int i = 0; i < list2.size(); i++) {
		    list.get(i).setAdFile(Base64.getDecoder().decode(list2.get(i)));
		   }
		   adDAO.saveAll(list);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		 }

}
