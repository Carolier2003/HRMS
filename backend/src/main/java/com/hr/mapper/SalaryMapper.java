package com.hr.mapper;

import com.hr.entity.SalaryDetail;
import com.hr.entity.SalaryItem;
import com.hr.entity.SalaryRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 工资 Mapper
 */
@Mapper
public interface SalaryMapper {
    
    // 工资项目
    @Select("SELECT id, name, type, default_amount AS defaultAmount, enabled, description, " +
            "created_at AS createdAt FROM salary_items WHERE enabled = true")
    List<SalaryItem> findAllActiveItems();
    
    @Select("SELECT id, name, type, default_amount AS defaultAmount, enabled, description, " +
            "created_at AS createdAt FROM salary_items WHERE id = #{id}")
    SalaryItem findItemById(Long id);
    
    @Insert("INSERT INTO salary_items (name, type, default_amount, enabled, description) " +
            "VALUES (#{name}, #{type}, #{defaultAmount}, #{enabled}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertItem(SalaryItem item);
    
    @Update("UPDATE salary_items SET name = #{name}, type = #{type}, default_amount = #{defaultAmount}, " +
            "enabled = #{enabled}, description = #{description} WHERE id = #{id}")
    int updateItem(SalaryItem item);
    
    @Delete("DELETE FROM salary_items WHERE id = #{id}")
    int deleteItem(Long id);
    
    // 工资记录
    @Select("SELECT sr.id, sr.employee_id AS employeeId, sr.year_month AS yearMonth, " +
            "sr.base_salary AS baseSalary, sr.bonus, sr.deduction, sr.actual_salary AS actualSalary, " +
            "sr.remark, sr.created_by AS createdBy, sr.created_at AS createdAt, " +
            "e.name as employeeName, e.employee_no AS employeeNo " +
            "FROM salary_records sr LEFT JOIN employees e ON sr.employee_id = e.id WHERE sr.id = #{id}")
    SalaryRecord findRecordById(Long id);
    
    @Select("SELECT sr.id, sr.employee_id AS employeeId, sr.year_month AS yearMonth, " +
            "sr.base_salary AS baseSalary, sr.bonus, sr.deduction, sr.actual_salary AS actualSalary, " +
            "sr.remark, sr.created_by AS createdBy, sr.created_at AS createdAt, " +
            "e.name as employeeName, e.employee_no AS employeeNo " +
            "FROM salary_records sr LEFT JOIN employees e ON sr.employee_id = e.id " +
            "WHERE sr.employee_id = #{employeeId} ORDER BY sr.year_month DESC")
    List<SalaryRecord> findRecordsByEmployeeId(Long employeeId);
    
    @Select("SELECT sr.id, sr.employee_id AS employeeId, sr.year_month AS yearMonth, " +
            "sr.base_salary AS baseSalary, sr.bonus, sr.deduction, sr.actual_salary AS actualSalary, " +
            "sr.remark, sr.created_by AS createdBy, sr.created_at AS createdAt, " +
            "e.name as employeeName, e.employee_no AS employeeNo " +
            "FROM salary_records sr LEFT JOIN employees e ON sr.employee_id = e.id " +
            "WHERE sr.year_month = #{yearMonth} ORDER BY sr.employee_id")
    List<SalaryRecord> findRecordsByYearMonth(String yearMonth);
    
    @Insert("INSERT INTO salary_records (employee_id, year_month, base_salary, bonus, deduction, " +
            "actual_salary, remark, created_by) VALUES (#{employeeId}, #{yearMonth}, #{baseSalary}, " +
            "#{bonus}, #{deduction}, #{actualSalary}, #{remark}, #{createdBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRecord(SalaryRecord record);
    
    @Update("UPDATE salary_records SET base_salary = #{baseSalary}, bonus = #{bonus}, " +
            "deduction = #{deduction}, actual_salary = #{actualSalary}, remark = #{remark} WHERE id = #{id}")
    int updateRecord(SalaryRecord record);
    
    @Delete("DELETE FROM salary_records WHERE id = #{id}")
    int deleteRecord(Long id);
    
    // 工资明细
    @Select("SELECT sd.id, sd.salary_record_id AS salaryRecordId, sd.item_id AS itemId, sd.amount, " +
            "si.name AS itemName, si.type AS itemType FROM salary_details sd " +
            "LEFT JOIN salary_items si ON sd.item_id = si.id " +
            "WHERE sd.salary_record_id = #{salaryRecordId}")
    List<SalaryDetail> findDetailsByRecordId(Long salaryRecordId);
    
    @Insert("INSERT INTO salary_details (salary_record_id, item_id, amount) " +
            "VALUES (#{salaryRecordId}, #{itemId}, #{amount})")
    int insertDetail(SalaryDetail detail);
    
    @Delete("DELETE FROM salary_details WHERE salary_record_id = #{salaryRecordId}")
    int deleteDetailsByRecordId(Long salaryRecordId);
}

