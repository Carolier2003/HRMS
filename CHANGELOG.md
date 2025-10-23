# 更新日志

## [1.0.0] - 2025-10-22

### 新增功能

#### 员工管理
- ✅ 员工信息的增删改查
- ✅ 支持记录学历、婚姻状况、职称等信息
- ✅ 员工状态管理（在职/离职/退休/辞退）
- ✅ 多条件组合查询和分页
- ✅ Excel 批量导出

#### 工资管理
- ✅ 自定义工资项（底薪、奖金、扣款）
- ✅ HR 批量录入工资
- ✅ 员工查看个人工资条
- ✅ 工资条 PDF 导出
- ✅ 工资明细查看

#### 统计报表
- ✅ 学历分布统计（饼图）
- ✅ 婚姻状况统计（柱状图）
- ✅ 部门人数统计（柱状图）
- ✅ 数据导出（Excel）

#### 权限控制
- ✅ RBAC 角色权限管理
- ✅ 三种角色：EMPLOYEE / HR / ADMIN
- ✅ 后端方法级权限控制
- ✅ 前端页面和按钮级权限

#### 认证安全
- ✅ JWT 无状态认证
- ✅ AccessToken + RefreshToken 双令牌机制
- ✅ 前端自动刷新令牌
- ✅ 密码 BCrypt 加密
- ✅ CORS 跨域配置

### 技术特性

#### 后端
- Spring Boot 3.2.5
- Spring Security 6
- MyBatis 3.0.3（全注解）
- PageHelper 分页
- SpringDoc OpenAPI 3.0
- EasyExcel 导出
- Apache PDFBox PDF 生成

#### 前端
- React 18
- TypeScript 5
- Ant Design Pro 6
- UmiJS 4
- Axios 请求封装
- ECharts 图表

### 初始数据

- 3 个角色：EMPLOYEE、HR、ADMIN
- 3 个测试账号：admin、hr001、emp001
- 5 条示例员工数据
- 7 个预设工资项目
- 若干示例工资记录

---

**维护者**：开发团队  
**发布日期**：2025-10-22

