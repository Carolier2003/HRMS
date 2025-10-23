package com.hr.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户实体
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private Long employeeId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 角色列表（用于查询时返回）
    private List<Role> roles;
    
    // 角色ID列表（用于创建和更新时接收）
    private List<Long> roleIds;
}

