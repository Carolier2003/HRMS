# HR 管理系统前端

## 技术栈

- React 18
- TypeScript 5
- Ant Design Pro 6
- UmiJS 4
- ECharts 5

## 快速启动

### 1. 安装依赖

```bash
npm install
# 或
yarn install
```

### 2. 启动开发服务器

```bash
npm run dev
# 或
yarn dev
```

应用将在 http://localhost:8000 启动

### 3. 构建生产版本

```bash
npm run build
# 或
yarn build
```

## 项目结构

```
src/
├── pages/                      # 页面组件
│   ├── Login/                  # 登录页
│   ├── Employee/               # 员工管理
│   ├── Salary/                 # 工资管理
│   └── Statistics/             # 统计报表
├── services/                   # API 服务
│   ├── api.ts                  # API 接口定义
│   └── typings.d.ts            # 类型定义
├── access.ts                   # 权限配置
└── app.tsx                     # 全局配置
```

## 配置说明

### 代理配置

编辑 `.umirc.ts` 修改后端 API 地址：

```typescript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
  },
}
```

### 路由配置

路由配置在 `.umirc.ts` 的 `routes` 字段中。

## 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| hr001 | 123456 | 人事专员 |
| emp001 | 123456 | 普通员工 |

## 核心功能

- 用户登录
- 员工管理（CRUD、导出）
- 工资查询
- 工资条导出（PDF）
- 统计报表（图表展示）
- 权限控制

