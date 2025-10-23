package com.hr.mapper;

import com.hr.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 角色 Mapper
 */
@Mapper
public interface RoleMapper {
    
    @Select("SELECT id, role_name AS roleName, role_code AS roleCode, description, created_at AS createdAt " +
            "FROM roles ORDER BY created_at DESC")
    List<Role> findAll();
    
    @Select("SELECT id, role_name AS roleName, role_code AS roleCode, description, created_at AS createdAt " +
            "FROM roles WHERE id = #{id}")
    Role findById(Long id);
    
    @Insert("INSERT INTO roles (role_name, role_code, description) " +
            "VALUES (#{roleName}, #{roleCode}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Role role);
    
    @Update("UPDATE roles SET role_name = #{roleName}, role_code = #{roleCode}, " +
            "description = #{description} WHERE id = #{id}")
    int update(Role role);
    
    @Delete("DELETE FROM roles WHERE id = #{id}")
    int delete(Long id);
}