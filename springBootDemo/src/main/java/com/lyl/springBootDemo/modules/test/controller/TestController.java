package com.lyl.springBootDemo.modules.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lyl.springBootDemo.modules.test.entity.City;
import com.lyl.springBootDemo.modules.test.entity.Country;
import com.lyl.springBootDemo.modules.test.service.CountryService;
import com.lyl.springBootDemo.modules.test.vo.ConfigBean;

@Controller
@RequestMapping("/test")
public class TestController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private CountryService countryService;
	
	@RequestMapping("/demoInfo")
	@ResponseBody
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
	@ResponseBody
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
	    @ResponseBody
	    public String logTest() {
			LOGGER.trace("This is trace log.");
			LOGGER.debug("This is debug log.");
			LOGGER.info("This is info log.");
			LOGGER.warn("This is warn log.");
			LOGGER.error("This is error log.");
	    	return "This is log test.";
	    	
	    }
	    
	    @PostMapping(value="/upload", consumes="multipart/form-data")
		public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
			if (file.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Please select file.");
				return "redirect:/test/index";
			}
			
			String fileName = file.getOriginalFilename();
			
			File destFile = new File(String.format("F:\\upload\\%s", fileName));
			try {
				file.transferTo(destFile);
				redirectAttributes.addFlashAttribute("message", "upload success.");
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				LOGGER.debug(e.getMessage());
				redirectAttributes.addFlashAttribute("message", "upload failed.");
			}
			
			return "redirect:/test/index";
		}
	    
	    @RequestMapping("/index")
		public String testIndexPage(ModelMap modelMap) {
	    	int countryId = 522;
	    	List<City> cities = countryService.getCitiesByCountryId(countryId)
	    			.stream().limit(10).collect(Collectors.toList());
	    	City city = cities.get(0);//此处可直接调用在（1号），减少一行代码
	    	Country country = countryService.getCountryById(countryId);
	    	
	    	modelMap.addAttribute("shopLogo", 
	    			"https://timgsa.baidu.com/timg?image&quality=80&"
	    			+ "size=b9999_10000&sec=1585883080930&di=b90f1440156eabe007569acca6982517"
	    			+ "&imgtype=0&src=http%3A%2F%2Fbbsfiles.vivo.com.cn"
	    			+ "%2Fvivobbs%2Fattachment%2Fforum%2F201711%2F25%2F190353wrl8l88w8zwlg8b2.gif");
	    	modelMap.addAttribute("cities", cities);
	    	modelMap.addAttribute("updateCityUri", "city");
	    	modelMap.addAttribute("country", country);
	    	modelMap.addAttribute("city", city);//封装对象时，不加引号 （1号）
	    	modelMap.addAttribute("bilibiliUrl", "https://www.bilibili.com");
	    	modelMap.addAttribute("changeType", "checkbox");
	    	modelMap.addAttribute("currentNumber", "99");
	    	modelMap.addAttribute("checked", true);
	    	modelMap.addAttribute("thymeleafTitle", "此处展示text");
			modelMap.addAttribute("template", "test/index");
			return "index";
		}
}
