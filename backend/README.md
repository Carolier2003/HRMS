# HR 管理系统后端

## 技术栈

- Spring Boot 3.2.5
- Spring Security 6
- MyBatis 3.0.3 (全注解)
- MySQL 8.0
- JWT 认证
- Swagger/OpenAPI 3.0

## 快速启动

### 1. 配置数据库

确保 MySQL 8.0 已安装并运行，然后执行初始化脚本：

```bash
mysql -u root -p < src/main/resources/db/init.sql
```

### 2. 修改配置（可选）

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hr
    username: root
    password: your_password
```

### 3. 启动应用

```bash
# 使用 Maven
mvn clean install
mvn spring-boot:run

# 或使用 Maven Wrapper
./mvnw spring-boot:run
```

应用将在 http://localhost:8080 启动

## API 文档

Swagger UI: http://localhost:8080/swagger-ui/index.html

## 项目结构

```
src/main/java/com/hr/
├── HrApplication.java          # 主启动类
├── config/                     # 配置类
│   ├── SecurityConfig.java     # Spring Security 配置
│   ├── JwtFilter.java          # JWT 过滤器
│   ├── WebMvcConfig.java       # Web MVC 配置
│   └── MyBatisConfig.java      # MyBatis 配置
├── controller/                 # 控制器
│   ├── AuthController.java     # 认证接口
│   ├── EmployeeController.java # 员工管理
│   ├── SalaryController.java   # 工资管理
│   └── StatisticsController.java # 统计报表
├── service/                    # 服务层
├── mapper/                     # MyBatis Mapper
├── entity/                     # 实体类
├── dto/                        # 数据传输对象
├── util/                       # 工具类
├── common/                     # 通用类
├── filter/                     # 过滤器
└── exception/                  # 异常处理
```

## 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | ADMIN |
| hr001 | 123456 | HR |
| emp001 | 123456 | EMPLOYEE |

## 核心功能

- 用户认证（JWT）
- 员工管理 CRUD
- 工资管理
- 统计报表
- Excel 导出
- PDF 生成
- 权限控制

