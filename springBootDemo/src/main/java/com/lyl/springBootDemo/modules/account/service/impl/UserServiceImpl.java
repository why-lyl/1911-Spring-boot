package com.lyl.springBootDemo.modules.account.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.dao.UserDao;
import com.lyl.springBootDemo.modules.account.dao.UserRoleDao;
import com.lyl.springBootDemo.modules.account.entity.Role;
import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.account.service.UserService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.Result.ResultStatus;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;
import com.lyl.springBootDemo.util.MD5Util;

/**
 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //将new Date()格式化为更易接受的格式且转换为字符串
 * String time = df.format(new Date());
 * 因为数据库设置的是date格式，所以无法插入成功，留待参考
 * time = time.replaceFirst("-", "年");//此处因为得到的字符串较简单，写出了每个需要替换的地方
 * time = time.replaceFirst("-", "月")+("日");
 * user.setCreateDate(time);
 */

/**
 * 1号的null的理解,查看dao层注入的sql语句会发现，是通过名字查询来获得user的信息，要是已有名字就会查询出值，
 * 未有名字就会无法查询出，因此为null。这样就可以判断是否有重复名字的用户
 */

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public Result insertUser(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if(userTemp != null){//添加的用户名重复时，会弹出弹窗
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("用户名重复，请重命名用户名");
			return result;
		}
     
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //将new Date()格式化为更易接受的格式且转换为字符串
		String time = df.format(new Date());
		user.setCreateDate(time);
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		userDao.insertUser(user);
		
		List<Role> roles = user.getRoles();
	       
		if (roles != null) {
			userRoleDao.deleteUserRoleByUserId(user.getUserId());
			for (Role role : roles) {//此处的顺序需要注意，要完成user的插入操作后才进行中间表的添加，不然中间表中的user-id会为0
				userRoleDao.addUserRole(role.getRoleId(), user.getUserId());
			}
		}
	       
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = 
					new UsernamePasswordToken(user.getUserName(), user.getPassword());
			
			subject.login(usernamePasswordToken);
			subject.checkRoles();
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
		}
			
		return result;
	}
    
	@Override
	public Result addtUser(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		User userTemp = userDao.getUserByUserName(user.getUserName());
     
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //将new Date()格式化为更易接受的格式且转换为字符串
		String time = df.format(new Date());
		user.setCreateDate(time);
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		userDao.insertUser(user);
		
		List<Role> roles = user.getRoles();
	       
		if (roles != null) {
			userRoleDao.deleteUserRoleByUserId(user.getUserId());
			for (Role role : roles) {//此处的顺序需要注意，要完成user的插入操作后才进行中间表的添加，不然中间表中的user-id会为0
				userRoleDao.addUserRole(role.getRoleId(), user.getUserId());
			}
		}
		
		if (user.getUserName() == "") {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("请输入用户名");
			return result;
		}else if (user.getPassword() == "") {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("请输入登录密码");
			return result;
		}else if(userTemp != null){//添加的用户名重复时，会弹出弹窗
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("用户名重复，请重命名用户名");
			return result;
		}
		
		if(roles.isEmpty()) {//此处设置为选择权限时，会弹出弹窗
        	result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("请至少选择一个权限");
			return result;
		}
		
		return result;
	}
	
	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public Result login(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = 
					new UsernamePasswordToken(user.getUserName(), MD5Util.getMD5(user.getPassword()));
			usernamePasswordToken.setRememberMe(user.getRememberMe());
			
			subject.login(usernamePasswordToken);
			subject.checkRoles();
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
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
		if (userTemp != null && user.getUserId() != userTemp.getUserId() ) {//1号
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("用户名重复。");
			return result;
		}
		
		userDao.updateUser(user);
		List<Role> roles = user.getRoles();
		if(!roles.isEmpty()) {
			userRoleDao.deleteUserRoleByUserId(user.getUserId());
			for (Role role : roles) {
				userRoleDao.addUserRole(role.getRoleId(), user.getUserId());
			}
		}else {//此处可拓展为至少选择2个或多个选项，用else if 来实现
			//userRoleDao.deleteUserRoleByUserId(user.getUserId());//roles为空时可删除中间表的所有有关信息
			//若是至少需要一个权限，则为空时提醒至少选择一个权限
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("请至少选择一个权限。");
		}
		return result;
	}

	@Override
	public Result deleteUser(int userId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		try {
			userDao.deleteUser(userId);
			userRoleDao.deleteUserRoleByUserId(userId);//删除user信息时，将中间表信息同时删除
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

}
