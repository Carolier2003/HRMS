package com.hr.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工资项目实体
 */
@Data
public class SalaryItem {
    private Long id;
    private String name;  // 项目名称
    private String type;  // 项目类型：基本工资/奖金/扣款/其他
    private BigDecimal defaultAmount;  // 默认金额
    private Boolean enabled;  // 是否启用
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

