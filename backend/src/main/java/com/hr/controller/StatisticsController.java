package com.hr.controller;

import com.hr.common.R;
import com.hr.dto.StatisticsOverview;
import com.hr.dto.StatisticsVO;
import com.hr.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 统计控制器
 */
@RestController
@RequestMapping("/api/statistics")
@Tag(name = "统计报表", description = "员工学历、婚姻、部门等统计数据接口")
public class StatisticsController {
    
    private final StatisticsService statisticsService;
    
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
    
    @GetMapping("/education")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "学历分布统计")
    public R<List<StatisticsVO>> getEducationStatistics() {
        List<StatisticsVO> data = statisticsService.getEducationStatistics();
        return R.ok(data);
    }
    
    @GetMapping("/marital-status")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "婚姻状况统计")
    public R<List<StatisticsVO>> getMaritalStatusStatistics() {
        List<StatisticsVO> data = statisticsService.getMaritalStatusStatistics();
        return R.ok(data);
    }
    
    @GetMapping("/department")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "部门人数统计")
    public R<List<StatisticsVO>> getDepartmentStatistics() {
        List<StatisticsVO> data = statisticsService.getDepartmentStatistics();
        return R.ok(data);
    }
    
    @GetMapping("/overview")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "统计概览")
    public R<StatisticsOverview> getStatisticsOverview() {
        StatisticsOverview data = statisticsService.getStatisticsOverview();
        return R.ok(data);
    }
}

