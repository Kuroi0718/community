package eeit163.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private IndexInterceptor indexInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(indexInterceptor)
						.addPathPatterns("/**")
						.excludePathPatterns("/layout/**", "/member/**", "/css/**", "/images/**", "/js/**");
	}

}