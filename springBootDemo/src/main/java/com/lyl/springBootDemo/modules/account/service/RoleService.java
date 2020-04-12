package com.lyl.springBootDemo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.entity.Role;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;

public interface RoleService {
	
	Result insertRole(Role role);
	
	Role getRoleByRoleName(String roleName);
	
	Role getRoleByRoleId(int roleId);
	
	PageInfo<Role> getRolesBySearchVo(SearchVo searchVo);
	
	Result updateRole(Role role);
	
	Result deleteRole(int roleId);

	List<Role> getRoles();
	
	List<Role> getRolesByUserId(int userId);
	
	List<Role> getRolesByResourceId(int resourceId);
	
	
	

}
