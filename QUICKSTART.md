# HR 管理系统快速启动指南 🚀

## 前置要求

在开始之前，请确保您的系统已安装以下软件：

- ✅ JDK 17 或更高版本
- ✅ Maven 3.9+
- ✅ MySQL 8.0+
- ✅ Node.js 16+
- ✅ npm 或 yarn

## 快速启动（3 步）

### 步骤 1：初始化数据库

```bash
# 登录 MySQL
mysql -u root -p

# 执行初始化脚本
source backend/src/main/resources/db/init.sql

# 或直接执行
mysql -u root -p < backend/src/main/resources/db/init.sql
```

**验证：** 执行成功后应创建 `hr` 数据库及相关表。

### 步骤 2：启动后端服务

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

**验证：** 
- 控制台输出 "HR Management System 启动成功！"
- 访问 http://localhost:8080/swagger-ui/index.html 能看到 API 文档

### 步骤 3：启动前端服务

```bash
cd frontend
npm install
npm run dev
```

**验证：**
- 浏览器自动打开 http://localhost:8000
- 看到登录页面

## 登录测试

使用以下任意账号登录：

| 角色 | 用户名 | 密码 | 权限 |
|------|--------|------|------|
| 系统管理员 | admin | 123456 | 所有功能 |
| 人事专员 | hr001 | 123456 | 员工管理、工资管理、统计报表 |
| 普通员工 | emp001 | 123456 | 查看个人工资条 |

## 功能测试清单

### ✅ 员工管理测试
1. 使用 `admin` 或 `hr001` 登录
2. 点击左侧菜单"员工管理"
3. 点击"新增员工"按钮，填写表单提交
4. 查看新增的员工出现在列表中
5. 点击"编辑"修改员工信息
6. 点击"离职"变更员工状态
7. 切换状态筛选，查看离职员工

### ✅ 工资管理测试
1. 使用 `hr001` 登录
2. 进入"工资管理" > "工资管理"
3. 点击"录入工资"，选择员工并填写工资信息
4. 退出登录，使用 `emp001` 登录
5. 进入"工资管理" > "我的工资条"
6. 点击"查看明细"查看工资详情
7. 点击"导出PDF"下载工资条

### ✅ 统计报表测试
1. 使用 `admin` 或 `hr001` 登录
2. 进入"统计报表"
3. 查看三个统计图表正常显示
4. 点击"导出 Excel 报表"下载数据

### ✅ 权限控制测试
1. 使用 `emp001` 登录
2. 验证左侧菜单只显示"员工管理"和"我的工资条"
3. 访问员工管理页面，验证没有"新增"、"编辑"、"删除"按钮
4. 尝试直接访问 http://localhost:8000/statistics （应该被拦截）

## 常见问题

### Q1: 后端启动失败，提示数据库连接错误？
**A:** 检查以下几点：
- MySQL 服务是否正在运行
- 数据库 `hr` 是否已创建
- `application.yml` 中的用户名密码是否正确

### Q2: 前端启动后页面空白？
**A:** 
- 检查浏览器控制台是否有错误
- 确认后端服务是否正常运行
- 清除浏览器缓存重试

### Q3: 登录后提示 401 未授权？
**A:**
- 检查用户名密码是否正确
- 确认数据库初始化脚本是否执行成功
- 查看后端日志排查问题

### Q4: Excel 或 PDF 导出失败？
**A:**
- 检查浏览器是否阻止了下载
- 查看后端日志是否有异常
- 确认文件权限设置正确

## 开发模式启动（推荐）

使用提供的启动脚本：

### Linux/macOS:
```bash
chmod +x start-dev.sh
./start-dev.sh
```

### Windows:
手动分别启动前后端，或使用 Git Bash 执行上述脚本。

## 生产环境部署

```bash
# 构建
chmod +x deploy.sh
./deploy.sh

# 启动后端
cd backend
java -jar target/hr-backend-1.0.0.jar

# 部署前端
cd frontend
npm run build
# 将 dist 目录部署到 Nginx 或其他 Web 服务器
```

## 技术支持

- 查看详细文档：[README.md](README.md)
- 查看 API 文档：http://localhost:8080/swagger-ui/index.html
- 后端文档：[backend/README.md](backend/README.md)
- 前端文档：[frontend/README.md](frontend/README.md)

## 下一步

- 🔧 修改配置文件适配您的环境
- 👥 添加更多用户和角色
- 📊 导入真实员工数据
- 🎨 自定义界面主题
- 🔐 配置生产环境安全策略

---

**祝您使用愉快！** 🎉

