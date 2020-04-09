package com.lyl.springBootDemo.modules.account.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.dao.UserDao;
import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.account.service.UserService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.Result.ResultStatus;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;

/**
 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //将new Date()格式化为更易接受的格式且转换为字符串
 * String time = df.format(new Date());
 * 因为数据库设置的是date格式，所以无法插入成功，留待参考
 * time = time.replaceFirst("-", "年");//此处因为得到的字符串较简单，写出了每个需要替换的地方
 * time = time.replaceFirst("-", "月")+("日");
 * user.setCreateDate(time);
 */


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
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //将new Date()格式化为更易接受的格式且转换为字符串
		String time = df.format(new Date());
		user.setCreateDate(time);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo(searchVo);
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		List<User> users = Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
				.orElse(Collections.emptyList());
		return new PageInfo(users);
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public Result updateUser(User user) {
		
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if (userTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("User name is repeat.");
			return result;
		}
		
		userDao.updateUser(user);
		
		return result;
	}

	@Override
	public Result deleteUser(int userId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		try {
			userDao.deleteUser(userId);
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

}
