package com.hr.controller;

import com.github.pagehelper.PageInfo;
import com.hr.common.R;
import com.hr.dto.EmployeeQuery;
import com.hr.entity.Employee;
import com.hr.service.EmployeeService;
import com.hr.util.ExcelExportUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 员工控制器
 */
@RestController
@RequestMapping("/api/employees")
@Tag(name = "员工管理", description = "员工的增删改查、状态变更、统计导出等接口")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HR', 'ADMIN')")
    @Operation(summary = "分页查询员工列表")
    public R<PageInfo<Employee>> list(EmployeeQuery query) {
        PageInfo<Employee> pageInfo = employeeService.queryEmployees(query);
        return R.ok(pageInfo);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询员工详情")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return R.ok(employee);
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "添加员工")
    public R<Employee> add(@RequestBody Employee employee) {
        Employee result = employeeService.addEmployee(employee);
        return R.ok(result);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "更新员工信息")
    public R<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        Employee result = employeeService.updateEmployee(employee);
        return R.ok(result);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除员工")
    public R<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return R.ok();
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "变更员工状态")
    public R<Void> changeStatus(@PathVariable Long id,
                                  @RequestParam String status,
                                  @RequestParam(required = false) String reason) {
        employeeService.changeEmployeeStatus(id, status, reason);
        return R.ok();
    }
    
    @GetMapping("/export")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @Operation(summary = "导出员工Excel")
    public void export(EmployeeQuery query, HttpServletResponse response) throws IOException {
        List<Employee> employees = employeeService.exportEmployees(query);
        ExcelExportUtil.exportEmployees(employees, response);
    }
}

