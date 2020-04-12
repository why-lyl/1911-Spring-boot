package com.lyl.springBootDemo.modules.account.service.impl;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.dao.ResourceDao;
import com.lyl.springBootDemo.modules.account.dao.RoleResourceDao;
import com.lyl.springBootDemo.modules.account.entity.Resource;
import com.lyl.springBootDemo.modules.account.entity.Role;
import com.lyl.springBootDemo.modules.account.service.ResourceService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;
import com.lyl.springBootDemo.modules.common.vo.Result.ResultStatus;


@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private RoleResourceDao roleResourceDao;

	@Override
	public Result insertResource(Resource resource) {
        Result result = new Result(ResultStatus.SUCCESS.status, "");
        
        Resource resourceTemp = resourceDao.getResourceByResourceName(resource.getResourceName());
        
		if (resource.getResourceName() == "") {//此处设置使未填写资源名时，弹出弹窗
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("资源名为必须添加字段，请添加资源名");
			return result;
		}else if(resourceTemp != null){//添加的资源名重复时，会弹出弹窗。
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("资源名重复，请重命名资源名");
			return result;
		}
        
		List<Role> roles = resource.getRoles();
		
        if(roles.isEmpty()) {//此处设置为选择权限时，会弹出弹窗
        	result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("请至少选择一个权限");
			return result;
		}
        
		resourceDao.insertResource(resource);
		
		for (Role role : roles) {//此处的顺序需要注意，要完成resource的插入操作后才进行中间表的添加，不然中间表中的resoource-id会为0
			roleResourceDao.addRoleResource(role.getRoleId(), resource.getResourceId());
		}
		return result;
	}

	@Override
	public Resource getResourceByResourceName(String resourceName) {
		return resourceDao.getResourceByResourceName(resourceName);
	}

	@Override
	public Resource getResourceByResourceId(int resourceId) {
		return resourceDao.getResourceByResourceId(resourceId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo(searchVo);
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		List<Resource> resources = Optional.ofNullable(resourceDao.getResourcesBySearchVo(searchVo))
				.orElse(Collections.emptyList());
		return new PageInfo(resources);
	}
	
	@Override
	public Result updateResource(Resource resource) {
		
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		Resource resourceTemp = resourceDao.getResourceByResourceName(resource.getResourceName());
		if (resourceTemp != null && resource.getResourceId() != resourceTemp.getResourceId()) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("资源名重复");
			return result;
		}
		
		resourceDao.updateResource(resource);
		
		List<Role> roles = resource.getRoles();
		if(!roles.isEmpty()) {
			roleResourceDao.deletRoleResourceByResourceId(resource.getResourceId());
			for (Role role : roles) {
				roleResourceDao.addRoleResource(role.getRoleId(), resource.getResourceId());
			}
		}else {//此处可拓展为至少选择2个或多个选项，用else if 来实现
			//roleResourceDao.deletRoleResourceByResourceId(resource.getResourceId());//resource为空时可删除中间表的所有有关信息
			//若是至少需要一个权限，则为空时提醒至少选择一个权限
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("请至少选择一个权限");
		}
		
		return result;
	}

	@Override
	public Result deleteResource(int resourceId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		try {
			resourceDao.deleteResource(resourceId);
			roleResourceDao.deletRoleResourceByResourceId(resourceId);
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public List<Resource> getResourcesByRoleId(int roleId) {
		return resourceDao.getResourcesByRoleId(roleId);
	}

	@Override
	public Resource getResourceById(int resourceId) {
		return resourceDao.getResourceByResourceId(resourceId);
	}
	

}
