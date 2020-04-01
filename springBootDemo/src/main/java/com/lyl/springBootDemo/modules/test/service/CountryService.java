package com.lyl.springBootDemo.modules.test.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lyl.springBootDemo.modules.test.entity.City;
import com.lyl.springBootDemo.modules.test.entity.Country;

public interface CountryService {
	
	Country getCountryById(int countryId);
	
	List<City> getCitiesByCountryId(int countryId);
	
	Country getCountryByname(String countryName);
	
	PageInfo<City> getCitiesByPage(int countryId,int currentPage,int pageSize);
	
	City insertCity(City city);
	
	City updateCity(City city);
	
	void deleteCity(int cityId);

	

}
