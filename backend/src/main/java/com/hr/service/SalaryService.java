package com.hr.service;

import com.hr.dto.SalaryDetailVO;
import com.hr.dto.SalaryRecordRequest;
import com.hr.entity.Employee;
import com.hr.entity.SalaryDetail;
import com.hr.entity.SalaryItem;
import com.hr.entity.SalaryRecord;
import com.hr.exception.BusinessException;
import com.hr.mapper.EmployeeMapper;
import com.hr.mapper.SalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资服务
 */
@Service
public class SalaryService {
    
    private final SalaryMapper salaryMapper;
    private final EmployeeMapper employeeMapper;
    
    public SalaryService(SalaryMapper salaryMapper, EmployeeMapper employeeMapper) {
        this.salaryMapper = salaryMapper;
        this.employeeMapper = employeeMapper;
    }
    
    /**
     * 获取所有工资项
     */
    public List<SalaryItem> getAllItems() {
        return salaryMapper.findAllActiveItems();
    }
    
    /**
     * 添加工资项
     */
    @Transactional
    public SalaryItem addItem(SalaryItem item) {
        salaryMapper.insertItem(item);
        return item;
    }
    
    /**
     * 更新工资项
     */
    @Transactional
    public SalaryItem updateItem(SalaryItem item) {
        salaryMapper.updateItem(item);
        return item;
    }
    
    /**
     * 删除工资项
     */
    @Transactional
    public void deleteItem(Long id) {
        salaryMapper.deleteItem(id);
    }
    
    /**
     * 创建工资记录
     */
    @Transactional
    public SalaryRecord createSalaryRecord(SalaryRecordRequest request, Long createdBy) {
        // 检查员工是否存在
        Employee employee = employeeMapper.findById(request.getEmployeeId());
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }
        
        SalaryRecord record = new SalaryRecord();
        record.setEmployeeId(request.getEmployeeId());
        record.setYearMonth(request.getYearMonth());
        record.setBaseSalary(request.getBaseSalary());
        record.setBonus(request.getBonus());
        record.setDeduction(request.getDeduction());
        record.setActualSalary(request.getActualSalary());
        record.setRemark(request.getRemark());
        record.setCreatedBy(createdBy);
        
        salaryMapper.insertRecord(record);
        
        // 保存工资明细
        if (request.getDetails() != null && !request.getDetails().isEmpty()) {
            for (SalaryRecordRequest.SalaryItemDetail detail : request.getDetails()) {
                SalaryDetail salaryDetail = new SalaryDetail();
                salaryDetail.setSalaryRecordId(record.getId());
                salaryDetail.setItemId(detail.getItemId());
                salaryDetail.setAmount(detail.getAmount());
                salaryMapper.insertDetail(salaryDetail);
            }
        }
        
        return record;
    }
    
    /**
     * 查询员工工资记录
     */
    public List<SalaryRecord> getEmployeeSalaryRecords(Long employeeId) {
        return salaryMapper.findRecordsByEmployeeId(employeeId);
    }
    
    /**
     * 查询工资记录明细
     */
    public SalaryDetailVO getSalaryDetail(Long recordId) {
        SalaryRecord record = salaryMapper.findRecordById(recordId);
        if (record == null) {
            throw new BusinessException("工资记录不存在");
        }
        
        // 获取员工信息
        Employee employee = employeeMapper.findById(record.getEmployeeId());
        if (employee == null) {
            throw new BusinessException("员工信息不存在");
        }
        
        List<SalaryDetail> details = salaryMapper.findDetailsByRecordId(recordId);
        
        SalaryDetailVO vo = new SalaryDetailVO();
        vo.setId(record.getId());
        vo.setEmployeeId(record.getEmployeeId());
        vo.setEmployeeName(employee.getName());
        vo.setEmployeeNo(employee.getEmployeeNo());
        vo.setYearMonth(record.getYearMonth());
        vo.setBaseSalary(record.getBaseSalary());
        vo.setBonus(record.getBonus());
        vo.setDeduction(record.getDeduction());
        vo.setActualSalary(record.getActualSalary());
        vo.setRemark(record.getRemark());
        
        // 转换明细
        List<SalaryDetailVO.ItemDetail> itemDetails = new ArrayList<>();
        for (SalaryDetail detail : details) {
            SalaryDetailVO.ItemDetail itemDetail = new SalaryDetailVO.ItemDetail();
            itemDetail.setItemName(detail.getItemName());
            itemDetail.setItemType(detail.getItemType());
            itemDetail.setAmount(detail.getAmount());
            itemDetails.add(itemDetail);
        }
        vo.setItems(itemDetails);
        
        return vo;
    }
    
    /**
     * 按月查询所有工资记录
     */
    public List<SalaryRecord> getRecordsByMonth(String yearMonth) {
        return salaryMapper.findRecordsByYearMonth(yearMonth);
    }
    
    /**
     * 删除工资记录
     */
    @Transactional
    public void deleteSalaryRecord(Long recordId) {
        SalaryRecord record = salaryMapper.findRecordById(recordId);
        if (record == null) {
            throw new BusinessException("工资记录不存在");
        }
        
        salaryMapper.deleteDetailsByRecordId(recordId);
        salaryMapper.deleteRecord(recordId);
    }
}

