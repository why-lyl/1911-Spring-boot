package com.lyl.springBootDemo.modules.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.entity.Role;
import com.lyl.springBootDemo.modules.account.service.RoleService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping(value="/roles", consumes="application/json")
	public PageInfo<Role> getRolesBySearchVo(@RequestBody SearchVo searchVo) {
		return roleService.getRolesBySearchVo(searchVo);
	}
	
	@PostMapping(value = "/addRole", consumes = "application/json")
	public Result addRole(@RequestBody Role role) {
		return roleService.insertRole(role);
	}
	
	@RequestMapping("/role/{roleId}")
	public Role getRoleById(@PathVariable int roleId) {
		return roleService.getRoleByRoleId(roleId);
	}
	
	@PutMapping(value="/role", consumes="application/json")
	public Result updateRole(@RequestBody Role role) {
		return roleService.updateRole(role);
	}
	
	@DeleteMapping("/role/{roleId}")
	public Result deleteRole(@PathVariable int roleId) {
		return roleService.deleteRole(roleId);
	}
	
	@RequestMapping("/roles")
	public List<Role> getRoles() {
		return roleService.getRoles();
	}

}
