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
	
	//插入角色,因为shrio验证注册时与添加时有冲突，所以添加了这个接口
	@Insert("insert into m_role (role_name) values (#{roleName})")
	@Options(useGeneratedKeys=true, keyColumn="role_id", keyProperty="roleId")
	void addtRole(Role role);
	
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
	@Update("update m_role set role_name=#{roleName} where role_id=#{roleId}")
	void updateRole(Role role);
	
	//根据id删除role的信息
	@Select("delete from m_role where role_id=#{roleId}")
	void deleteRole(int roleId);
	
	//查询role所有信息到集合中
	@Select("select * from m_role")
	List<Role> getRoles();
	
	//根据resource Id 查询出roles信息
	@Select("select * from m_role role left join m_role_resource roleResource "
			+ "on role.role_id = roleResource.role_id where roleResource.resource_id = #{resourceId}")
	List<Role> getRolesByResourceId(int resourceId);
	
	//根据user Id 查询出roles信息
	@Select("select * from m_role role left join m_user_role userRole "
			+ "on role.role_id = userRole.role_id where userRole.user_id = #{userId}")
	List<Role> getRolesByUserId(int userId);
	
	
	
	
}
