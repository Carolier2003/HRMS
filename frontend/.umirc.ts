import { defineConfig } from '@umijs/max';

export default defineConfig({
  antd: {},
  access: {},
  model: {},
  initialState: {},
  request: {},
  layout: {
    title: 'HR管理系统',
  },
  routes: [
    {
      path: '/login',
      layout: false,
      component: './Login',
    },
    {
      path: '/',
      redirect: '/employee',
    },
    {
      name: '员工管理',
      path: '/employee',
      icon: 'TeamOutlined',
      component: './Employee',
    },
    {
      name: '工资管理',
      path: '/salary',
      icon: 'MoneyCollectOutlined',
      routes: [
        {
          path: '/salary/my',
          name: '我的工资条',
          component: './Salary/MySalary',
        },
        {
          path: '/salary/manage',
          name: '工资管理',
          component: './Salary/Manage',
          access: 'canManageSalary',
        },
        {
          path: '/salary/items',
          name: '工资项目管理',
          component: './Salary/Items',
          access: 'canManageSalary',
        },
      ],
    },
    {
      name: '统计报表',
      path: '/statistics',
      icon: 'BarChartOutlined',
      component: './Statistics',
      access: 'canViewStatistics',
    },
    {
      name: '系统管理',
      path: '/system',
      icon: 'SettingOutlined',
      access: 'isAdmin',
      routes: [
        {
          path: '/system/users',
          name: '用户管理',
          component: './System/Users',
        },
        {
          path: '/system/permissions',
          name: '权限管理',
          component: './System/Permissions',
        },
      ],
    },
  ],
  npmClient: 'npm',
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
    },
  },
});

