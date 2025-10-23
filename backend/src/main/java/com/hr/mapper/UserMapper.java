package com.hr.mapper;

import com.hr.entity.Role;
import com.hr.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户 Mapper
 */
@Mapper
public interface UserMapper {
    
    @Select("SELECT u.id, u.username, u.password, u.real_name AS realName, u.email, u.phone, u.employee_id AS employeeId, " +
            "CASE WHEN u.status = 1 THEN 'ACTIVE' ELSE 'INACTIVE' END AS status, " +
            "u.created_at AS createdAt, u.updated_at AS updatedAt " +
            "FROM users u WHERE u.username = #{username}")
    User findByUsername(String username);
    
    @Select("SELECT u.id, u.username, u.real_name AS realName, u.email, u.phone, u.employee_id AS employeeId, " +
            "CASE WHEN u.status = 1 THEN 'ACTIVE' ELSE 'INACTIVE' END AS status, " +
            "u.created_at AS createdAt, u.updated_at AS updatedAt " +
            "FROM users u WHERE u.id = #{id}")
    User findById(Long id);
    
    @Select("SELECT u.id, u.username, u.real_name AS realName, u.email, u.phone, u.employee_id AS employeeId, " +
            "CASE WHEN u.status = 1 THEN 'ACTIVE' ELSE 'INACTIVE' END AS status, " +
            "u.created_at AS createdAt, u.updated_at AS updatedAt " +
            "FROM users u ORDER BY u.created_at DESC")
    List<User> findAllUsers();
    
    @Select("SELECT r.id, r.role_name AS roleName, r.role_code AS roleCode, r.description, r.created_at AS createdAt " +
            "FROM roles r INNER JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<Role> findRolesByUserId(Long userId);
    
    @Insert("INSERT INTO users (username, password, real_name, email, phone, employee_id, status) " +
            "VALUES (#{username}, #{password}, #{realName}, #{email}, #{phone}, #{employeeId}, " +
            "CASE WHEN #{status} = 'ACTIVE' THEN 1 ELSE 0 END)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Update("UPDATE users SET password = #{password}, real_name = #{realName}, " +
            "email = #{email}, phone = #{phone}, status = CASE WHEN #{status} = 'ACTIVE' THEN 1 ELSE 0 END WHERE id = #{id}")
    int update(User user);
    
    @Delete("DELETE FROM users WHERE id = #{id}")
    int delete(Long id);
    
    @Insert("INSERT INTO user_roles (user_id, role_id) VALUES (#{userId}, #{roleId})")
    int insertUserRole(Long userId, Long roleId);
    
    @Delete("DELETE FROM user_roles WHERE user_id = #{userId}")
    int deleteUserRoles(Long userId);
}

