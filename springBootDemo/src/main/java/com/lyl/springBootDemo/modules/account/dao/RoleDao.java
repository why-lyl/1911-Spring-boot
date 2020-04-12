package com.lyl.springBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.lyl.springBootDemo.modules.account.entity.Role;
import com.lyl.springBootDemo.modules.account.entity.User;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;


@Repository
@Mapper
public interface RoleDao {
	
	//插入角色
	@Insert("insert into m_role (role_name) values (#{roleName})")
	@Options(useGeneratedKeys=true, keyColumn="role_id", keyProperty="roleId")
	void insertRole(Role role);
	
	//根据角色查看权限等级
	@Select("select * from m_role where role_name = #{roleName}")
	Role getRoleByRoleName(String roleName);
	
	//根据权限等级查看角色，
	@Select("select * from m_role where role_id = #{roleId}")
	Role getRoleByRoleId(int roleId);
	
	//查询分页
	@Select("<script>" + 
			"select * from m_role "
			+ "<where> "
			+ "<if test='keyWord != \"\" and keyWord != null'>"
			+ "and role_name like '%${keyWord}%' "
			+ "</if>"
			+ "</where>"
			+ "<choose>"
			+ "<when test='orderBy != \"\" and orderBy != null'>"
			+ "order by ${orderBy} ${sort}"
			+ "</when>"
			+ "<otherwise>"
			+ "order by create_date desc"
			+ "</otherwise>"
			+ "</choose>"
			+ "</script>")
	List<Role> getRolesBySearchVo(SearchVo searchVo);
	
	//根据id更新role的roleName
	@Update("update m_role set role_name=#{roleName} where role_id=#{userId}")
	void updateRole(Role role);
	
	//根据id删除role的信息
	@Select("delete from m_role where role_id=#{roleId}")
	void deleteRole(int roleId);
	
	
	
	
	
	
}
