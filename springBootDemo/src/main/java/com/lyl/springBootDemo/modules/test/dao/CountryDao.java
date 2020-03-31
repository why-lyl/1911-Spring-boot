package com.lyl.springBootDemo.modules.test.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.lyl.springBootDemo.modules.test.entity.Country;

@Repository
@Mapper
public interface CountryDao {
	
	@Select("select * from m_country where country_id = #{countryId}")
	Country getCountryById(int countryId);

}
