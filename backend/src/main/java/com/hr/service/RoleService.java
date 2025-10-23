package com.hr.service;

import com.hr.entity.Role;
import com.hr.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色服务
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;

    /**
     * 获取所有角色
     */
    public List<Role> getAllRoles() {
        return roleMapper.findAll();
    }

    /**
     * 根据ID获取角色
     */
    public Role getRoleById(Long id) {
        return roleMapper.findById(id);
    }

    /**
     * 创建角色
     */
    @Transactional
    public Role createRole(Role role) {
        roleMapper.insert(role);
        return role;
    }

    /**
     * 更新角色
     */
    @Transactional
    public Role updateRole(Role role) {
        roleMapper.update(role);
        return role;
    }

    /**
     * 删除角色
     */
    @Transactional
    public void deleteRole(Long id) {
        roleMapper.delete(id);
    }
}
