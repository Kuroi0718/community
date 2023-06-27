package eeit163.config;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Base64;
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

import eeit163.model.Member;
import eeit163.model.MemberRepository;

@Component
public class Initialize implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MemberRepository mDAO;

	@Value("classpath:static/json/member.json")
	private Resource memberResource;

	@Value("classpath:static/json/memberPhoto.json")
	private Resource memberPhotoResource;

	@Value("classpath:static/json/memberBirthday.json")
	private Resource memberBirthdayResource;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		ServletContext servletContext = ((WebApplicationContext) event.getApplicationContext()).getServletContext();
//		servletContext.setAttribute("root", servletContext.getContextPath());
//		servletContext.setAttribute("webName", "Community");
		JSON.configReaderDateFormat("yyyy-MM-dd");
		insertMember();
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

}
