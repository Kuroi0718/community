package eeit163.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eeit163.model.Member;
import eeit163.model.Message;
import eeit163.service.MessageService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MessageController {

	@Autowired
	private MessageService msgService;

	@Value("classpath:static/image/noimg.png")
	private Resource noimg;

	@ResponseBody
	@PostMapping("/message/update")
	public String update(@RequestParam("id") Integer id, @RequestParam("sender") Integer sender,
			@RequestParam("target") Integer target, @RequestParam("type") Integer type,
			@RequestParam("content") String content, @RequestParam("part") MultipartFile part) {
		try {
			Message m = msgService.findById(id);
			if (sender != null) {
				m.setSender(sender);
			}
			if (target != null) {
				m.setTarget(target);
			}
			if (type != null) {
				m.setType(type);
			}
			if (type == 0 && content != null && content.length() != 0) {
				m.setContent(content);
			}
			if (part.getSize() != 0L && type == 1) {
				m.setContent(Base64.getEncoder().encodeToString(part.getBytes()));
			}
			m.setCreationtime(new Date());
			msgService.updateById(m);
			return "修改成功";
		} catch (IOException e) {
			e.printStackTrace();
			return "修改失敗";
		}
	}

	@ResponseBody
	@PostMapping("/message/addMessage")
	public String addMessage(@RequestParam("target") Integer target, @RequestParam("content") String content,
			@RequestParam("part") MultipartFile part, HttpSession hs) {
		try {
			Integer sender = ((Member) hs.getAttribute("loggedInMember")).getId();
			Message m = new Message();
			Integer type = 0;
			if (part.getSize() != 0L) {
				type = 1;
				m.setContent("data:image/*;base64," + Base64.getEncoder().encodeToString(part.getBytes()));
			}
			m.setSender(sender);
			m.setTarget(target);
			m.setType(type);
			if (type == 0) {
				m.setContent(content);
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			m.setCreationtime(date);
			msgService.insertMessage(m);
			return sdf.format(date);
		} catch (IOException e) {
			e.printStackTrace();
			return "發送失敗";
		}
	}

	@ResponseBody
	@PostMapping("/message/showChat")
	public String showChat(@RequestParam("target") Integer target, Model model, HttpSession hs) {
		Integer sender = ((Member) hs.getAttribute("loggedInMember")).getId();
		List<Message> chat = msgService.findByChat(sender, target);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String str = objectMapper.writeValueAsString(chat);
			return str;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@ResponseBody
	@PostMapping("/message/unRead")
	public Integer unRead(@RequestParam("id") Integer sender, HttpSession hs) {
		Integer target = ((Member) hs.getAttribute("loggedInMember")).getId();
		List<Message> chat = msgService.findByChat(sender, target);
		int count=0;
		for (Message m : chat) {
			if (m.getType() == 0) {
				count++;
			}
			if (m.getType() == 1) {
				count++;
			}
		}
		return count;
	}

	@ResponseBody
	@PostMapping("/message/read")
	public void read(@RequestParam("id") Integer sender, HttpSession hs) {
		Integer target = ((Member) hs.getAttribute("loggedInMember")).getId();
		List<Message> chat = msgService.findByChat(sender, target);
		for (Message m : chat) {
			if (m.getType() == 0) {
				m.setType(2);
				msgService.updateById(m);
			}
			if (m.getType() == 1) {
				m.setType(3);
				msgService.updateById(m);
			}
		}
	}
	/////////////////////////////////////////////////////////////////
	@ResponseBody
	@PostMapping("/message/shareMessage")
	public String shareMessage(@RequestParam("target") Integer target, @RequestParam("content") String content,
			 HttpSession hs) {
		Integer sender = ((Member) hs.getAttribute("loggedInMember")).getId();
		Message m = new Message();
		Integer type = 0;
		m.setSender(sender);
		m.setTarget(target);
		m.setType(type);
		if (type == 0) {
			m.setContent(content);
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		m.setCreationtime(date);
		msgService.insertMessage(m);
		return sdf.format(date);
	}

	

}