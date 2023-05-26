package eeit163.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Member;
import eeit163.model.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository mDao;

	public void insertMessage(Member member) {
		mDao.save(member);
	}
	
	@Transactional
	public void deleteById(Integer id) {
		mDao.deleteById(id);
	}
}
