package com.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 统计概览 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsOverview {
    private Long totalEmployees;      // 员工总数
    private Long onJobCount;          // 在职人数
    private Long leaveCount;          // 离职人数
    private List<StatItem> educationStats;     // 学历统计
    private List<StatItem> maritalStats;       // 婚姻状况统计
    private List<StatItem> positionStats;      // 岗位统计
    private List<StatItem> departmentStats;    // 部门统计
}

