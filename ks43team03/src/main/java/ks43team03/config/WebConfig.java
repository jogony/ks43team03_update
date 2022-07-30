package ks43team03.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ks43team03.interceptor.LoginInterceptor;

/**
 * mvc 추가하는 설정들 넣는 것
 * 인터셉터 등
 */
@Component
public class WebConfig implements WebMvcConfigurer{
	
	private LoginInterceptor loginInterceptor;
	
	public WebConfig(LoginInterceptor loginInterceptor) {
		  this.loginInterceptor = loginInterceptor; 
	  }
	
	/**
	 * 몇개의 어디든지의 경로에 추가하고 싶으면 addPathPatterns("/**")을 사용한다.
	 * 특정 경로를 제외하고 싶으면 excludePathPatterns("/*")를 사용
	 * favicon - 브라우저 탭 아이콘
	 */
	/*
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/favicon.ico")
				.excludePathPatterns("/");
				
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	*/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/admin/**")
				.addPathPatterns("/trainer/addTrainer")
				.addPathPatterns("/trainer/modifyTrainer")
				.addPathPatterns("/adminFacility/addFacility")
				.addPathPatterns("/stadium/addStadium")
				.addPathPatterns("/facility/addFacilityUser")
				.addPathPatterns("/pass/passList")
				.addPathPatterns("/pass/admin/passList")
				.addPathPatterns("/pass/addPass")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/favicon.ico")
				.excludePathPatterns("/");
				
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
