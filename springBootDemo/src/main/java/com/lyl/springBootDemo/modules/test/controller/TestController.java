package com.lyl.springBootDemo.modules.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@RequestMapping("/test/demoInfo")
	@ResponseBody
	public String demoDesc() {
		return "这是一次成功的操作";
	}

}
