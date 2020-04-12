package com.lyl.springBootDemo.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;

public interface UserService {
	
	Result insertUser(User user);
	
	Result addUser(User user);
	
	User getUserByUserName(String userName);
	
	Result login(User user);
	
	void logout();
	
    PageInfo<User> getUsersBySearchVo(SearchVo searchVo);
	
	User getUserById(int userId);
	
	Result updateUser(User user);
	
	Result deleteUser(int userId);
}
