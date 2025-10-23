package com.hr.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hr.dto.EmployeeQuery;
import com.hr.entity.Employee;
import com.hr.exception.BusinessException;
import com.hr.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 员工服务
 */
@Service
public class EmployeeService {
    
    private final EmployeeMapper employeeMapper;
    
    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }
    
    /**
     * 分页查询员工
     */
    public PageInfo<Employee> queryEmployees(EmployeeQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<Employee> list = employeeMapper.findByCondition(
                query.getName(),
                query.getDepartment(),
                query.getEducation(),
                query.getMaritalStatus(),
                query.getPosition(),
                query.getStatus()
        );
        return new PageInfo<>(list);
    }
    
    /**
     * 根据ID查询员工
     */
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeMapper.findById(id);
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }
        return employee;
    }
    
    /**
     * 添加员工
     */
    @Transactional
    public Employee addEmployee(Employee employee) {
        // 检查工号是否重复
        Employee existing = employeeMapper.findByEmployeeNo(employee.getEmployeeNo());
        if (existing != null) {
            throw new BusinessException("工号已存在");
        }
        
        if (employee.getStatus() == null || employee.getStatus().isEmpty()) {
            employee.setStatus("在职");
        }
        
        employeeMapper.insert(employee);
        return employee;
    }
    
    /**
     * 更新员工
     */
    @Transactional
    public Employee updateEmployee(Employee employee) {
        Employee existing = employeeMapper.findById(employee.getId());
        if (existing == null) {
            throw new BusinessException("员工不存在");
        }
        
        // 如果工号改变，检查新工号是否重复
        if (!existing.getEmployeeNo().equals(employee.getEmployeeNo())) {
            Employee duplicate = employeeMapper.findByEmployeeNo(employee.getEmployeeNo());
            if (duplicate != null) {
                throw new BusinessException("工号已存在");
            }
        }
        
        employeeMapper.update(employee);
        return employee;
    }
    
    /**
     * 删除员工
     */
    @Transactional
    public void deleteEmployee(Long id) {
        Employee existing = employeeMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("员工不存在");
        }
        employeeMapper.deleteById(id);
    }
    
    /**
     * 变更员工状态
     */
    @Transactional
    public void changeEmployeeStatus(Long id, String status, String reason) {
        Employee employee = employeeMapper.findById(id);
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }
        
        employee.setStatus(status);
        if (!"在职".equals(status)) {
            employee.setLeaveDate(java.time.LocalDate.now());
            employee.setLeaveReason(reason);
        }
        
        employeeMapper.update(employee);
    }
    
    /**
     * 学历统计
     */
    public List<Map<String, Object>> statisticsByEducation() {
        return employeeMapper.statisticsByEducation();
    }
    
    /**
     * 婚姻状况统计
     */
    public List<Map<String, Object>> statisticsByMaritalStatus() {
        return employeeMapper.statisticsByMaritalStatus();
    }
    
    /**
     * 部门统计
     */
    public List<Map<String, Object>> statisticsByDepartment() {
        return employeeMapper.statisticsByDepartment();
    }
    
    /**
     * 导出所有员工（用于 Excel）
     */
    public List<Employee> exportEmployees(EmployeeQuery query) {
        return employeeMapper.findByCondition(
                query.getName(),
                query.getDepartment(),
                query.getEducation(),
                query.getMaritalStatus(),
                query.getPosition(),
                query.getStatus()
        );
    }
}

