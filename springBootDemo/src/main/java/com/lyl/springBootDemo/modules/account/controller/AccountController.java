package com.lyl.springBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyl.springBootDemo.modules.account.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 127.0.0.1:8688/account/login
	 * https://127.0.0.1:8868/account/login
	 */
	@RequestMapping("/login")
	public String loginPage() {
		
		return "indexSimple";
		
	}
	
	/**
	 * 用户退出登录
	 */
	@RequestMapping("/logout")
	public String logout() {
		userService.logout();
		return "redirect:/account/login";
	}
	
	
	/**
	 * 127.0.0.1:8688/account/dashboard
	 * https://127.0.0.1:8868/account/dashboard
	 */
	@RequestMapping("/dashboard")
	public String dashboardPage() {
		
		return "index";
	}
	
	/**
	 * 127.0.0.1:8688/account/users
	 * https://127.0.0.1:8868/account/users
	 */
	@RequestMapping("/users")
	public String usersPage() {
		return "index";
	}
	
	/**
	 * 127.0.0.1:8688/account/roles
	 * https://127.0.0.1:8868/account/roles
	 */
	@RequestMapping("/roles")
	public String rolesPage() {
		return "index";
	}
	
	/**
	 * 127.0.0.1:8688/account/resources
	 * https://127.0.0.1:8868/account/resources
	 */
	@RequestMapping("/resources")
	public String resourcesPage() {
		return "index";
	}


}
