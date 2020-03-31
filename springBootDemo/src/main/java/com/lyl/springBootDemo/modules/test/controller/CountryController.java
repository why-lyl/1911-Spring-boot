package com.lyl.springBootDemo.modules.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyl.springBootDemo.modules.test.entity.Country;
import com.lyl.springBootDemo.modules.test.service.CountryService;
@RestController
public class CountryController {
	@Autowired
	private CountryService countryService;
	/**
	 * 127.0.0.1:8688/country/522
	 *https://127.0.0.1:8868/country/522
	 */
	@RequestMapping("/country/{countryId}")
	public Country getCountryById(@PathVariable int countryId) {
		return countryService.getCountryById(countryId);
	}


}
