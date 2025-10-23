package com.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsVO {
    private String name;
    private Long count;
}

