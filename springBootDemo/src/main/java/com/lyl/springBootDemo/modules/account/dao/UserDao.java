package com.lyl.springBootDemo.modules.account.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.lyl.springBootDemo.modules.account.entity.User;

@Repository
@Mapper
public interface UserDao {

	@Insert("insert into m_user (user_name, password, create_date) "
			+ "values (#{userName}, #{password}, #{createDate})")
	@Options(useGeneratedKeys=true, keyColumn="user_id", keyProperty="userId")
	void insertUser(User user);
	
	@Select("select * from m_user where user_name = #{userName}")
	User getUserByUserName(String userName);
	
	@Select("select * from m_user where user_name=#{userName} and password=#{password}")
	User getUser(User user);
}
