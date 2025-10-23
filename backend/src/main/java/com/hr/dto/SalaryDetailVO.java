package com.hr.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 工资明细视图对象
 */
@Data
public class SalaryDetailVO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String employeeNo;
    private String yearMonth;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deduction;
    private BigDecimal actualSalary;
    private String remark;
    private List<ItemDetail> items;
    
    @Data
    public static class ItemDetail {
        private String itemName;
        private String itemType;
        private BigDecimal amount;
    }
}

