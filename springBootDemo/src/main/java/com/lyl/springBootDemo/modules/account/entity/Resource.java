package com.lyl.springBootDemo.modules.account.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 资源类
 * @author: HymanHu
 * @date: 2019年11月28日
 */
@Entity
@Table(name = "m_resource")//生成表所需注解
public class Resource {

	@Id//声明主键注解
	@GeneratedValue(strategy = GenerationType.IDENTITY)//自增注解
	private int resourceId;
	private String resourceUri;
	private String resourceName;
	private String permission;

	@Transient//表明以下内容与生成表无关
	private List<Role> roles;

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
