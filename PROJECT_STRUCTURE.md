# HR 管理系统项目结构说明

## 完整目录树

```
hr-system/
│
├── README.md                          # 项目主文档
├── QUICKSTART.md                      # 快速启动指南
├── CHANGELOG.md                       # 更新日志
├── LICENSE                            # 开源协议
├── .gitignore                         # Git 忽略配置
├── deploy.sh                          # 部署脚本
├── start-dev.sh                       # 开发启动脚本
│
├── backend/                           # 后端项目（Spring Boot）
│   ├── pom.xml                        # Maven 依赖配置
│   ├── README.md                      # 后端文档
│   │
│   └── src/
│       ├── main/
│       │   ├── java/com/hr/
│       │   │   ├── HrApplication.java           # 主启动类
│       │   │   │
│       │   │   ├── common/                      # 通用类
│       │   │   │   └── R.java                   # 统一响应对象
│       │   │   │
│       │   │   ├── config/                      # 配置类
│       │   │   │   ├── MyBatisConfig.java       # MyBatis 配置
│       │   │   │   ├── SecurityConfig.java      # Spring Security 配置
│       │   │   │   └── WebMvcConfig.java        # Web MVC 配置
│       │   │   │
│       │   │   ├── controller/                  # 控制器层
│       │   │   │   ├── AuthController.java      # 认证接口
│       │   │   │   ├── EmployeeController.java  # 员工管理
│       │   │   │   ├── SalaryController.java    # 工资管理
│       │   │   │   └── StatisticsController.java# 统计报表
│       │   │   │
│       │   │   ├── dto/                         # 数据传输对象
│       │   │   │   ├── EmployeeQuery.java       # 员工查询 DTO
│       │   │   │   ├── EmployeeVO.java          # 员工视图对象
│       │   │   │   ├── LoginRequest.java        # 登录请求
│       │   │   │   ├── LoginResponse.java       # 登录响应
│       │   │   │   ├── SalaryDetailVO.java      # 工资明细 VO
│       │   │   │   ├── SalaryRecordRequest.java # 工资记录请求
│       │   │   │   └── StatisticsVO.java        # 统计数据 VO
│       │   │   │
│       │   │   ├── entity/                      # 实体类
│       │   │   │   ├── Employee.java            # 员工实体
│       │   │   │   ├── Role.java                # 角色实体
│       │   │   │   ├── SalaryDetail.java        # 工资明细实体
│       │   │   │   ├── SalaryItem.java          # 工资项实体
│       │   │   │   ├── SalaryRecord.java        # 工资记录实体
│       │   │   │   └── User.java                # 用户实体
│       │   │   │
│       │   │   ├── exception/                   # 异常处理
│       │   │   │   ├── BusinessException.java   # 业务异常
│       │   │   │   └── GlobalExceptionHandler.java # 全局异常处理器
│       │   │   │
│       │   │   ├── filter/                      # 过滤器
│       │   │   │   └── JwtFilter.java           # JWT 验证过滤器
│       │   │   │
│       │   │   ├── mapper/                      # MyBatis Mapper（全注解）
│       │   │   │   ├── EmployeeMapper.java      # 员工 Mapper
│       │   │   │   ├── RoleMapper.java          # 角色 Mapper
│       │   │   │   ├── SalaryMapper.java        # 工资 Mapper
│       │   │   │   └── UserMapper.java          # 用户 Mapper
│       │   │   │
│       │   │   ├── service/                     # 服务层
│       │   │   │   ├── AuthService.java         # 认证服务
│       │   │   │   ├── EmployeeService.java     # 员工服务
│       │   │   │   ├── SalaryService.java       # 工资服务
│       │   │   │   └── StatisticsService.java   # 统计服务
│       │   │   │
│       │   │   └── util/                        # 工具类
│       │   │       ├── ExcelExportUtil.java     # Excel 导出工具
│       │   │       ├── JwtUtil.java             # JWT 工具
│       │   │       └── PdfUtil.java             # PDF 生成工具
│       │   │
│       │   └── resources/
│       │       ├── application.yml              # 主配置文件
│       │       ├── application-dev.yml          # 开发环境配置
│       │       ├── application-prod.yml         # 生产环境配置
│       │       └── db/
│       │           └── init.sql                 # 数据库初始化脚本
│       │
│       └── test/
│           └── java/com/hr/
│               └── HrApplicationTests.java      # 测试类
│
└── frontend/                          # 前端项目（React + Ant Design Pro）
    ├── package.json                   # 依赖配置
    ├── tsconfig.json                  # TypeScript 配置
    ├── .umirc.ts                      # UmiJS 配置（路由、代理等）
    ├── .eslintrc.js                   # ESLint 配置
    ├── .gitignore                     # Git 忽略配置
    ├── typings.d.ts                   # 类型声明
    ├── README.md                      # 前端文档
    │
    └── src/
        ├── access.ts                  # 权限配置
        ├── app.tsx                    # 全局配置
        │
        ├── pages/                     # 页面组件
        │   ├── Login/                 # 登录页
        │   │   ├── index.tsx
        │   │   └── index.less
        │   │
        │   ├── Employee/              # 员工管理
        │   │   ├── index.tsx          # 员工列表页
        │   │   └── components/
        │   │       └── EmployeeModal.tsx  # 员工编辑弹窗
        │   │
        │   ├── Salary/                # 工资管理
        │   │   ├── MySalary.tsx       # 我的工资条
        │   │   └── Manage.tsx         # 工资管理（HR）
        │   │
        │   └── Statistics/            # 统计报表
        │       └── index.tsx          # 统计图表页
        │
        └── services/                  # API 服务层
            ├── api.ts                 # API 接口定义
            └── typings.d.ts           # 类型定义
```

