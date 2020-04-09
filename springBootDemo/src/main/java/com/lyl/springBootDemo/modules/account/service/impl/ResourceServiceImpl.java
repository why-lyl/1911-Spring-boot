package com.lyl.springBootDemo.modules.account.service.impl;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.dao.ResourceDao;
import com.lyl.springBootDemo.modules.account.entity.Resource;
import com.lyl.springBootDemo.modules.account.service.ResourceService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;
import com.lyl.springBootDemo.modules.common.vo.Result.ResultStatus;


@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public Result insertResource(Resource resource) {
        Result result = new Result(ResultStatus.SUCCESS.status, "");
		
		Resource resourceTemp = resourceDao.getResourceByResourceName(resource.getResourceName());
		if (resourceTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("resource name is repeat.");
			return result;
		}
		
		resourceDao.insertResource(resource);
		
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
		if (resourceTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("resource name is repeat.");
			return result;
		}
		
		resourceDao.updateResource(resource);
		
		return result;
	}

	@Override
	public Result deleteResource(int resourceId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		try {
			resourceDao.deleteResource(resourceId);
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

	

}
