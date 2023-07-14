package eeit163.errorhandle;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=MaxUploadSizeExceededException.class)
	public String imageMaxSizeHandler(Model model) {
		model.addAttribute("errorMsg", "圖片太大了~~限制10MB唷");
		return "ad/uploadPage";
	}
}
