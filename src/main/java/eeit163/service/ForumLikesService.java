package eeit163.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.ForumLikes;
import eeit163.model.ForumLikesRepository;

@Service
public class ForumLikesService {
	@Autowired
	private ForumLikesRepository flDao;

	public void insertForumLikes(ForumLikes ForumLikes) {
		flDao.save(ForumLikes);
	}

	@Transactional
	public void deleteById(Integer id) {
		flDao.deleteById(id);
	}
	
	public Integer showLikes(Integer postId,String type) {
		List<ForumLikes> list = flDao.findByPost(postId,type);
		return list.size();
	}

	public Integer checkLikes(Integer postId,Integer memberId,String type) {
		List<ForumLikes> list = flDao.findByPost(postId,type);
		Integer x=0;
		for(int i=0;i<list.size();i++) {
			if(memberId==list.get(i).getMemberId()) {
				x=list.get(i).getId();
			}
		}
		return x;
	}
	
	public Integer checkBookmark(Integer memberId,Integer articleId) {
		List<ForumLikes> list = flDao.findByMember(memberId,"bookmark");
		Integer x=0;
		for(int i=0;i<list.size();i++) {
			if(articleId==list.get(i).getPostId()) {
				x=list.get(i).getId();
			}
		}
		return x;
	}
	
	public List<ForumLikes> showBookmark(Integer memberId) {
		return flDao.findByMember(memberId,"bookmark");
	}

}
