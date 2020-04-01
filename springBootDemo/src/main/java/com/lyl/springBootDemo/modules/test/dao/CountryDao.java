package com.lyl.springBootDemo.modules.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.lyl.springBootDemo.modules.test.entity.City;
import com.lyl.springBootDemo.modules.test.entity.Country;

@Repository
@Mapper
public interface CountryDao {
	
	//使用Mapper.xml文件配置的方法
	Country getCountryById2(int countryId);
	
	@Select("select * from m_country where country_id = #{countryId}")
	@Results(id = "countryResult", value = {//连表查询的结果集
			@Result(column = "country_id",property = "countryId"),
			@Result(column = "country_id",property = "cities",
			javaType = List.class,
			many = @Many(select = "com.lyl.springBootDemo.modules.test.dao."
					+ "CountryDao.getCitiesByCountryId"))
	})
	Country getCountryById(int countryId);
	
	@Select("select * from m_city where country_id = #{countryId}")
	List<City> getCitiesByCountryId(int countryId);
	
	@Select("select * from m_country where country_name = #{countryName}")
	@ResultMap(value = "countryResult")//前面已有需要使用的查询结果集，直接写id引用
	Country getCountryByname(String countryName);
	
	@Insert("insert into m_city (city_name, local_city_name, country_id, date_created) "
			+ "values (#{cityName}, #{localCityName}, #{countryId}, #{dateCreated})")
	@Options(useGeneratedKeys = true ,keyColumn = "city_id",keyProperty = "cityId")
	void insertCity(City city);
	
	@Update("update m_city set local_city_name = #{localCityName} where city_id = #{cityId}")
	void updateCity(City city);
	
	@Delete("delete from m_city where city_id = #{cityId}")
	void deleteCity(int cityId);

}
