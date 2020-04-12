package com.lyl.springBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.lyl.springBootDemo.modules.account.entity.Resource;
import com.lyl.springBootDemo.modules.common.vo.SearchVo;



@Repository
@Mapper
public interface ResourceDao {
	
	    //插入资源信息
		@Insert("insert into m_resource (resource_uri,resource_name,permission) "
				+ "values (#{resourceUri},#{resourceName},#{permission})")
		/**
		 * useGeneratedKeys设置为 true时，表示如果插入的表id以自增列为主键，则允许 JDBC 支持自动生成主键，
		 * 并可将自动生成的主键id返回。useGeneratedKeys参数只针对 insert 语句生效，默认为 false
		 * 
		 */
		@Options(useGeneratedKeys=true, keyColumn="resource_id", keyProperty="resourceId")
		void insertResource(Resource resource);
		
		//根据名字查出资源信息
		@Select("select * from m_resource where resource_name = #{resourceName}")
		Resource getResourceByResourceName(String resourceName);
		
		//根据id查出资源信息
		@Select("select * from m_resource where resource_id = #{resourceId}")
		@Results(id="resourceResult", value={
				@Result(column="resource_id", property="resourceId"),
				@Result(column="resource_id",property="roles",
						javaType=List.class,
						many=@Many(select="com.lyl.springBootDemo.modules.account.dao."
								+ "RoleDao.getRolesByResourceId"))
			})
		Resource getResourceByResourceId(int resourceId);
		
		//查询分页
		@Select("<script>" + 
				"select * from m_resource "
				+ "<where> "
				+ "<if test='keyWord != \"\" and keyWord != null'>"
				+ "and resource_name like '%${keyWord}%' "
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
		List<Resource> getResourcesBySearchVo(SearchVo searchVo);

		
		/**
		 * 根据id更新resource的resourceName
		 * 此处可以添加更多sql语句修改更多内容，配合前端的内容改写可以在前端修改响应的更多内容
		 * 暂写名字修改
		 */
		@Update("update m_resource set resource_name=#{resourceName} where resource_id=#{resourceId}")
		void updateResource(Resource resource);
			
		//根据id删除resource的信息
		@Select("delete from m_resource where resource_id=#{resourceId}")
		void deleteResource(int resourceId);
		
		//根据id查出resource的名字
		@Select("select resource_name from m_resource where resource_id = #{resourceId}")
		String getResourceNameByResourceId(int resourceId);
		
		//得到resource的信息集合
		@Select("select * from m_resource")
		List<Resource> getResources();
		
		//根据role Id 获得resource信息
		@Select("select * from m_resource resource left join m_role_resource roleResource on "
				+ "resource.resource_id = roleResource.resource_id where roleResource.role_id = #{roleId}")
		List<Resource> getResourcesByRoleId(int roleId);

}
