package com.lyl.springBootDemo.modules.account.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.dao.RoleDao;
import com.lyl.springBootDemo.modules.account.entity.Role;
import com.lyl.springBootDemo.modules.account.service.RoleService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;
import com.lyl.springBootDemo.modules.common.vo.Result.ResultStatus;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public Result insertRole(Role role) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		if (role.getRoleName() == "") {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("请输入权限角色名");
			return result;
		}
		
		roleDao.insertRole(role);
		
		return result;
	}

	@Override
	public Role getRoleByRoleName(String roleName) {
		return roleDao.getRoleByRoleName(roleName);
	}

	@Override
	public Role getRoleByRoleId(int roleId) {
		return roleDao.getRoleByRoleId(roleId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo<Role> getRolesBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo(searchVo);
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		List<Role> roles = Optional.ofNullable(roleDao.getRolesBySearchVo(searchVo))
				.orElse(Collections.emptyList());
		return new PageInfo(roles);
	}
	
	@Override
	public Result updateRole(Role role) {
		
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		Role roleTemp = roleDao.getRoleByRoleName(role.getRoleName());
		if (roleTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("role name is repeat.");
			return result;
		}
		
		roleDao.updateRole(role);
		
		return result;
	}

	@Override
	public Result deleteRole(int roleId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		try {
			roleDao.deleteRole(roleId);
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public List<Role> getRoles() {
		return roleDao.getRoles();
	}
	
	@Override
	public List<Role> getRolesByUserId(int userId) {
		return roleDao.getRolesByUserId(userId);
	}

	@Override
	public List<Role> getRolesByResourceId(int resourceId) {
		return roleDao.getRolesByResourceId(resourceId);
	}


}
