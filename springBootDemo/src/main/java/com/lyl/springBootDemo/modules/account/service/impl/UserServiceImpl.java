package com.lyl.springBootDemo.modules.account.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.springBootDemo.modules.account.dao.UserDao;
import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.account.service.UserService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.Result.ResultStatus;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public Result insertUser(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if (userTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("User name is repeat.");
			return result;
		}
		
		user.setCreateDate(new Date());
		userDao.insertUser(user);
		
		return result;
	}

	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public Result getUser(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		User userTemp = userDao.getUser(user);
		if (userTemp == null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("User name or password error.");
			return result;
		} else {
			result.setObject(userTemp);
		}
		
		return result;
	}

}
