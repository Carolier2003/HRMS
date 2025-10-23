declare namespace API {
  // 通用响应类型
  type R<T> = {
    code: number;
    msg: string;
    data: T;
  };
  
  // 分页信息
  type PageInfo<T> = {
    list: T[];
    pageNum: number;
    pageSize: number;
    total: number;
    pages: number;
  };
  
  // 当前用户
  type CurrentUser = {
    userId: number;
    username: string;
    realName?: string;
    roles: string[];
  };

  // 用户
  type User = {
    id?: number;
    username: string;
    realName: string;
    email: string;
    status: string;
    roles?: Role[];
    createdAt?: string;
    updatedAt?: string;
  };

  // 用户请求
  type UserRequest = {
    username: string;
    realName: string;
    email: string;
    password?: string;
    status: string;
    roleIds: number[];
  };

  // 角色
  type Role = {
    id?: number;
    roleName: string;
    roleCode: string;
    description?: string;
    createdAt?: string;
  };

  // 系统设置
  type SystemSettings = {
    systemName: string;
    systemVersion: string;
    systemDescription?: string;
    passwordMinLength: number;
    sessionTimeout: number;
    enableTwoFactor: boolean;
    enableLoginLog: boolean;
    smtpHost?: string;
    smtpPort?: number;
    smtpUsername?: string;
    smtpPassword?: string;
    enableEmailNotification: boolean;
    backupInterval: number;
    maxBackupFiles: number;
    enableDataExport: boolean;
  };
  
  // 登录请求
  type LoginRequest = {
    username: string;
    password: string;
  };
  
  // 登录响应
  type LoginResponse = {
    accessToken: string;
    refreshToken: string;
    userId: number;
    username: string;
    realName?: string;
    roles: string[];
  };
  
  // 员工
  type Employee = {
    id?: number;
    employeeNo: string;
    name: string;
    gender?: string;
    birthDate?: string;
    idCard?: string;
    phone?: string;
    email?: string;
    address?: string;
    education?: string;
    major?: string;
    graduateSchool?: string;
    graduateDate?: string;
    maritalStatus?: string;
    department?: string;
    position?: string;
    jobTitle?: string;
    entryDate?: string;
    workYears?: number;
    status?: string;
    leaveDate?: string;
    leaveReason?: string;
  };
  
  // 员工查询
  type EmployeeQuery = {
    name?: string;
    department?: string;
    education?: string;
    maritalStatus?: string;
    position?: string;
    status?: string;
    pageNum?: number;
    pageSize?: number;
  };
  
  // 工资项
  type SalaryItem = {
    id?: number;
    name: string;
    type: string;
    defaultAmount: number;
    enabled: boolean;
    description?: string;
    createdAt?: string;
    updatedAt?: string;
  };
  
  // 工资记录
  type SalaryRecord = {
    id?: number;
    employeeId: number;
    yearMonth: string;
    baseSalary: number;
    bonus: number;
    deduction: number;
    actualSalary: number;
    remark?: string;
  };
  
  // 工资记录请求
  type SalaryRecordRequest = {
    employeeId: number;
    yearMonth: string;
    baseSalary: number;
    bonus: number;
    deduction: number;
    actualSalary: number;
    remark?: string;
    details?: {
      itemId: number;
      amount: number;
    }[];
  };
  
  // 工资明细
  type SalaryDetailVO = {
    id: number;
    employeeId: number;
    employeeName: string;
    employeeNo: string;
    yearMonth: string;
    baseSalary: number;
    bonus: number;
    deduction: number;
    actualSalary: number;
    remark?: string;
    items: {
      itemName: string;
      itemType: string;
      amount: number;
    }[];
  };
  
  // 统计数据
  type StatisticsVO = {
    name: string;
    count: number;
  };
  
  // 统计数据项
  type StatItem = {
    name: string;
    value: number;
  };
  
  // 统计概览
  type StatisticsOverview = {
    totalEmployees: number;
    onJobCount: number;
    leaveCount: number;
    educationStats: StatItem[];
    maritalStats: StatItem[];
    positionStats: StatItem[];
    departmentStats: StatItem[];
  };
}

