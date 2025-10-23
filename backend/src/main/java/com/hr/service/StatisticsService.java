package com.hr.service;

import com.hr.dto.StatItem;
import com.hr.dto.StatisticsOverview;
import com.hr.dto.StatisticsVO;
import com.hr.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统计服务
 */
@Service
public class StatisticsService {
    
    private final EmployeeMapper employeeMapper;
    
    public StatisticsService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }
    
    /**
     * 学历分布统计
     */
    public List<StatisticsVO> getEducationStatistics() {
        List<Map<String, Object>> data = employeeMapper.statisticsByEducation();
        return data.stream()
                .map(map -> new StatisticsVO(
                        (String) map.get("education"),
                        ((Number) map.get("count")).longValue()
                ))
                .collect(Collectors.toList());
    }
    
    /**
     * 婚姻状况统计
     */
    public List<StatisticsVO> getMaritalStatusStatistics() {
        List<Map<String, Object>> data = employeeMapper.statisticsByMaritalStatus();
        return data.stream()
                .map(map -> new StatisticsVO(
                        (String) map.get("marital_status"),
                        ((Number) map.get("count")).longValue()
                ))
                .collect(Collectors.toList());
    }
    
    /**
     * 部门人数统计
     */
    public List<StatisticsVO> getDepartmentStatistics() {
        List<Map<String, Object>> data = employeeMapper.statisticsByDepartment();
        return data.stream()
                .map(map -> new StatisticsVO(
                        (String) map.get("department"),
                        ((Number) map.get("count")).longValue()
                ))
                .collect(Collectors.toList());
    }
    
    /**
     * 统计概览
     */
    public StatisticsOverview getStatisticsOverview() {
        // 基础统计
        Long totalEmployees = employeeMapper.countTotal();
        Long onJobCount = employeeMapper.countOnJob();
        Long leaveCount = employeeMapper.countLeave();
        
        // 学历统计
        List<StatItem> educationStats = employeeMapper.statisticsByEducation().stream()
                .map(map -> new StatItem(
                        (String) map.get("education"),
                        ((Number) map.get("count")).longValue()
                ))
                .collect(Collectors.toList());
        
        // 婚姻状况统计
        List<StatItem> maritalStats = employeeMapper.statisticsByMaritalStatus().stream()
                .map(map -> new StatItem(
                        (String) map.get("marital_status"),
                        ((Number) map.get("count")).longValue()
                ))
                .collect(Collectors.toList());
        
        // 岗位统计
        List<StatItem> positionStats = employeeMapper.statisticsByPosition().stream()
                .map(map -> new StatItem(
                        (String) map.get("position"),
                        ((Number) map.get("count")).longValue()
                ))
                .collect(Collectors.toList());
        
        // 部门统计
        List<StatItem> departmentStats = employeeMapper.statisticsByDepartment().stream()
                .map(map -> new StatItem(
                        (String) map.get("department"),
                        ((Number) map.get("count")).longValue()
                ))
                .collect(Collectors.toList());
        
        return new StatisticsOverview(
                totalEmployees,
                onJobCount,
                leaveCount,
                educationStats,
                maritalStats,
                positionStats,
                departmentStats
        );
    }
}

