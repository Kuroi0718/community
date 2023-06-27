package eeit163.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		mDao.updateById(member.getId(), member.getUsername(), member.getPassword(), member.getLevel(), member.getEmail(),member.getPhoto(),member.getTel(),member.getGender(),member.getFriend(),member.getInvitation(),member.getInvite(),member.getBirthday(),member.getCreationdate());
	}
	
	
	@Transactional
	public void deleteById(Integer id) {
		mDao.deleteById(id);
	}
	
	public List<Member> findAllById(List<Integer> list) {
		return mDao.findAllById(list);
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
	
	public Page<Member> findAllByPage(Integer pageNumber,String sort,String order,String where,String search){
		PageRequest pgb;
		
		if("asc".equals(order)) {
		pgb = PageRequest.of(pageNumber-1, 10, Sort.Direction.ASC, sort);}
		else {pgb = PageRequest.of(pageNumber-1, 10, Sort.Direction.DESC, sort);}
		Page<Member> page = mDao.findAll(pgb);
		List<Member> list;
		String[] str;
		String a;
		ArrayList<Integer> intlist= new ArrayList<Integer>();
System.out.println("======================");
		if("id".equals(where)) {
			if(!"".equals(search)) {
				for(int i=0;i<search.length();i++) {
					a=search.substring(i, i+1) ;
					int c=1;
					for(int j=0;j<=9;j++) {
						if(String.valueOf(j).equals(a)) {
							c=0;
						}
					}
					if(c==1) {
						search=search.replaceFirst(a, ",");
					}
				}
				str = search.split(",");
				for(int i=0;i<str.length;i++) {
					if(!"".equals(str[i])) {
					intlist.add(Integer.valueOf(str[i])) ;}
				}
				list=mDao.findAllById(intlist);
				System.out.println("===========456654==========="+list.size());
				System.out.println("===========456654==========="+pageNumber);
				System.out.println("===========456654==========="+(10*pageNumber>list.size()?list.size():10*pageNumber));
				page=new PageImpl<Member>(list.subList(10*pageNumber-10, (10*pageNumber>list.size()?list.size():10*pageNumber)),pgb,list.size());
				}
		}
		else if("username".equals(where)) {
			switch(order) {
			case "desc":
				switch(sort) {
				case "id":list=mDao.findByUsernameLikeOrderByIdDesc(search);
					break;
				case "username":list=mDao.findByUsernameLikeOrderByUsernameDesc(search);
					break;
				case "level":list=mDao.findByUsernameLikeOrderByLevelDesc(search);
					break;
				case "gender":list=mDao.findByUsernameLikeOrderByGenderDesc(search);
					break;
				case "creationdate":list=mDao.findByUsernameLikeOrderByCreationdateDesc(search);
					break;
				case "birthday":list=mDao.findByUsernameLikeOrderByBirthdayDesc(search);
					break;
				case "tel":list=mDao.findByUsernameLikeOrderByTelDesc(search);
					break;
				case "email":list=mDao.findByUsernameLikeOrderByEmailDesc(search);
					break;
				 default :	list=mDao.findByUsernameLikeOrderByIdDesc(search);
				}
				break;
			default:
				switch(sort) {
				case "id":list=mDao.findByUsernameLikeOrderByIdAsc(search);
					break;
				case "username":list=mDao.findByUsernameLikeOrderByUsernameAsc(search);
					break;
				case "level":list=mDao.findByUsernameLikeOrderByLevelAsc(search);
					break;
				case "gender":list=mDao.findByUsernameLikeOrderByGenderAsc(search);
					break;
				case "creationdate":list=mDao.findByUsernameLikeOrderByCreationdateAsc(search);
					break;
				case "birthday":list=mDao.findByUsernameLikeOrderByBirthdayAsc(search);
					break;
				case "tel":list=mDao.findByUsernameLikeOrderByTelAsc(search);
					break;
				case "email":list=mDao.findByUsernameLikeOrderByEmailAsc(search);
					break;
				 default :	list=mDao.findByUsernameLikeOrderByIdAsc(search);
				}
			}
			System.out.println("===========123321==========="+list.size());
			System.out.println("===========123321==========="+pageNumber);
			System.out.println("===========123321==========="+(10*pageNumber>list.size()?list.size():10*pageNumber));
			page=new PageImpl<Member>(list.subList(10*pageNumber-10, (10*pageNumber>list.size()?list.size():10*pageNumber)),pgb,list.size());
			
		}
		return page;
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
