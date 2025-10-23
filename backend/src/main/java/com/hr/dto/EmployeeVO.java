package com.hr.dto;

import com.hr.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

/**
 * 员工视图对象
 */
@Data
@NoArgsConstructor
public class EmployeeVO {
    private Long id;
    private String employeeNo;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String education;
    private String maritalStatus;
    private String department;
    private String position;
    private LocalDate entryDate;
    private String status;
    
    public static EmployeeVO fromEntity(Employee employee) {
        EmployeeVO vo = new EmployeeVO();
        BeanUtils.copyProperties(employee, vo);
        return vo;
    }
}

