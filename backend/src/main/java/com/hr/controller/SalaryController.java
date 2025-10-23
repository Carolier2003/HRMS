package com.hr.controller;

import com.hr.common.R;
import com.hr.dto.SalaryDetailVO;
import com.hr.dto.SalaryRecordRequest;
import com.hr.entity.SalaryItem;
import com.hr.entity.SalaryRecord;
import com.hr.service.SalaryService;
import com.hr.util.JwtUtil;
import com.hr.util.PdfUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 工资控制器
 */
@RestController
@RequestMapping("/api/salary")
@Tag(name = "工资管理", description = "工资项目、工资记录、工资条查询和导出等接口")
public class SalaryController {
    
    private final SalaryService salaryService;
    private final JwtUtil jwtUtil;
    
    public SalaryController(SalaryService salaryService, JwtUtil jwtUtil) {
        this.salaryService = salaryService;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping("/items")
    @Operation(summary = "获取所有工资项")
    public R<List<SalaryItem>> getAllItems() {
        List<SalaryItem> items = salaryService.getAllItems();
        return R.ok(items);
    }
    
    @PostMapping("/items")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "添加工资项")
    public R<SalaryItem> addItem(@RequestBody SalaryItem item) {
        SalaryItem result = salaryService.addItem(item);
        return R.ok(result);
    }
    
    @PutMapping("/items/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "更新工资项")
    public R<SalaryItem> updateItem(@PathVariable Long id, @RequestBody SalaryItem item) {
        item.setId(id);
        SalaryItem result = salaryService.updateItem(item);
        return R.ok(result);
    }
    
    @DeleteMapping("/items/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "删除工资项")
    public R<Void> deleteItem(@PathVariable Long id) {
        salaryService.deleteItem(id);
        return R.ok();
    }
    
    @PostMapping("/records")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "创建工资记录")
    public R<SalaryRecord> createRecord(@RequestBody SalaryRecordRequest request, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        SalaryRecord result = salaryService.createSalaryRecord(request, userId);
        return R.ok(result);
    }
    
    @GetMapping("/records/employee/{employeeId}")
    @Operation(summary = "查询员工工资记录列表")
    public R<List<SalaryRecord>> getEmployeeRecords(@PathVariable Long employeeId) {
        List<SalaryRecord> records = salaryService.getEmployeeSalaryRecords(employeeId);
        return R.ok(records);
    }
    
    @GetMapping("/records/{recordId}")
    @Operation(summary = "查询工资记录明细")
    public R<SalaryDetailVO> getRecordDetail(@PathVariable Long recordId) {
        SalaryDetailVO detail = salaryService.getSalaryDetail(recordId);
        return R.ok(detail);
    }
    
    @GetMapping("/records/month/{yearMonth}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "按月查询所有工资记录")
    public R<List<SalaryRecord>> getRecordsByMonth(@PathVariable String yearMonth) {
        List<SalaryRecord> records = salaryService.getRecordsByMonth(yearMonth);
        return R.ok(records);
    }
    
    @DeleteMapping("/records/{recordId}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "删除工资记录")
    public R<Void> deleteRecord(@PathVariable Long recordId) {
        salaryService.deleteSalaryRecord(recordId);
        return R.ok();
    }
    
    @GetMapping("/records/{recordId}/pdf")
    @Operation(summary = "导出工资条PDF")
    public void exportPdf(@PathVariable Long recordId, HttpServletResponse response) throws IOException {
        SalaryDetailVO detail = salaryService.getSalaryDetail(recordId);
        PdfUtil.exportSalaryPdf(detail, response);
    }
}

