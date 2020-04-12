package com.lyl.springBootDemo.modules.account.service;


import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.entity.Resource;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;

public interface ResourceService {
	
	Result insertResource(Resource resource);
	
	Resource getResourceByResourceName(String resourceName);
	
	Resource getResourceByResourceId(int resourceId);
	
	PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo);
	
	Result updateResource(Resource resource);
	
	Result deleteResource(int resourceId);

}
