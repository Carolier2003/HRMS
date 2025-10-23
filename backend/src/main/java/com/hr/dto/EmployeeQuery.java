package com.hr.dto;

import lombok.Data;

/**
 * 员工查询 DTO
 */
@Data
public class EmployeeQuery {
    private String name;
    private String department;
    private String education;
    private String maritalStatus;
    private String position;
    private String status;  // 不设置默认值，允许查询所有状态的员工
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}

