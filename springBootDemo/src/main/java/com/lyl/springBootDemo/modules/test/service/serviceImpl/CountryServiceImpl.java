package com.lyl.springBootDemo.modules.test.service.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.test.dao.CountryDao;
import com.lyl.springBootDemo.modules.test.entity.City;
import com.lyl.springBootDemo.modules.test.entity.Country;
import com.lyl.springBootDemo.modules.test.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
    
	@Autowired
	private CountryDao countryDao;
	
	@Override
	public Country getCountryById(int countryId) {
		//直接在dao层注释的方法
		return countryDao.getCountryById(countryId);
		//Mapper.xml配置的方法
		//return countryDao.getCountryById2(countryId);
	}
	
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		//原本写法
		//return countryDao.getCitiesByCountryId(countryId);
		//jdk1.8新写法，防止空,集合时用
		return Optional.ofNullable(countryDao.getCitiesByCountryId(countryId)).
				orElse(Collections.emptyList());
		
	}
	
	@Override
	public Country getCountryByname(String countryName) {
		return countryDao.getCountryByname(countryName);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<City> getCitiesByPage(int countryId, int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<City> cities = Optional.ofNullable(countryDao.getCitiesByCountryId(countryId)).
		orElse(Collections.emptyList());
		return new PageInfo(cities);
	}
	
	@Transactional
	@Override
	public City insertCity(City city) {
		countryDao.insertCity(city);
		return city;
	}
    
	@Transactional
	@Override
	public City updateCity(City city) {
		countryDao.updateCity(city);
		return city;
	}
    
	@Transactional
	@Override
	public void deleteCity(int cityId) {
		countryDao.deleteCity(cityId);
	}
    
	
}
