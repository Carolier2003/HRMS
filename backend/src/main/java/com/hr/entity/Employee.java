package com.hr.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体
 */
@Data
public class Employee {
    private Long id;
    private String employeeNo;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String idCard;
    private String phone;
    private String email;
    private String address;
    
    // 学历信息
    private String education;
    private String major;
    private String graduateSchool;
    private LocalDate graduateDate;
    
    // 婚姻状况
    private String maritalStatus;
    
    // 岗位信息
    private String department;
    private String position;
    private String jobTitle;
    private LocalDate entryDate;
    private Integer workYears;
    
    // 状态信息
    private String status;
    private LocalDate leaveDate;
    private String leaveReason;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

