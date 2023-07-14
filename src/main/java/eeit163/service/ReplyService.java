package eeit163.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Member;
import eeit163.model.MemberRepository;
import eeit163.model.Reply;
import eeit163.model.ReplyRepository;

@Service
public class ReplyService {
	@Autowired
	private ReplyRepository rDao;

	@Autowired
	private MemberRepository mDao;
	
	public void insertReply(Reply Reply) {
		rDao.save(Reply);
	}

	@Transactional
	public void updateById(Reply Reply) {
		rDao.updateById(Reply.getId(), Reply.getAuthorId(), Reply.getArticleId(), Reply.getContent(), Reply.getLikes(),
				Reply.getReplyDate());
	}
	
	public List<Reply> findByLock() {
		return rDao.findByLock();
	}

	@Transactional
	public void deleteById(Integer id) {
		rDao.deleteById(id);
	}

	public List<Reply> findAllById(List<Integer> list) {
		return rDao.findAllById(list);
	}

	public Reply findById(Integer id) throws NoSuchElementException {
		return rDao.findById(id).get();
	}

	public List<Reply> findReplyByArticleId(Integer authorId) {
		List<Reply> list = rDao.findByArticleIdOrderByIdAsc(authorId);
		return list;
	}
	
	public List<Member> findReplyMembersByArticleId(Integer authorId) {
		List<Reply> list = rDao.findByArticleIdOrderByIdAsc(authorId);
		ArrayList<Member> arrayList = new ArrayList<Member>();
		for(Reply r:list) {
			arrayList.add(mDao.findById(r.getAuthorId()).get());
		}
		return arrayList;
	}

}
