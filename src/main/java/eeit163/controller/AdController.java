package eeit163.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eeit163.model.Ad;
import eeit163.model.Member;
import eeit163.service.AdService;
import eeit163.service.MemberService;


@Controller
public class AdController {	
	
	@Autowired
	private AdService adService;	
	
    @Autowired
    private MemberService memberService;
	
	@GetMapping("/ad/upload")
    public String uploadPhoto() {
        return "ad/uploadPage";
    }
	

	@PostMapping("/ad/uploadPost")
    public String uploadPost(@RequestParam("adContent") String adContent,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("memberId") Integer memberId,
                             RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "上傳失敗，請選擇廣告檔案");
            return "redirect:/ad/upload";
        }
        if (adContent.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "上傳失敗，請填寫廣告說明");
            return "redirect:/ad/upload";
        }
        try {
            Member member = memberService.findById(memberId);
            if (member == null) {
                redirectAttributes.addFlashAttribute("errorMsg", "找不到相應的會員");
                return "redirect:/ad/upload";
            }

            Ad ad = new Ad();
            ad.setAdContent(adContent);
            ad.setAdFile(file.getBytes());
            ad.setMember(member);
            adService.insertAd(ad);

            redirectAttributes.addFlashAttribute("ok", "上傳成功，等待審核");
            return "redirect:/ad/upload";
//            redirectAttributes.addAttribute("adId", ad.getId());
//            return "redirect:/ad/payment";
            
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMsg", "上傳失敗");
            return "redirect:/ad/upload";
        }
    }

	@GetMapping("/ad/image")
	public ResponseEntity<byte[]> getImage(@RequestParam("id") Integer id){
		Ad ad = adService.findAdById(id);
		byte[] adFile = ad.getAdFile();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_JPEG);	
		return new ResponseEntity<byte[]>(adFile, header, HttpStatus.OK);
	}
	@GetMapping("/ad/list")
	public String listPhotoData(Model model) {
		List<Ad> adList = adService.findByStatus(0);
		model.addAttribute("adList", adList);
		return "ad/listAd";
	} 
	@PostMapping("/ad/selectedImages")
	public String displaySelectedImages(@RequestParam("selectedImages") List<Integer> selectedImages, Model model) {
	    model.addAttribute("selectedImages", selectedImages);
	    return "ad/displaySelectedImages";
	}
	
	@GetMapping("/ad/delete")
	public String deleteAd(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
	    adService.deleteAd(id);
	    redirectAttributes.addFlashAttribute("deleteSuccess", true); // 傳遞刪除成功的參數
	    return "redirect:/dashboardPage?activeBlock=Block4";
	}
	

	@GetMapping("/ad/payment")
	public String showPaymentPage(Model model) {
	    // 設置付款頁面資料
	    return "ad/payment";
	}
	@PostMapping("/ad/processPayment")
	public String processPayment(@RequestParam(value = "adId", required = false) Integer adId, RedirectAttributes redirectAttributes, Model model) {
	    if (adId == null) {
	        redirectAttributes.addFlashAttribute("errorMsg","付款成功,請等待審核");
	        return "redirect:/ad/upload";
	    }

	    Ad ad = adService.findAdById(adId);
	    if (ad == null) {
	        redirectAttributes.addFlashAttribute("errorMsg", "找不到相應的廣告");
	        return "redirect:/ad/upload";
	    }

	    model.addAttribute("ad", ad);

	    redirectAttributes.addFlashAttribute("ok", "付款成功");

	    return "redirect:/ad/upload";
	}
	
	@PostMapping("/ad/updateAdStatus")
	public String updateAdStatus(@RequestParam("selectedImages") Integer[] selectedImages,@RequestParam("deSelectedImages") Integer[] deSelectedImages, Model model) {
		for(int i=0;i<selectedImages.length;i++) {
			adService.updateAdStatus(selectedImages[i], 1);
			adService.updateAdStatus(deSelectedImages[i], 0);
		}
	    return "redirect:/ad/displayImage";
	}
	
	@GetMapping("/ad/displayImage")
	public String displayImage(Model model) {
	    List<Ad> adList1 = adService.findByStatus(1);
	    List<Ad> adList2 = adService.findByStatus(2);
	    model.addAttribute("adList1", adList1);
	    model.addAttribute("adList2", adList2);
	    return "Index";
	}






	

	
//	@GetMapping("/ad/delete")
//  public String deleteAd(@RequestParam("id") Integer id) {
//      adService.deleteAd(id);
//      return "redirect:/ad/list";
//  }
	
//	@PostMapping("/ad/uploadPost")
//	public String uploadPost(@RequestParam("adContent") String adContent,
//			@RequestParam("file") MultipartFile file,
//			RedirectAttributes redirectAttributes) {
//		if (file.isEmpty()) {
//			redirectAttributes.addFlashAttribute("errorMsg", "上傳失敗，請選擇廣告檔案");
//			return "redirect:/ad/upload";
//		}		
//		if (adContent.isEmpty()) {
//			redirectAttributes.addFlashAttribute("errorMsg", "上傳失敗，請填寫廣告說明");
//			return "redirect:/ad/upload";
//		}
//		try {
//			
//			Ad ad = new Ad();
//			ad.setAdContent(adContent);
//			ad.setAdFile(file.getBytes());			
//			adService.insertAd(ad);		
//	
//			redirectAttributes.addFlashAttribute("ok", "上傳成功，等待審核");			
//			return "redirect:/ad/upload";
//		} catch (IOException e) {
//			e.printStackTrace();
//			redirectAttributes.addFlashAttribute("errorMsg", "上傳失敗");
//			return "redirect:/ad/upload";
//		}
//	}
}