package com.hr.mapper;

import com.hr.entity.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 员工 Mapper
 */
@Mapper
public interface EmployeeMapper {
    
    @Select("SELECT id, employee_no AS employeeNo, name, gender, birth_date AS birthDate, " +
            "id_card AS idCard, phone, email, address, education, major, graduate_school AS graduateSchool, " +
            "graduate_date AS graduateDate, marital_status AS maritalStatus, department, position, " +
            "job_title AS jobTitle, entry_date AS entryDate, work_years AS workYears, status, " +
            "leave_date AS leaveDate, leave_reason AS leaveReason, created_at AS createdAt, updated_at AS updatedAt " +
            "FROM employees WHERE id = #{id}")
    Employee findById(Long id);
    
    @Select("SELECT id, employee_no AS employeeNo, name, gender, birth_date AS birthDate, " +
            "id_card AS idCard, phone, email, address, education, major, graduate_school AS graduateSchool, " +
            "graduate_date AS graduateDate, marital_status AS maritalStatus, department, position, " +
            "job_title AS jobTitle, entry_date AS entryDate, work_years AS workYears, status, " +
            "leave_date AS leaveDate, leave_reason AS leaveReason, created_at AS createdAt, updated_at AS updatedAt " +
            "FROM employees WHERE employee_no = #{employeeNo}")
    Employee findByEmployeeNo(String employeeNo);
    
    @SelectProvider(type = EmployeeSqlProvider.class, method = "selectByCondition")
    List<Employee> findByCondition(@Param("name") String name,
                                    @Param("department") String department,
                                    @Param("education") String education,
                                    @Param("maritalStatus") String maritalStatus,
                                    @Param("position") String position,
                                    @Param("status") String status);
    
    @Insert("INSERT INTO employees (employee_no, name, gender, birth_date, id_card, phone, email, address, " +
            "education, major, graduate_school, graduate_date, marital_status, department, position, job_title, " +
            "entry_date, work_years, status) VALUES (#{employeeNo}, #{name}, #{gender}, #{birthDate}, #{idCard}, " +
            "#{phone}, #{email}, #{address}, #{education}, #{major}, #{graduateSchool}, #{graduateDate}, " +
            "#{maritalStatus}, #{department}, #{position}, #{jobTitle}, #{entryDate}, #{workYears}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Employee employee);
    
    @Update("UPDATE employees SET name = #{name}, gender = #{gender}, birth_date = #{birthDate}, " +
            "id_card = #{idCard}, phone = #{phone}, email = #{email}, address = #{address}, " +
            "education = #{education}, major = #{major}, graduate_school = #{graduateSchool}, " +
            "graduate_date = #{graduateDate}, marital_status = #{maritalStatus}, department = #{department}, " +
            "position = #{position}, job_title = #{jobTitle}, entry_date = #{entryDate}, work_years = #{workYears}, " +
            "status = #{status}, leave_date = #{leaveDate}, leave_reason = #{leaveReason} WHERE id = #{id}")
    int update(Employee employee);
    
    @Delete("DELETE FROM employees WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT COUNT(*) FROM employees GROUP BY education")
    List<String> countByEducation();
    
    @Select("SELECT education, COUNT(*) as count FROM employees WHERE status = '在职' GROUP BY education")
    @MapKey("education")
    List<java.util.Map<String, Object>> statisticsByEducation();
    
    @Select("SELECT marital_status, COUNT(*) as count FROM employees WHERE status = '在职' GROUP BY marital_status")
    @MapKey("marital_status")
    List<java.util.Map<String, Object>> statisticsByMaritalStatus();
    
    @Select("SELECT department, COUNT(*) as count FROM employees WHERE status = '在职' GROUP BY department")
    @MapKey("department")
    List<java.util.Map<String, Object>> statisticsByDepartment();
    
    @Select("SELECT position, COUNT(*) as count FROM employees WHERE status = '在职' GROUP BY position")
    @MapKey("position")
    List<java.util.Map<String, Object>> statisticsByPosition();
    
    @Select("SELECT COUNT(*) FROM employees")
    Long countTotal();
    
    @Select("SELECT COUNT(*) FROM employees WHERE status = '在职'")
    Long countOnJob();
    
    @Select("SELECT COUNT(*) FROM employees WHERE status != '在职'")
    Long countLeave();
    
    /**
     * SQL 提供者类
     */
    class EmployeeSqlProvider {
        public String selectByCondition(@Param("name") String name,
                                         @Param("department") String department,
                                         @Param("education") String education,
                                         @Param("maritalStatus") String maritalStatus,
                                         @Param("position") String position,
                                         @Param("status") String status) {
            return new SQL() {{
                SELECT("id, employee_no AS employeeNo, name, gender, birth_date AS birthDate, " +
                       "id_card AS idCard, phone, email, address, education, major, graduate_school AS graduateSchool, " +
                       "graduate_date AS graduateDate, marital_status AS maritalStatus, department, position, " +
                       "job_title AS jobTitle, entry_date AS entryDate, work_years AS workYears, status, " +
                       "leave_date AS leaveDate, leave_reason AS leaveReason, created_at AS createdAt, updated_at AS updatedAt");
                FROM("employees");
                if (name != null && !name.isEmpty()) {
                    WHERE("name LIKE CONCAT('%', #{name}, '%')");
                }
                if (department != null && !department.isEmpty()) {
                    WHERE("department = #{department}");
                }
                if (education != null && !education.isEmpty()) {
                    WHERE("education = #{education}");
                }
                if (maritalStatus != null && !maritalStatus.isEmpty()) {
                    WHERE("marital_status = #{maritalStatus}");
                }
                if (position != null && !position.isEmpty()) {
                    WHERE("position = #{position}");
                }
                if (status != null && !status.isEmpty()) {
                    WHERE("status = #{status}");
                }
                ORDER_BY("created_at DESC");
            }}.toString();
        }
    }
}

