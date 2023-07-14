package eeit163.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Message;
import eeit163.model.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository msgDao;

	public void insertMessage(Message Message) {
		msgDao.save(Message);
	}
	
	@Transactional
	public void updateById(Message Message) {
		msgDao.updateById(Message.getId(), Message.getSender(), Message.getTarget(), Message.getType(), Message.getContent(),Message.getCreationtime());
	}
	
	
	@Transactional
	public void deleteById(Integer id) {
		msgDao.deleteById(id);
	}
	
	public List<Message> findAllById(List<Integer> list) {
		return msgDao.findAllById(list);
	}
	
	public Message findById(Integer id) throws NoSuchElementException{
		return msgDao.findById(id).get();
	}
	
	public List<Message> findBySender(Integer senderId,Integer targetId) {
		return msgDao.findBySender(senderId,targetId);
	}
	
	public List<Message> findByTarget(Integer senderId,Integer targetId) {
		return msgDao.findByTarget(senderId,targetId);
	}
	
	public List<Message> findByChat(Integer senderId,Integer targetId) {
		return msgDao.findByChat(senderId,targetId);
	}
	
}