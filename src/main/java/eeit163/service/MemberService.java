package eeit163.service;

import java.util.List;

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
	
	public Member findByUsername(String username) {
		return mDao.findByUsername(username).get(0);
	}

	public String checkLogin(String username,String password) {
		List<Member> memberList = mDao.findByUsername(username);
		
		if(memberList.isEmpty()) {
			return "查無用戶名";
		}
		else if(memberList.get(0).getPassword().equals(password)){
			return "登入成功";
		}
        return "密碼錯誤";
	}
}
