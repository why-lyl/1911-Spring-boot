package com.lyl.springBootDemo.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lyl.springBootDemo.filter.ParameterFilter;
import com.lyl.springBootDemo.interceptor.UriInterceptor;

@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private UriInterceptor uriInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(uriInterceptor).addPathPatterns("/**");
	}

	@Bean
	public FilterRegistrationBean<ParameterFilter> filterRegister() {
		FilterRegistrationBean<ParameterFilter> bean = new FilterRegistrationBean<ParameterFilter>();
		bean.setFilter(new ParameterFilter());
		return bean;
	}
}
