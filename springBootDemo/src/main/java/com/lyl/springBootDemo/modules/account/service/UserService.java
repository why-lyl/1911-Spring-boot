package com.lyl.springBootDemo.modules.account.service;

import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.common.vo.Result;

public interface UserService {
	
	Result insertUser(User user);
	
	User getUserByUserName(String userName);
	
	Result getUser(User user);
}
