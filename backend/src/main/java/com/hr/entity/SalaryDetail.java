package com.hr.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 工资明细实体
 */
@Data
public class SalaryDetail {
    private Long id;
    private Long salaryRecordId;
    private Long itemId;
    private BigDecimal amount;
    
    // 关联的工资项目信息（用于查询时填充）
    private String itemName;
    private String itemType;
}

