package com.hr;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HR 管理系统主启动类
 */
@SpringBootApplication
@MapperScan("com.hr.mapper")
@OpenAPIDefinition(
    info = @Info(
        title = "HR Management System API",
        version = "1.0",
        description = "人事管理系统 API 文档"
    )
)
public class HrApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("HR Management System 启动成功！");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui/index.html");
        System.out.println("========================================\n");
    }
}

