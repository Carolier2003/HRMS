package com.hr.service;

import com.hr.entity.Role;
import com.hr.entity.User;
import com.hr.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 获取所有用户
     */
    public List<User> getAllUsers() {
        List<User> users = userMapper.findAllUsers();
        // 为每个用户加载角色信息
        for (User user : users) {
            List<Role> roles = userMapper.findRolesByUserId(user.getId());
            user.setRoles(roles);
        }
        return users;
    }

    /**
     * 根据ID获取用户
     */
    public User getUserById(Long id) {
        User user = userMapper.findById(id);
        if (user != null) {
            List<Role> roles = userMapper.findRolesByUserId(user.getId());
            user.setRoles(roles);
        }
        return user;
    }

    /**
     * 创建用户
     */
    @Transactional
    public User createUser(User user) {
        // 加密密码
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        // 插入用户
        userMapper.insert(user);
        
        // 分配角色
        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            for (Long roleId : user.getRoleIds()) {
                userMapper.insertUserRole(user.getId(), roleId);
            }
        }
        
        return user;
    }

    /**
     * 更新用户
     */
    @Transactional
    public User updateUser(User user) {
        // 如果提供了新密码，则加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // 不更新密码，保持原密码
            User existingUser = userMapper.findById(user.getId());
            if (existingUser != null) {
                user.setPassword(existingUser.getPassword());
            }
        }
        
        // 更新用户信息
        userMapper.update(user);
        
        // 更新角色分配
        if (user.getRoleIds() != null) {
            // 删除原有角色
            userMapper.deleteUserRoles(user.getId());
            // 添加新角色
            for (Long roleId : user.getRoleIds()) {
                userMapper.insertUserRole(user.getId(), roleId);
            }
        }
        
        return user;
    }

    /**
     * 删除用户
     */
    @Transactional
    public void deleteUser(Long id) {
        // 删除用户角色关联
        userMapper.deleteUserRoles(id);
        // 删除用户
        userMapper.delete(id);
    }
}
