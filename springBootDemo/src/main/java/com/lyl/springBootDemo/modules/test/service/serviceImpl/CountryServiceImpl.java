package com.lyl.springBootDemo.modules.test.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.springBootDemo.modules.test.dao.CountryDao;
import com.lyl.springBootDemo.modules.test.entity.Country;
import com.lyl.springBootDemo.modules.test.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
    
	@Autowired
	private CountryDao countryDao;
	@Override
	public Country getCountryById(int countryId) {
		return countryDao.getCountryById(countryId);
	}

}
