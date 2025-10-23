package com.hr.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 角色实体
 */
@Data
public class Role {
    private Long id;
    private String roleName;
    private String roleCode;
    private String description;
    private LocalDateTime createdAt;
}

