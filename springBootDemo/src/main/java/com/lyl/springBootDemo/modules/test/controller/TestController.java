package com.lyl.springBootDemo.modules.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyl.springBootDemo.modules.test.vo.ConfigBean;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/demoInfo")
	public String demoDesc() {
		return "这是一次成功的操作";
	}
    
	@Value("${server.port}")
	private Integer port;
	@Value("${com.lyl.name}")
	private String name;
	@Value("${com.lyl.age}")
	private int age;
	@Value("${com.lyl.desc}")
	private String desc;
	@Value("${com.lyl.random}")
	private String random;
	
	@Autowired
	private ConfigBean configBean;
	
	@RequestMapping("/config")
	public String configTest() {
		//获得全局配置的值
		StringBuffer sb = new StringBuffer();
		sb = sb.append(port).append("===");
		sb = sb.append(name).append("===");
		sb = sb.append(age).append("===");
		sb = sb.append(desc).append("===");
		sb = sb.append(random).append("<br>");
		//获得自行配置的值
		sb = sb.append(configBean.getName()).append("***");
		sb = sb.append(configBean.getAge()).append("***");
		sb = sb.append(configBean.getDesc()).append("***");
		sb = sb.append(configBean.getRandom()).append("//").append("END");
		
		return sb.toString();
		
		
	}
	    @RequestMapping("/log")
	    public String logTest() {
			LOGGER.trace("This is trace log.");
			LOGGER.debug("This is debug log.");
			LOGGER.info("This is info log.");
			LOGGER.warn("This is warn log.");
			LOGGER.error("This is error log.");
	    	return "This is log test.";
	    	
	    }
}
