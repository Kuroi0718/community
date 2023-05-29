package eeit163.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Member;
import eeit163.model.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository mDao;

	public void insertMember(Member member) {
		mDao.save(member);
	}
	
	@Transactional
	public void updateById(Member member) {
		mDao.updateById(member.getId(), member.getUsername(), member.getPassword(), member.getLevel(), member.getEmail(),member.getPhoto());
	}
	
	
	@Transactional
	public void deleteById(Integer id) {
		mDao.deleteById(id);
	}
	
	public byte[] findPhotoById(Integer id) {
		return mDao.findById(id).get().getPhoto();
	}
	
	public Member findById(Integer id) throws NoSuchElementException{
		return mDao.findById(id).get();
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
	
	
	public String checkUsername(String username) {
		List<Member> memberList = mDao.findByUsername(username);
		
		if(memberList.isEmpty()) {
			return "可使用";
		}
		else{
			return "用戶已存在";
		}
	}
	
	
	
}
