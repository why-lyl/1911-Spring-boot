package com.lyl.springBootDemo.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebFilter(filterName = "parameterFilter", urlPatterns = "/**")
public class ParameterFilter implements Filter {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ParameterFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("Init parameter filter.");
		Filter.super.init(filterConfig);
	}

	@Override
	public void destroy() {
		LOGGER.debug("Destroy parameter filter.");
		Filter.super.destroy();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
//		Map<String, String[]> map = httpRequest.getParameterMap();
//		map.put("testkey", new String[] {"********"});
		
	
	HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
		@Override
		public String getParameter(String name) {
			String value = httpRequest.getParameter(name);
			if (value != null || value != "") {
				return value.replace("fuck", "***");
			}
			return super.getParameter(name);
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] values = httpRequest.getParameterValues(name);
			if (values != null && values.length > 0) {
				values[0] = values[0].replace("fuck", "***");
				return values;
			}
			return super.getParameterValues(name);
		}
		
		
	};
	
	chain.doFilter(wrapper, response);
}

}
	


