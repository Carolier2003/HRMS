package com.hr.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工资记录实体
 */
@Data
public class SalaryRecord {
    private Long id;
    private Long employeeId;
    private String yearMonth;  // YYYY-MM
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deduction;
    private BigDecimal actualSalary;
    private String remark;
    private Long createdBy;
    private LocalDateTime createdAt;
}

