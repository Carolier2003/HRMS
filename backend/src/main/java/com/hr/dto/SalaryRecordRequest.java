package com.hr.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 工资记录请求 DTO
 */
@Data
public class SalaryRecordRequest {
    private Long employeeId;
    private String yearMonth;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deduction;
    private BigDecimal actualSalary;
    private String remark;
    private List<SalaryItemDetail> details;
    
    @Data
    public static class SalaryItemDetail {
        private Long itemId;
        private BigDecimal amount;
    }
}

