package com.hr.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.hr.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 导出工具类
 */
public class ExcelExportUtil {
    
    /**
     * 导出员工 Excel
     */
    public static void exportEmployees(List<Employee> employees, HttpServletResponse response) throws IOException {
        // 转换为 DTO
        List<EmployeeExportDTO> data = new ArrayList<>();
        for (Employee emp : employees) {
            EmployeeExportDTO dto = new EmployeeExportDTO();
            dto.setEmployeeNo(emp.getEmployeeNo());
            dto.setName(emp.getName());
            dto.setGender(emp.getGender());
            dto.setBirthDate(emp.getBirthDate() != null ? emp.getBirthDate().toString() : "");
            dto.setPhone(emp.getPhone());
            dto.setEmail(emp.getEmail());
            dto.setEducation(emp.getEducation());
            dto.setMaritalStatus(emp.getMaritalStatus());
            dto.setDepartment(emp.getDepartment());
            dto.setPosition(emp.getPosition());
            dto.setJobTitle(emp.getJobTitle());
            dto.setEntryDate(emp.getEntryDate() != null ? emp.getEntryDate().toString() : "");
            dto.setStatus(emp.getStatus());
            data.add(dto);
        }
        
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("员工信息表", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        
        // 导出
        EasyExcel.write(response.getOutputStream(), EmployeeExportDTO.class)
                .sheet("员工信息")
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(data);
    }
    
    /**
     * 员工导出 DTO
     */
    @Data
    public static class EmployeeExportDTO {
        @com.alibaba.excel.annotation.ExcelProperty("工号")
        private String employeeNo;
        
        @com.alibaba.excel.annotation.ExcelProperty("姓名")
        private String name;
        
        @com.alibaba.excel.annotation.ExcelProperty("性别")
        private String gender;
        
        @com.alibaba.excel.annotation.ExcelProperty("出生日期")
        private String birthDate;
        
        @com.alibaba.excel.annotation.ExcelProperty("联系电话")
        private String phone;
        
        @com.alibaba.excel.annotation.ExcelProperty("邮箱")
        private String email;
        
        @com.alibaba.excel.annotation.ExcelProperty("学历")
        private String education;
        
        @com.alibaba.excel.annotation.ExcelProperty("婚姻状况")
        private String maritalStatus;
        
        @com.alibaba.excel.annotation.ExcelProperty("部门")
        private String department;
        
        @com.alibaba.excel.annotation.ExcelProperty("岗位")
        private String position;
        
        @com.alibaba.excel.annotation.ExcelProperty("职称")
        private String jobTitle;
        
        @com.alibaba.excel.annotation.ExcelProperty("入职日期")
        private String entryDate;
        
        @com.alibaba.excel.annotation.ExcelProperty("状态")
        private String status;
    }
}

