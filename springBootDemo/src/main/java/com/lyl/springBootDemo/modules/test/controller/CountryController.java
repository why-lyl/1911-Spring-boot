package com.lyl.springBootDemo.modules.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.test.entity.City;
import com.lyl.springBootDemo.modules.test.entity.Country;
import com.lyl.springBootDemo.modules.test.service.CountryService;
@RestController
public class CountryController {
	@Autowired
	private CountryService countryService;
	/**
	 * 127.0.0.1:8688/country/522
	 * https://127.0.0.1:8868/country/522
	 */
	@RequestMapping("/country/{countryId}")
	public Country getCountryById(@PathVariable int countryId) {
		return countryService.getCountryById(countryId);
	}
	
	/**
	 * 127.0.0.1:8688/cities/522
	 * https://127.0.0.1:8868/cities/522
	 */
	@RequestMapping("/cities/{countryId}")
	public List<City> getCitiesByCountryId(@PathVariable int countryId){
		return countryService.getCitiesByCountryId(countryId);
		
	}
	

	/**
	 * 通过国家名字查询
	 * 127.0.0.1:8688/country?countryName=China
	 * https://127.0.0.1:8868/country?countryName=China
	 */
	@RequestMapping("/country")
	public Country getCountryByname(@RequestParam String countryName) {
		return countryService.getCountryByname(countryName);
	}
	
	/**
	 * 分页
	 * 127.0.0.1:8688/cities?countryId=522&currentPage=1&pageSize=5
	 * https://127.0.0.1:8868/cities?countryId=522&currentPage=1&pageSize=5
	 */
	@RequestMapping("/cities")
	public PageInfo<City> getCitiesByPage(@RequestParam int countryId, 
			@RequestParam int currentPage, @RequestParam int pageSize){
		return countryService.getCitiesByPage(countryId, currentPage, pageSize);
		
	}
	
	/**
	 * 插入
	 * 127.0.0.1:8688/city
	 * https://127.0.0.1:8868/city
	 * json字符串
	 * {"cityName":"konghai","countryId":"522","dateCreated":"2020-04-01 21:55:02","localCityName":"jiange"}
	 * @RequestMapping(value = "/city", method = RequestMethod.POST,consumes = "application/json")
	 */
	@PostMapping(value = "/city",consumes = "application/json")
	public City insertCity(@RequestBody City city) {
		countryService.insertCity(city);
		return city;
	}
	
	/**
	 * 修改
	 * 当请求方式不一致时，路径可以相同
	 * form表单
	 * 127.0.0.1:8688/city
	 * https://127.0.0.1:8868/city
	 */
	@PutMapping(value = "/city",consumes = "application/x-www-form-urlencoded")
	public City updateCity(@ModelAttribute City city) {
		countryService.updateCity(city);
		return city;
	}
	
	/**
	 * 删除
	 * 当请求方式不一致时，路径可以相同
	 * 127.0.0.1:8688/city/2255
	 * https://127.0.0.1:8868/city/2255
	 */
	@DeleteMapping("/city/{cityId}")
	public void deleteCity(@PathVariable int cityId) {
		countryService.deleteCity(cityId);
	}


}
