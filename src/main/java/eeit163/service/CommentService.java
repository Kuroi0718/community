package eeit163.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import eeit163.model.Comment;
import eeit163.model.CommentRepository;
import eeit163.model.MyOrder;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentDao;
	
	public void insertComment(Comment comment) {
		commentDao.save(comment);
	}
	
	public List<Comment> findAllComments(){
		return commentDao.findAllComments();
	}
	
	public List<Comment> findMyComments(Integer id, String type){
		return commentDao.findMyComments(id,type);
	}
	
	public List<Comment> findComments(Integer id, String type){
		return commentDao.findComments(id,type);
	}
	
	public List<Comment> findCommentsBySerialIdArray(String[] serialIdArray){
		return commentDao.findCommentsBySerialIdArray(serialIdArray);
	}

	
}
