# HR 管理系统（HRMS）

一个基于 Spring Boot 3.2.5 + MyBatis + React 18 + Ant Design Pro 6 的前后端分离人事管理系统。

## 📋 项目简介

本系统是一个功能完整的人事管理系统，包含员工管理、工资管理、统计报表等核心功能，支持 RBAC 权限控制，采用 JWT 无状态认证。

## 🛠️ 技术栈

### 后端
- **Spring Boot 3.2.5** - 基础框架
- **Spring Security 6** - 安全框架
- **MyBatis 3.0.3** - ORM 框架（全注解）
- **PageHelper 2.1.0** - 分页插件
- **MySQL 8.0** - 数据库
- **JWT 0.12.3** - 令牌认证
- **SpringDoc 2.3.0** - API 文档
- **EasyExcel 3.3.4** - Excel 导出
- **Apache PDFBox 2.0.30** - PDF 生成

### 前端
- **React 18** - UI 框架
- **TypeScript 5** - 类型支持
- **Ant Design Pro 6** - 企业级 UI 框架
- **UmiJS 4** - 应用框架
- **Axios 1.6** - HTTP 客户端
- **ECharts 5.4** - 图表库

## 📁 项目结构

```
hr-system/
├── backend/                    # 后端项目
│   ├── src/main/java/com/hr/
│   │   ├── HrApplication.java # 主启动类
│   │   ├── config/            # 配置类
│   │   ├── controller/        # 控制器
│   │   ├── service/           # 服务层
│   │   ├── mapper/            # MyBatis Mapper
│   │   ├── entity/            # 实体类
│   │   ├── dto/               # 数据传输对象
│   │   ├── util/              # 工具类
│   │   ├── common/            # 通用类
│   │   ├── filter/            # 过滤器
│   │   └── exception/         # 异常处理
│   ├── src/main/resources/
│   │   ├── application.yml    # 配置文件
│   │   └── db/init.sql        # 数据库初始化脚本
│   └── pom.xml                # Maven 依赖
│
└── frontend/                   # 前端项目
    ├── src/
    │   ├── pages/             # 页面组件
    │   │   ├── Login/         # 登录页
    │   │   ├── Employee/      # 员工管理
    │   │   ├── Salary/        # 工资管理
    │   │   └── Statistics/    # 统计报表
    │   ├── services/          # API 服务
    │   ├── access.ts          # 权限配置
    │   └── app.tsx            # 全局配置
    ├── .umirc.ts              # UmiJS 配置
    └── package.json           # 依赖配置
```

## ✨ 核心功能

### 1. 员工管理
- ✅ 员工信息 CRUD（增删改查）
- ✅ 包含基本信息、学历、婚姻状况、岗位等
- ✅ 员工状态管理（在职/离职/退休/辞退）
- ✅ 默认只显示在职员工，支持按状态筛选
- ✅ 多条件组合查询和分页
- ✅ Excel 批量导出
- ✅ 权限控制：HR/ADMIN 可编辑，EMPLOYEE 只读

### 2. 工资管理
- ✅ **工资项目管理**：自定义工资项（底薪、奖金、扣款）
- ✅ **工资记录管理**：HR 批量录入每月工资，支持删除记录
- ✅ **我的工资条**：员工查看个人工资条和明细
- ✅ **PDF 导出**：专业格式的工资条 PDF 导出
- ✅ **工资明细**：详细的工资项目构成
- ✅ **默认金额**：合理的工资项目默认金额设置

### 3. 统计报表
- ✅ 学历分布饼图
- ✅ 婚姻状况柱状图
- ✅ 部门人数统计图
- ✅ 岗位分布统计
- ✅ Excel 报表导出
- ✅ 数据概览卡片

### 4. 系统管理
- ✅ **用户管理**：用户账号的增删改查
- ✅ **权限管理**：角色和权限的配置
- ✅ **系统设置**：系统参数配置（已隐藏）

### 5. 权限控制
- ✅ RBAC 角色权限管理
- ✅ 三种角色：EMPLOYEE / HR / ADMIN
- ✅ 方法级权限控制（@PreAuthorize）
- ✅ 前端页面和按钮级权限
- ✅ 动态权限验证

