package eeit163.config;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class IndexInterceptor implements HandlerInterceptor {

	  @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
	        
	        System.out.println("preHandle");
	        return true;
	    }
	    
	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	            @Nullable ModelAndView modelAndView) throws Exception {
	        
	        System.out.println("postHandle");
	    }
	    
	    @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
	            @Nullable Exception ex) throws Exception {
	        
	        System.out.println("afterCompletion");
	    }

}