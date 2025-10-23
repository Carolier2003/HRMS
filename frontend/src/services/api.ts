import { request } from '@umijs/max';

/**
 * API 服务层
 */

// ==================== 认证相关 ====================
export async function login(data: API.LoginRequest) {
  return request<API.R<API.LoginResponse>>('/api/auth/login', {
    method: 'POST',
    data,
  });
}

export async function refreshToken(refreshToken: string) {
  return request<API.R<API.LoginResponse>>('/api/auth/refresh', {
    method: 'POST',
    data: refreshToken,
    headers: {
      'Content-Type': 'application/json',
    },
  });
}

export async function getCurrentUser(): Promise<API.CurrentUser> {
  const userInfo = localStorage.getItem('userInfo');
  if (userInfo) {
    return JSON.parse(userInfo);
  }
  throw new Error('未登录');
}

// ==================== 员工相关 ====================
export async function queryEmployees(params: API.EmployeeQuery) {
  return request<API.R<API.PageInfo<API.Employee>>>('/api/employees/list', {
    method: 'GET',
    params,
  });
}

export async function getEmployee(id: number) {
  return request<API.R<API.Employee>>(`/api/employees/${id}`, {
    method: 'GET',
  });
}

export async function addEmployee(data: API.Employee) {
  return request<API.R<API.Employee>>('/api/employees', {
    method: 'POST',
    data,
  });
}

export async function updateEmployee(id: number, data: API.Employee) {
  return request<API.R<API.Employee>>(`/api/employees/${id}`, {
    method: 'PUT',
    data,
  });
}

export async function deleteEmployee(id: number) {
  return request<API.R<void>>(`/api/employees/${id}`, {
    method: 'DELETE',
  });
}

export async function changeEmployeeStatus(id: number, status: string, reason?: string) {
  return request<API.R<void>>(`/api/employees/${id}/status`, {
    method: 'PUT',
    params: { status, reason },
  });
}

export async function exportEmployees(params: API.EmployeeQuery) {
  return request('/api/employees/export', {
    method: 'GET',
    params,
    responseType: 'blob',
  });
}

// ==================== 工资相关 ====================
export async function getSalaryItems() {
  return request<API.R<API.SalaryItem[]>>('/api/salary/items', {
    method: 'GET',
  });
}


export async function createSalaryRecord(data: API.SalaryRecordRequest) {
  return request<API.R<API.SalaryRecord>>('/api/salary/records', {
    method: 'POST',
    data,
  });
}

export async function getEmployeeSalaryRecords(employeeId: number) {
  return request<API.R<API.SalaryRecord[]>>(`/api/salary/records/employee/${employeeId}`, {
    method: 'GET',
  });
}

export async function getSalaryDetail(recordId: number) {
  return request<API.R<API.SalaryDetailVO>>(`/api/salary/records/${recordId}`, {
    method: 'GET',
  });
}

export async function exportSalaryPdf(recordId: number) {
  return request(`/api/salary/records/${recordId}/pdf`, {
    method: 'GET',
    responseType: 'blob',
    timeout: 30000, // 30秒超时
  });
}

// ==================== 工资项目管理 ====================
export async function createSalaryItem(data: API.SalaryItem) {
  return request<API.R<API.SalaryItem>>('/api/salary/items', {
    method: 'POST',
    data,
  });
}

export async function updateSalaryItem(id: number, data: API.SalaryItem) {
  return request<API.R<API.SalaryItem>>(`/api/salary/items/${id}`, {
    method: 'PUT',
    data,
  });
}

export async function deleteSalaryItem(id: number) {
  return request<API.R<void>>(`/api/salary/items/${id}`, {
    method: 'DELETE',
  });
}

export async function deleteSalaryRecord(id: number) {
  return request<API.R<void>>(`/api/salary/records/${id}`, {
    method: 'DELETE',
  });
}

// ==================== 用户管理 ====================
export async function getUsers() {
  return request<API.R<API.User[]>>('/api/users', {
    method: 'GET',
  });
}

export async function createUser(data: API.UserRequest) {
  return request<API.R<API.User>>('/api/users', {
    method: 'POST',
    data,
  });
}

export async function updateUser(id: number, data: API.UserRequest) {
  return request<API.R<API.User>>(`/api/users/${id}`, {
    method: 'PUT',
    data,
  });
}

export async function deleteUser(id: number) {
  return request<API.R<void>>(`/api/users/${id}`, {
    method: 'DELETE',
  });
}

export async function getRoles() {
  return request<API.R<API.Role[]>>('/api/roles', {
    method: 'GET',
  });
}

export async function createRole(data: API.Role) {
  return request<API.R<API.Role>>('/api/roles', {
    method: 'POST',
    data,
  });
}

export async function updateRole(id: number, data: API.Role) {
  return request<API.R<API.Role>>(`/api/roles/${id}`, {
    method: 'PUT',
    data,
  });
}

export async function deleteRole(id: number) {
  return request<API.R<void>>(`/api/roles/${id}`, {
    method: 'DELETE',
  });
}

// ==================== 系统设置 ====================
export async function getSystemSettings() {
  return request<API.R<API.SystemSettings>>('/api/system/settings', {
    method: 'GET',
  });
}

export async function updateSystemSettings(data: API.SystemSettings) {
  return request<API.R<API.SystemSettings>>('/api/system/settings', {
    method: 'PUT',
    data,
  });
}

// ==================== 统计相关 ====================
export async function getEducationStatistics() {
  return request<API.R<API.StatisticsVO[]>>('/api/statistics/education', {
    method: 'GET',
  });
}

export async function getMaritalStatusStatistics() {
  return request<API.R<API.StatisticsVO[]>>('/api/statistics/marital-status', {
    method: 'GET',
  });
}

export async function getDepartmentStatistics() {
  return request<API.R<API.StatisticsVO[]>>('/api/statistics/department', {
    method: 'GET',
  });
}

export async function getStatistics() {
  return request<API.R<API.StatisticsOverview>>('/api/statistics/overview', {
    method: 'GET',
  });
}