## 核心文件说明

### 后端核心文件

#### 配置层
- **SecurityConfig.java** - Spring Security 6 无状态配置，JWT 认证
- **JwtFilter.java** - JWT 令牌验证过滤器
- **MyBatisConfig.java** - MyBatis + PageHelper 配置

#### 实体层
- **Employee.java** - 员工实体（包含基本信息、学历、婚姻、岗位、状态）
- **User.java** - 用户实体
- **SalaryRecord.java** - 工资记录实体

#### Mapper 层（全注解）
- 使用 `@Select`、`@Insert`、`@Update`、`@Delete` 注解
- 使用 `@SelectProvider` 实现动态 SQL
- 无 XML 配置文件

#### 服务层
- **AuthService** - 处理登录、刷新令牌
- **EmployeeService** - 员工 CRUD、状态变更、统计
- **SalaryService** - 工资录入、查询、导出

#### 工具类
- **JwtUtil** - JWT 生成、验证、解析
- **ExcelExportUtil** - EasyExcel 流式导出
- **PdfUtil** - Apache PDFBox 生成 PDF

### 前端核心文件

#### 配置文件
- **.umirc.ts** - 路由、代理、插件配置
- **access.ts** - 权限守卫配置
- **app.tsx** - 全局初始化、请求拦截

#### 页面组件
- **Login/index.tsx** - 登录表单，JWT token 存储
- **Employee/index.tsx** - ProTable 表格，CRUD 操作
- **Salary/MySalary.tsx** - 员工查看工资条
- **Statistics/index.tsx** - ECharts 图表展示

#### API 服务
- **api.ts** - 封装所有后端接口调用
- **typings.d.ts** - TypeScript 类型定义

## 数据流向

```
用户操作 → 前端页面组件 → API 服务层 → 后端 Controller
                                            ↓
                                        Service 层
                                            ↓
                                        Mapper 层
                                            ↓
                                        MySQL 数据库
```

## 权限控制流程

```
用户登录 → 后端验证 → 生成 JWT（包含角色信息）
                          ↓
        前端存储 token → 每次请求携带 token
                          ↓
        后端 JwtFilter 验证 → 提取角色信息
                          ↓
        Controller @PreAuthorize 鉴权 → 执行业务逻辑
```

## 技术栈映射

| 功能 | 后端技术 | 前端技术 |
|------|---------|---------|
| 认证授权 | Spring Security + JWT | Axios 拦截器 |
| 数据持久化 | MyBatis（注解） | - |
| 分页查询 | PageHelper | ProTable |
| Excel 导出 | EasyExcel | 浏览器下载 |
| PDF 生成 | Apache PDFBox | 浏览器下载 |
| 图表展示 | - | ECharts |
| API 文档 | SpringDoc (Swagger) | - |
| 权限控制 | @PreAuthorize | access.ts |

## 扩展建议

### 功能扩展
- 📧 邮件通知（工资条发送）
- 📱 移动端适配
- 📅 考勤管理模块
- 🎓 培训管理模块
- 📝 绩效考核模块

### 技术优化
- 🔄 Redis 缓存
- 📊 分布式跟踪（Sleuth）
- 🔐 更细粒度的权限控制（按钮级）
- 📦 Docker 容器化部署
- ☁️ 云平台部署（阿里云/AWS）

---

**维护建议：** 保持前后端代码风格一致，定期更新依赖版本，及时处理安全漏洞。

