package com.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计数据项 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatItem {
    private String name;   // 名称
    private Long value;    // 数量
}

