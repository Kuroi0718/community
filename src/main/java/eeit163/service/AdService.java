package eeit163.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Ad;
import eeit163.model.AdRepository;
import eeit163.model.MemberRepository;

@Service
public class AdService {
	@Autowired
	private AdRepository adRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	 @Autowired
	    private MemberService memberService;

	public Ad findAdById(Integer id) {
		Optional<Ad> option = adRepository.findById(id);
		if (option.isPresent()) {
			Ad rsAd = option.get();
			return rsAd;
		}
		return null;
	}
	
	public Ad findAdById2(Integer id) {
	    Optional<Ad> option = adRepository.findById(id);
	    return option.orElse(null);
	}

	public List<Ad> listAllAds() {
		return adRepository.findAll();
	}
	
	public List<Ad> findByStatus(Integer status) {
		return adRepository.findByStatus(status);
	}
	
	@Transactional
	public void updateAdStatus(Integer id,Integer status) {
		adRepository.updateById(id, status);
	}

	public void deleteAd(Integer id) {
		adRepository.deleteById(id);
	}

	
	public AdService(AdRepository adRepository, MemberRepository memberRepository) {
		this.adRepository = adRepository;
		this.memberRepository = memberRepository;
	}

	 
	public Ad insertAd(Ad ad) {
	return adRepository.save(ad);
}

}
