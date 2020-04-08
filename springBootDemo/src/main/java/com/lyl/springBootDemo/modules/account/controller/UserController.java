package com.lyl.springBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.account.service.UserService;
import com.lyl.springBootDemo.modules.common.vo.Result;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping(value = "/register", consumes = "application/json")
	public Result register(@RequestBody User user) {
		return userService.insertUser(user);
	}
	
	@PostMapping(value="/login", consumes="application/json")
	public Result getUser(@RequestBody User user) {
		return userService.getUser(user);
	}
}
