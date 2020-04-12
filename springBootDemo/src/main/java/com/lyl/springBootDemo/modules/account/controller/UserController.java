package com.lyl.springBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.account.service.UserService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;

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
	
	@PostMapping(value="/users", consumes="application/json")
	public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo) {
		return userService.getUsersBySearchVo(searchVo);
	}
	
	@RequestMapping("/user/{userId}")
	public User getUserById(@PathVariable int userId) {
		return userService.getUserById(userId);
	}
	
	@PutMapping(value="/user", consumes="application/json")
	public Result updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/user/{userId}")
	public Result deleteUser(@PathVariable int userId) {
		return userService.deleteUser(userId);
	}
}
