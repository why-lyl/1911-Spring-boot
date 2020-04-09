package com.lyl.springBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.account.entity.Resource;
import com.lyl.springBootDemo.modules.account.service.ResourceService;
import com.lyl.springBootDemo.modules.common.vo.Result;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;

@RestController
@RequestMapping("/api")
public class ResoursceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@PostMapping(value="/resources", consumes="application/json")
	public PageInfo<Resource> getResourcesBySearchVo(@RequestBody SearchVo searchVo) {
		return resourceService.getResourcesBySearchVo(searchVo);
	}
	
	@PostMapping(value = "/addResource", consumes = "application/json")
	public Result addresource(@RequestBody Resource resource) {
		return resourceService.insertResource(resource);
	}
	
	@RequestMapping("/resource/{resourceId}")
	public Resource getResourceById(@PathVariable int resourceId) {
		return resourceService.getResourceByResourceId(resourceId);
	}
	
	@PutMapping(value="/resource", consumes="application/json")
	public Result updateResource(@RequestBody Resource resource) {
		return resourceService.updateResource(resource);
	}
	
	@DeleteMapping("/resource/{resourceId}")
	public Result deleteresource(@PathVariable int resourceId) {
		return resourceService.deleteResource(resourceId);
	}

}
