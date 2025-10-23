package com.hr.service;

import com.hr.dto.LoginRequest;
import com.hr.dto.LoginResponse;
import com.hr.entity.Employee;
import com.hr.entity.Role;
import com.hr.entity.User;
import com.hr.exception.BusinessException;
import com.hr.mapper.EmployeeMapper;
import com.hr.mapper.UserMapper;
import com.hr.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证服务
 */
@Service
public class AuthService {
    
    private final UserMapper userMapper;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthService(UserMapper userMapper, EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    /**
     * 用户登录
     */
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new BusinessException("账号已被禁用");
        }
        
        // 获取用户角色
        List<Role> roles = userMapper.findRolesByUserId(user.getId());
        List<String> roleCodes = roles.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());
        
        // 生成 token
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername(), roleCodes);
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());
        
        // 获取员工信息以提供更准确的用户信息
        String displayName = user.getRealName();
        if (user.getEmployeeId() != null) {
            Employee employee = employeeMapper.findById(user.getEmployeeId());
            if (employee != null) {
                displayName = employee.getName();
            }
        }
        
        return new LoginResponse(accessToken, refreshToken, user.getId(), 
                user.getUsername(), displayName, roleCodes);
    }
    
    /**
     * 刷新令牌
     */
    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new BusinessException("刷新令牌无效或已过期");
        }
        
        // 验证token类型
        String tokenType = jwtUtil.parseToken(refreshToken).get("type", String.class);
        if (!"refresh".equals(tokenType)) {
            throw new BusinessException("无效的刷新令牌类型");
        }
        
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        User user = userMapper.findByUsername(username);
        
        if (user == null || !"ACTIVE".equals(user.getStatus())) {
            throw new BusinessException("用户不存在或已被禁用");
        }
        
        List<Role> roles = userMapper.findRolesByUserId(user.getId());
        List<String> roleCodes = roles.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());
        
        String newAccessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername(), roleCodes);
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());
        
        // 获取员工信息以提供更准确的用户信息
        String displayName = user.getRealName();
        if (user.getEmployeeId() != null) {
            Employee employee = employeeMapper.findById(user.getEmployeeId());
            if (employee != null) {
                displayName = employee.getName();
            }
        }
        
        return new LoginResponse(newAccessToken, newRefreshToken, user.getId(), 
                user.getUsername(), displayName, roleCodes);
    }
}