### 6. 认证安全
- ✅ JWT 无状态认证
- ✅ AccessToken + RefreshToken 机制
- ✅ 自动刷新令牌（2小时有效期）
- ✅ 密码 BCrypt 加密
- ✅ CORS 跨域配置
- ✅ 用户状态管理

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.9+
- MySQL 8.0+
- Node.js 16+
- npm 或 yarn

### 1. 数据库初始化

```bash
# 登录 MySQL
mysql -u root -p

# 执行初始化脚本
source backend/src/main/resources/db/init.sql
```

### 2. 启动后端

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

Swagger 文档：http://localhost:8080/swagger-ui/index.html

### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端服务将在 http://localhost:8000 启动

## 👥 默认账号

系统内置三个测试账号（密码均为：`123456`）：

| 用户名 | 角色 | 说明 |
|--------|------|------|
| admin | ADMIN | 系统管理员，拥有所有权限 |
| hr001 | HR | 人事专员，可管理员工和工资 |
| emp001 | EMPLOYEE | 普通员工，只能查看个人信息 |

## 🧪 功能验证

### 员工管理验证
1. 使用 admin 或 hr001 登录
2. 进入"员工管理"页面
3. 点击"新增员工"，填写信息并提交
4. 修改员工状态为"离职"，列表自动隐藏该员工
5. 在筛选条件中选择"离职"状态，可查看离职员工
6. 验证权限：emp001 用户看不到编辑按钮

### 工资管理验证
1. 使用 hr001 登录
2. 进入"工资管理" > "工资项目管理"，查看合理的默认金额
3. 进入"工资管理" > "工资管理"，点击"录入工资"
4. 选择员工并填写工资信息，支持删除工资记录
5. 退出登录，使用 emp001 登录
6. 进入"我的工资条"，查看工资记录和明细
7. 点击"导出PDF"，下载专业格式的工资条 PDF 文件

### 统计报表验证
1. 使用 admin 或 hr001 登录
2. 进入"统计报表"页面
3. 查看数据概览卡片、学历分布饼图、婚姻状况柱状图、部门人数统计图
4. 点击"导出 Excel 报表"，下载统计数据

### 系统管理验证
1. 使用 admin 登录
2. 进入"系统管理" > "用户管理"
3. 查看用户列表，验证角色显示正确
4. 进入"系统管理" > "权限管理"
5. 查看角色列表和权限配置

## 🔒 安全说明

- 所有密码使用 BCrypt 加密存储
- JWT Token 有效期：AccessToken 2小时，RefreshToken 7天
- 自动令牌刷新机制，提升用户体验
- 跨域请求已配置白名单
- SQL 注入防护：使用 MyBatis 参数绑定
- XSS 防护：前端输入验证 + 后端过滤
- 用户状态管理：支持账号启用/禁用

## 📝 API 文档

后端启动后访问 Swagger UI：
```
http://localhost:8080/swagger-ui/index.html
```

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

MIT License

## 📧 联系方式

如有问题，请通过 Issue 联系我们。

---

## 🎯 系统特色

### 1. 完整的 HR 业务流程
- 员工全生命周期管理
- 灵活的工资体系配置
- 多维度数据统计分析

### 2. 专业的 PDF 工资条
- 标准企业工资条格式
- 支持中英文显示
- 完整的工资明细信息

### 3. 细粒度权限控制
- 基于角色的访问控制（RBAC）
- 页面级和按钮级权限
- 动态权限验证

### 4. 现代化技术栈
- Spring Boot 3.x + React 18
- 前后端分离架构
- RESTful API 设计

## 📊 系统截图

### 主要功能页面
- **员工管理**：支持增删改查、状态管理、多条件查询
- **工资管理**：工资项目管理、工资录入、工资条查看
- **统计报表**：多维度数据可视化分析
- **系统管理**：用户管理、权限配置

## 🔧 技术亮点

1. **PDF 生成优化**：解决中文字符编码问题，支持专业工资条格式
2. **权限系统**：完整的 RBAC 实现，支持动态权限验证
3. **数据一致性**：用户表和员工表关联，确保数据完整性
4. **用户体验**：自动令牌刷新、响应式设计、友好的错误提示

**注意事项：**
1. 首次运行请务必先执行数据库初始化脚本
2. 确保 MySQL 服务已启动且端口为 3306
3. 如需修改数据库配置，请编辑 `backend/src/main/resources/application.yml`
4. 如需修改前端代理配置，请编辑 `frontend/.umirc.ts`
5. PDF 导出功能已优化，支持中英文混合显示

