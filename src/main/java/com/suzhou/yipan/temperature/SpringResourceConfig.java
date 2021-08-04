/*package com.suzhou.yipan.temperature;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.WebAppRootListener;

import com.suzhou.yipan.temperature.filter.TempRequestFilter;



@Configuration("openSpringResourceConfig")

public class SpringResourceConfig {

	@Bean
	public FilterRegistrationBean ifpEncodingFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new TempRequestFilter());
		registration.addUrlPatterns("*.do", "*.html"); 
		registration.addInitParameter("encoding", "UTF-8"); 
		registration.setName("TempRequestFilter");
		registration.setOrder(2);
		return registration;
	}
	

	@Bean
	public ServletListenerRegistrationBean<WebAppRootListener> webAppRootListenerBean(){
		ServletListenerRegistrationBean<WebAppRootListener> 
		webAppRootListener = new ServletListenerRegistrationBean<WebAppRootListener>(new WebAppRootListener());
		return webAppRootListener;
	}
}
*/